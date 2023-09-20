package cs309.backend.services;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.repo.TestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final TestEntityRepository testRepository;

    @Autowired
    public UserService(TestEntityRepository testRepository) {
        this.testRepository = testRepository;
    }

    public TestEntity readTestData(int id) {
        return testRepository.readTestData(id);
    }
}
