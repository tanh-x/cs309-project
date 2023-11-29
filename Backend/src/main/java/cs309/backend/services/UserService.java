package cs309.backend.services;

import cs309.backend.auth.AuthorizationUtils;
import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.user.*;
import cs309.backend.jpa.repo.*;
import cs309.backend.DTOs.ChangePasswordData;
import cs309.backend.DTOs.LoginData;
import cs309.backend.DTOs.RegistrationData;
import cs309.backend.DTOs.SessionTokenData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
@Transactional
public class UserService {
    private final TestEntityRepository testRepository;
    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final StaffRepository staffRepository;

    private final AdminRepository adminRepository;
    @Autowired
    public UserService(TestEntityRepository testRepository, UserRepository userRepository, StudentRepository studentRepository, StaffRepository staffRepository, AdminRepository adminRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
        this.adminRepository = adminRepository;
    }

    /*public TestEntity readTestTable(int id) {
        return testRepository.readTestTable(id);
    }*/

    /*public void registerUser(RegistrationData args) {
        String pwdBcryptHash = AuthorizationUtils.bcryptHash(args.password());

        userRepository.registerUser(
            args.username(),
            args.email(),
            args.displayName(),
            args.privilegeLevel(),
            pwdBcryptHash
        );
    }*/

    public void registerUser(RegistrationData args) {
            String pwdBcryptHash = AuthorizationUtils.bcryptHash(args.password());
            UserEntity user = new UserEntity(args.username(),
                    args.email(),
                    args.displayName(),
                    args.privilegeLevel(),
                    pwdBcryptHash);
            userRepository.save(user);
            if (args.privilegeLevel() == 1) {
                StudentEntity student = new StudentEntity(user, null);
                studentRepository.save(student);
            } else if (args.privilegeLevel() == 2) {
                StaffEntity staff = new StaffEntity(user, false);
                staffRepository.save(staff);
            } else if (args.privilegeLevel() == 3) {
                AdminEntity admin = new AdminEntity(user, false);
                adminRepository.save(admin);
            }
    }

    public SessionTokenData loginUser(LoginData args) {
        UserEntity user = getUserByEmail(args.email());
        user = (user != null) ? user : getUserByUsername(args.email());

        // Check if credentials were correct
        if (!validateLoginCredentials(user, args)) throw new InvalidCredentialsException();

        // Else, we give them the session token
        return new SessionTokenData(true, AuthorizationUtils.createSessionJwt(user.getUid(), user.getUsername()));
    }

    private boolean validateLoginCredentials(UserEntity user, LoginData login) {
        if (user == null) return false;
        if (!user.isVerified()) return false;
        return BCrypt.checkpw(login.password(), user.getPwdBcryptHash());
    }

    public UserEntity getUserByUid(int uid) throws EntityNotFoundException {
        return userRepository.getReferenceById(uid);
    }
    public UserEntity getUserByUsername(String username) throws EntityNotFoundException{
        UserEntity user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    public UserEntity getUserByEmail(String email) throws EntityNotFoundException{
        UserEntity user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    public Boolean updateUser(int uid, String email, String displayName) {
        UserEntity user = getUserByUid(uid);
        if (user == null) {
            return false;
        }
        /*userRepository.updateUser(
            uid,
            Objects.equals(email, "") ? null : email,
            Objects.equals(displayName, "") ? null : displayName
        );*/

            if (email != null) {
                user.setEmail(email);
            }
            if (displayName != null) {
                user.setDisplayName(displayName);
            }
            // Save the updated user entity
            userRepository.save(user);

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
        curUser.setPwdBcryptHash(newPass);
        return "Successful";
    }

    public Boolean deleteUser(Principal user) {
        var curUser = (UserEntity) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        //userRepository.deleteUser(curUser.getUid(), curUser.getPrivilegeLevel());
        if (curUser.getPrivilegeLevel() == 1) {
            studentRepository.delete(studentRepository.getReferenceById(curUser.getUid()));
        }
        else if (curUser.getPrivilegeLevel() == 2) {
            staffRepository.delete(staffRepository.getReferenceById(curUser.getUid()));
        }
        else if (curUser.getPrivilegeLevel() == 3) {
            adminRepository.delete(adminRepository.getReferenceById(curUser.getUid()));
        }
        userRepository.delete(curUser);
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
