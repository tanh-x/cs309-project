package cs309.backend.services;

import cs309.backend.core.AuthorizationUtils;
import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.models.LoginData;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        UserEntity user = getUserByEmail(args.email());

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

    public UserEntity getUserByUid(int uid) {
        return userRepository.getUserByUid(uid);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
