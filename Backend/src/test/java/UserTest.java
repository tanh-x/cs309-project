import cs309.backend.DTOs.RegistrationData;
import cs309.backend.jpa.entity.user.AdminEntity;
import cs309.backend.jpa.entity.user.StaffEntity;
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
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(studentRepository.save(any(StudentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(staffRepository.save(any(StaffEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(adminRepository.save(any(AdminEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        int[] arr = {1,2,3};
        for (int i : arr) {
            RegistrationData reg = new RegistrationData("Khoi", "duckhoi@iastate.edu", "nopeGG", "KhoiZeus", i);

            userService.registerUser(reg);
            if (i == 1) {
                verify(studentRepository).save(any(StudentEntity.class));
            }
            else if (i == 2) {
                verify(staffRepository).save(any(StaffEntity.class));
            }
            else {
                verify(adminRepository).save(any(AdminEntity.class));
            }
        }
        verify(userRepository, times(3)).save(any(UserEntity.class));
    }
}
