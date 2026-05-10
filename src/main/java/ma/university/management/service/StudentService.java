package ma.university.management.service;

import ma.university.management.dto.StudentDTO;
import ma.university.management.entity.enums.Level;
import ma.university.management.entity.enums.StudentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    Page<StudentDTO> getAllStudents(Pageable pageable);

    StudentDTO getStudentById(Long id);

    StudentDTO getStudentByCin(String cin);

    StudentDTO getStudentByCne(String cne);

    StudentDTO createStudent(StudentDTO dto);

    StudentDTO updateStudent(Long id, StudentDTO dto);

    void deleteStudent(Long id);

    Page<StudentDTO> searchStudents(String keyword, Pageable pageable);

    Page<StudentDTO> filterStudents(Long filiereId, Level level, StudentStatus status, Pageable pageable);

    List<StudentDTO> getAllStudentsList();

    List<StudentDTO> getStudentsByFiliere(Long filiereId);
}
