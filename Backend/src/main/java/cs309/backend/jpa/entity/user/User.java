package cs309.backend.jpa.entity.user;

public sealed interface User
    permits
    AdminEntity,
    StaffEntity,
    StudentEntity,
    UserEntity {
}
