package cs309.backend.jpa.repo;


import cs309.backend.jpa.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    // TODO: Query / Stored Procedure
    /*
    @author KhoiPham
    This one is pretty straightforward and does not require much memory so I do query
    If you want to do Procedure for security is fine.
     */
    @Query("SELECT u FROM UserEntity u WHERE u.username = :p_username")
    UserEntity getUserByUsername(@Param("p_username") String username);

    // TODO: Query / Stored Procedure
    @Query("SELECT u FROM UserEntity u WHERE u.uid = :p_uid")
    UserEntity getUserByUid(@Param("p_uid") int uid);
}
