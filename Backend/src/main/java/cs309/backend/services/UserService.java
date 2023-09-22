package cs309.backend.services;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import cs309.backend.models.RegistrationData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;


@Service
@Transactional
public class UserService {
    private final TestEntityRepository testRepository;

    @Autowired
    public UserService(TestEntityRepository testRepository) {
        this.testRepository = testRepository;
    }

    public TestEntity readTestTable(int id) {
        return testRepository.readTestTable(id);
    }

    public boolean regsiterUser(RegistrationData data) {

    }
}
