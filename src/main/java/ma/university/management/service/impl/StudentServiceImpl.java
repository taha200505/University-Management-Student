package ma.university.management.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.StudentDTO;
import ma.university.management.entity.Filiere;
import ma.university.management.entity.Student;
import ma.university.management.entity.enums.Level;
import ma.university.management.entity.enums.StudentStatus;
import ma.university.management.exception.DuplicateResourceException;
import ma.university.management.exception.ResourceNotFoundException;
import ma.university.management.mapper.StudentMapper;
import ma.university.management.repository.FiliereRepository;
import ma.university.management.repository.StudentRepository;
import ma.university.management.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@SuppressWarnings("null")
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FiliereRepository filiereRepository;
    private final StudentMapper studentMapper;

    @Override
    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        log.debug("Fetching all students, page: {}", pageable.getPageNumber());
        return studentRepository.findAll(pageable).map(studentMapper::toDto);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", "id", id));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDTO getStudentByCin(String cin) {
        Student student = studentRepository.findByCin(cin)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", "CIN", cin));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDTO getStudentByCne(String cne) {
        Student student = studentRepository.findByCne(cne)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", "CNE", cne));
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional
    public StudentDTO createStudent(StudentDTO dto) {
        log.info("Creating student: {} {}", dto.getFirstName(), dto.getLastName());

        validateUniqueness(dto, null);

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", dto.getFiliereId()));

        Student student = studentMapper.toEntity(dto);
        student.setFiliere(filiere);

        Student saved = studentRepository.save(student);
        log.info("Student created with id: {}", saved.getId());
        return studentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        log.info("Updating student id: {}", id);

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant", "id", id));

        validateUniqueness(dto, id);

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", dto.getFiliereId()));

        studentMapper.updateEntityFromDto(dto, existing);
        existing.setFiliere(filiere);

        Student saved = studentRepository.save(existing);
        return studentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.info("Deleting student id: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Étudiant", "id", id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public Page<StudentDTO> searchStudents(String keyword, Pageable pageable) {
        log.debug("Searching students with keyword: {}", keyword);
        return studentRepository.searchByKeyword(keyword, pageable).map(studentMapper::toDto);
    }

    @Override
    public Page<StudentDTO> filterStudents(Long filiereId, Level level,
                                           StudentStatus status, Pageable pageable) {
        log.debug("Filtering students — filiere: {}, level: {}, status: {}", filiereId, level, status);
        return studentRepository.findByFilters(filiereId, level, status, pageable)
                .map(studentMapper::toDto);
    }

    @Override
    public List<StudentDTO> getAllStudentsList() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentDTO> getStudentsByFiliere(Long filiereId) {
        return studentRepository.findAllByFiliereId(filiereId).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    // ---- Private helpers ----

    private void validateUniqueness(StudentDTO dto, Long excludeId) {
        studentRepository.findByCin(dto.getCin()).ifPresent(s -> {
            if (!s.getId().equals(excludeId)) {
                throw new DuplicateResourceException("Étudiant", "CIN", dto.getCin());
            }
        });
        studentRepository.findByCne(dto.getCne()).ifPresent(s -> {
            if (!s.getId().equals(excludeId)) {
                throw new DuplicateResourceException("Étudiant", "CNE", dto.getCne());
            }
        });
    }
}
