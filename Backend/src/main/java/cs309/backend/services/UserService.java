package cs309.backend.services;

import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.models.LoginData;
import cs309.backend.models.RegistrationData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;


@Service
@Transactional
public class UserService {
    private final TestEntityRepository testRepository;
    private final UserRepository userRepository;

    private static final int BCRYPT_LOG_ROUNDS = 8;

    @Autowired
    public UserService(TestEntityRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    public TestEntity readTestTable(int id) {
        return testRepository.readTestTable(id);
    }

    public void registerUser(RegistrationData args) {
        String salt = BCrypt.gensalt(BCRYPT_LOG_ROUNDS);
        String hashed_password = BCrypt.hashpw(args.password(), salt);

        userRepository.registerUser(
            args.username(),
            args.email(),
            args.displayName(),
            args.privilegeLevel(),
            hashed_password
        );
    }

    public void LoginUser(LoginData args) {
        if (!validateLoginCredentials(args)) {
            // Wrong password
            throw new InvalidCredentialsException();
        }

    }

    private boolean validateLoginCredentials(LoginData args) {
        return true;
    }
}
