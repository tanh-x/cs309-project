package cs309.backend.services;

import cs309.backend.core.AuthorizationUtils;
import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.models.LoginData;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import jakarta.transaction.Transactional;
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

    public SessionTokenData LoginUser(LoginData args) {
        if (!validateLoginCredentials(args)) {
            // Wrong password
            throw new InvalidCredentialsException();
        }

        // Else, we give them the session token
        return new SessionTokenData(
            AuthorizationUtils.createSessionJwt(1)
        );
    }

    private boolean validateLoginCredentials(LoginData args) {
        return true;
    }
}
