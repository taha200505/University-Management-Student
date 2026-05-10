package ma.university.management.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.CourseDTO;
import ma.university.management.entity.Course;
import ma.university.management.entity.Filiere;
import ma.university.management.entity.Professor;
import ma.university.management.exception.DuplicateResourceException;
import ma.university.management.exception.ResourceNotFoundException;
import ma.university.management.mapper.CourseMapper;
import ma.university.management.repository.CourseRepository;
import ma.university.management.repository.FiliereRepository;
import ma.university.management.repository.ProfessorRepository;
import ma.university.management.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@SuppressWarnings("null")
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final FiliereRepository filiereRepository;
    private final ProfessorRepository professorRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseDTO createCourse(CourseDTO dto) {
        log.info("Creating course: {}", dto.getCode());
        if (courseRepository.existsByCode(dto.getCode())) {
            throw new DuplicateResourceException("Module", "code", dto.getCode());
        }

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", dto.getFiliereId()));

        Course course = courseMapper.toEntity(dto);
        course.setFiliere(filiere);

        if (dto.getProfessorId() != null) {
            Professor professor = professorRepository.findById(dto.getProfessorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professeur", "id", dto.getProfessorId()));
            course.setProfessor(professor);
        }

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        log.info("Updating course id: {}", id);
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));

        courseRepository.findByCode(dto.getCode()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new DuplicateResourceException("Module", "code", dto.getCode());
            }
        });

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", dto.getFiliereId()));

        courseMapper.updateEntityFromDto(dto, existing);
        existing.setFiliere(filiere);

        if (dto.getProfessorId() != null) {
            Professor professor = professorRepository.findById(dto.getProfessorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professeur", "id", dto.getProfessorId()));
            existing.setProfessor(professor);
        } else {
            existing.setProfessor(null);
        }

        return courseMapper.toDto(courseRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course id: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Module", "id", id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseDTO> getCoursesByFiliere(Long filiereId) {
        return courseRepository.findByFiliereId(filiereId).stream()
                .map(courseMapper::toDto)
                .toList();
    }
}
