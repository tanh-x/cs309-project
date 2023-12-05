package cs309.backend.services;

import cs309.backend.jpa.entity.user.StudentEntity;
import cs309.backend.jpa.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository repo) {
        this.studentRepository = repo;
    }

    public StudentEntity getStudentByUid(int uid) throws EntityNotFoundException{
        StudentEntity student = studentRepository.getStudentByUid(uid);
        if (student == null) {
            throw new EntityNotFoundException();
        }
        return student;
    }
}
