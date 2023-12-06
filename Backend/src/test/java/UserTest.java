import cs309.backend.DTOs.RegistrationData;
import cs309.backend.jpa.entity.user.StudentEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.jpa.repo.*;
import cs309.backend.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserTest {
    @TestConfiguration
    static class userServiceConfig {
        @Bean
        public UserService userService(){
            return new UserService();
        }

        @Bean
        UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        StudentRepository studentRepository() {
            return mock(StudentRepository.class);
        }

        @Bean
        StaffRepository staffRepository() {
            return mock(StaffRepository.class);
        }

        @Bean
        AdminRepository adminRepository() {
            return mock(AdminRepository.class);
        }
        @Bean
        StudentProgramRepository studentProgramRepository() {
            return mock(StudentProgramRepository.class);
        }

        @Bean
        TestEntityRepository testEntityRepository() {
            return mock(TestEntityRepository.class);
        }
    }
    @Autowired
    private  UserService userService;
    @Autowired
    private  StudentProgramRepository studentProgramRepository;
    @Autowired
    private  TestEntityRepository testEntityRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  StaffRepository staffRepository;
    @Autowired
    private  StudentRepository studentRepository;
    @Autowired
    private  AdminRepository adminRepository;

    @Test
    public void registerUser() {
        RegistrationData reg = new RegistrationData("Khoi", "duckhoi@iastate.edu","nopeGG","KhoiZeus", 1);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(studentRepository.save(any(StudentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        userService.registerUser(reg);
        verify(userRepository).save(any(UserEntity.class));
        //verify(studentRepository).save(any(UserEntity.class));
    }
}
