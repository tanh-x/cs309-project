import cs309.backend.DTOs.RegistrationData;
import cs309.backend.jpa.entity.user.AdminEntity;
import cs309.backend.jpa.entity.user.StaffEntity;
import cs309.backend.jpa.entity.user.StudentEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.jpa.repo.*;
import cs309.backend.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Test
    public void testGrantPermission() {
        // Mock user
        UserEntity mockUser = new UserEntity();
        mockUser.setPrivilegeLevel(1);
        mockUser.setUid(1);

        // Mock getUserByUid method
        when(userRepository.getUserByUid(anyInt())).thenReturn(mockUser);

        // Mock countByUid method
        when(studentProgramRepository.countByUid(anyInt())).thenReturn(0);

        // Mock delete methods
        doNothing().when(studentRepository).delete(any());
        doNothing().when(studentProgramRepository).delete(any());

        String result = userService.grantPermission(1, 1);
        assertEquals(result, "Not a correct privilege");
        // Call the method to test
        result = userService.grantPermission(1, 2);

        // Verify interactions
        verify(userRepository, times(1)).getUserByUid(1);
        verify(studentProgramRepository, times(1)).countByUid(1);
        verify(studentRepository, times(1)).delete(any());
        verify(staffRepository, times(2)).save(any(StaffEntity.class));//1 more in register
        verify(adminRepository, times(1)).save(any(AdminEntity.class));//1 more in register

        // Assertions
        assertEquals("Successful", result);
        assertEquals(2, mockUser.getPrivilegeLevel());

        result = userService.grantPermission(1, 2);
        assertEquals("This is you current privilege", result);

        result = userService.grantPermission(1, 3);
        verify(userRepository, times(3)).getUserByUid(1);
        verify(studentProgramRepository, times(2)).countByUid(1);
        verify(studentRepository, times(2)).delete(any());
        verify(staffRepository, times(2)).save(any(StaffEntity.class));//1 more in register
        verify(adminRepository, times(2)).save(any(AdminEntity.class));//1 more in register
        assertEquals("Successful", result);
        assertEquals(3, mockUser.getPrivilegeLevel());
    }

    @Test
    public void testUpdateUser() {
        int uid = 10;
        String email = "newemail@example.com";
        String displayName = "NewDisplayName";

        UserEntity userEntity = new UserEntity("existingUser", "existingemail@example.com", "ExistingDisplayName", 1, "hashedpassword");
        userEntity.setUid(uid);


        when(userRepository.getUserByUid(uid)).thenReturn(userEntity);

        boolean result = userService.updateUser(uid, email, displayName);

        assertTrue(result);
        verify(userRepository, times(1)).save(userEntity);

        assertEquals(email, userEntity.getEmail());
        assertEquals(displayName, userEntity.getDisplayName());
    }


}
