package cs309.backend.services;

import cs309.backend.core.AuthorizationUtils;
import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.UserEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.models.LoginData;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import cs309.backend.models.Users;
import cs309.backend.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


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

        userRepository.registerUser(
            args.username(),
            args.email(),
            args.displayName(),
            args.privilegeLevel(),
            pwdBcryptHash
        );
    }

    public SessionTokenData loginUser(LoginData args) {
        UserEntity user = userRepository.getUserByEmail(args.email());

        // Check if credentials were correct
        if (!validateLoginCredentials(user, args)) throw new InvalidCredentialsException();

        // Else, we give them the session token
        return new SessionTokenData(true, AuthorizationUtils.createSessionJwt(user.getUid()));
    }

    private boolean validateLoginCredentials(UserEntity user, LoginData login) {
        if (user == null) return false;
        if (!user.isVerified()) return false;
        return BCrypt.checkpw(login.password(), user.getPwdBcryptHash());
    }

    private final cs309.backend.repo.UserRepository userRepository;
    public UserService(cs309.backend.repo.UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Users getUser(int check, int id) {
        if (check == 1) {
            return userRepository.findUserById(id);
        }
        else {      //check = 2
            return userRepository.findUserByUID(id);
        }
    }
    public Users getUser(int check, String str) {
        if (check == 1) {
            return userRepository.findUserByEmail(str);
        } else {        //check == 2
            return userRepository.findUserByUsername(str);
        }
    }
    public String createUser(Users user) {
        if (user.getUsername() == null || user.getEmail() == null) {       //check if the input username or email null
            return "Invalid Input";
        }
        if (getUser(1, user.getEmail()) != null || getUser(2, user.getUsername()) != null) {
            return "Already exist";
        }
        userRepository.save(user);
        return "nice";
    }
}
