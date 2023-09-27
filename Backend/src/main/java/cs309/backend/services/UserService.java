package cs309.backend.services;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.jpa.repo.UserRepository;
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

    public void regsiterUser(RegistrationData data) {
        String salt = BCrypt.gensalt(BCRYPT_LOG_ROUNDS);
        String hashed_password = BCrypt.hashpw(data.password(), salt);

        userRepository.registerUser(
            data.username(),
            data.email(),
            data.displayName(),
            data.privilegeLevel(),
            hashed_password
        );
    }
}
