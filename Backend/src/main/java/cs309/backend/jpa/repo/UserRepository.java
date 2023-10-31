package cs309.backend.jpa.repo;


import cs309.backend.jpa.entity.user.UserEntity;
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
    UserEntity getUserByEmail(@Param("p_email") String email);

    @Procedure(name = "getUserByUsername")
    UserEntity getUserByUsername(@Param("p_username") String username);

    @Procedure(name = "getUserByUid")
    UserEntity getUserByUid(@Param("p_uid") int uid);

    @Procedure(name = "updateUser")
    void updateUser(@Param("p_uid") int id, @Param("p_email") String email, @Param("p_display_name") String displayName);

    @Procedure(name = "changePassword")
    void changePassword(@Param("p_pass") String password, @Param("p_uid") int uid);

    @Procedure(name = "deleteUser")
    void deleteUser(@Param("p_uid") int uid, @Param("p_privilege_level") int privilege_level);

    @Procedure(name = "getUidByUsername")
    Integer getIdByUsername(@Param("p_username") String username);
}
