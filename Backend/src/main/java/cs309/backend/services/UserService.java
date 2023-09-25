package cs309.backend.services;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.Users;
import cs309.backend.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Transactional
public class UserService {
    /*private final TestEntityRepository testRepository;

    @Autowired
    public UserService(TestEntityRepository testRepository) {
        this.testRepository = testRepository;
    }

    public TestEntity readTestTable(int id) {
        return testRepository.readTestTable(id);
    }

    public boolean regsiterUser(RegistrationData data) {
        return true;
    }*/
    private final UserRepository userRepository;

    @Autowired UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getUser(UUID id) {
        return userRepository.findUser(id);
    }

    public Users getUser(int check, String str) {
        if (check == 1) {
            return userRepository.findUserByEmail(str);
        } else {        //check == 2
            return userRepository.findUserByUsername(str);
        }
    }
}
