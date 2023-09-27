package cs309.backend.repo;

import cs309.backend.EntityInterfaces.IUser;
import cs309.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<IUser, Long> {
    @Query("SELECT u FROM Users u WHERE u.id = :id")
    Users findUserById(@Param("id") int id);
    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Users findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM Users u WHERE u.uid = :uid")
    Users findUserByUID(@Param("uid") int uid);
}
