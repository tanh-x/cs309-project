package cs309.backend.services;

import cs309.backend.auth.AuthorizationUtils;
import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.User;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.models.ChangePasswordData;
import cs309.backend.models.LoginData;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.Objects;


@Service
@Transactional
public class UserService {
    private final TestEntityRepository testRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(TestEntityRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    public TestEntity readTestTable(int id) {
        return testRepository.readTestTable(id);
    }

    public void registerUser(RegistrationData args) {
        String pwdBcryptHash = AuthorizationUtils.bcryptHash(args.password());
        String token = AuthorizationUtils.createSessionJwt(args.username());
        userRepository.registerUser(
            args.username(),
            args.email(),
            args.displayName(),
            args.privilegeLevel(),
            pwdBcryptHash
        );
    }

    public SessionTokenData loginUser(LoginData args) {
        UserEntity user = getUserByEmail(args.email());
        user = (user != null) ? user : getUserByUsername(args.email());

        // Check if credentials were correct
        if (!validateLoginCredentials(user, args)) throw new InvalidCredentialsException();

        // Else, we give them the session token
        return new SessionTokenData(true, AuthorizationUtils.createSessionJwt(user.getUsername()));
    }

    private boolean validateLoginCredentials(UserEntity user, LoginData login) {
        if (user == null) return false;
        if (!user.isVerified()) return false;
        return BCrypt.checkpw(login.password(), user.getPwdBcryptHash());
    }

    public UserEntity getUserByUid(int uid) {
        return userRepository.getUserByUid(uid);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Boolean updateUser(int uid, String email, String displayName) {
        UserEntity user = getUserByUid(uid);
        if (user == null) {
            return false;
        }
        userRepository.updateUser(
                uid,
                Objects.equals(email, "") ? null : email,
                Objects.equals(displayName, "") ? null : displayName
        );
        return true;
    }

    public String changePassword(ChangePasswordData req, Principal user) {
        var curUser = (UserEntity) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        if (!BCrypt.checkpw(req.currentPassword(), curUser.getPwdBcryptHash())) {
            return "Wrong password";
        }
        if (!req.newPassword().equals(req.confirmationPassword())) {
            return "Passwords are not the same";
        }
        String newPass = AuthorizationUtils.bcryptHash(req.newPassword());
        userRepository.changePassword(newPass, curUser.getUid());
        return "Successful";
    }

    public Boolean deleteUser(Principal user) {
        var curUser = (UserEntity) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        userRepository.deleteUser(curUser.getUid(), curUser.getPrivilegeLevel());
        return true;
    }

    public String grantPermission(int id, int newPrivilege) {
        if (newPrivilege != 2 && newPrivilege != 3) {
            return "Not a correct privilege";
        }
        var user = getUserByUid(id);
        if (user == null) {
            return "Cannot Find User";
        }
        if (newPrivilege == user.getPrivilegeLevel()) {
            return "This is you current privilege";
        }
        userRepository.grantPermission(id, newPrivilege);
        return "Successful";
    }

    /*public Boolean testDeleteUser(int uid) {
        var curUser =  getUserByUid(uid);
        userRepository.deleteUser(curUser.getUid(), curUser.getPrivilegeLevel());
        return true;
    }*/
}
