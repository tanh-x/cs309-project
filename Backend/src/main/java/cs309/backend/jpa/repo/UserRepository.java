package cs309.backend.jpa.repo;


import cs309.backend.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Procedure(name = "registerUser")
    void registerUser(
        @Param("p_username") String username,
        @Param("p_email") String email,
        @Param("p_display_name") String displayName,
        @Param("p_privilege_level") int privilegeLevel,
        @Param("p_pwd_bcrypt_hash") String passwordBcryptHash
    );

    @Procedure(name = "getUserByEmail")
    UserEntity getByEmail(@Param("p_email") String email);

    @Procedure(name = "getUserByUsername")
    UserEntity getByUsername(@Param("p_username") String username);
}
