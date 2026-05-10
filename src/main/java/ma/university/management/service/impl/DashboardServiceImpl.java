package ma.university.management.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.DashboardDTO;
import ma.university.management.entity.enums.StudentStatus;
import ma.university.management.repository.CourseRepository;
import ma.university.management.repository.FiliereRepository;
import ma.university.management.repository.ProfessorRepository;
import ma.university.management.repository.StudentRepository;
import ma.university.management.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;
    private final FiliereRepository filiereRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;

    @Override
    public DashboardDTO getDashboardStatistics() {
        log.debug("Building dashboard statistics");

        Map<String, Long> studentsPerFiliere = new LinkedHashMap<>();
        studentRepository.countStudentsPerFiliere()
                .forEach(row -> studentsPerFiliere.put((String) row[0], (Long) row[1]));

        Map<String, Long> studentsPerLevel = new LinkedHashMap<>();
        studentRepository.countStudentsPerLevel()
                .forEach(row -> studentsPerLevel.put(row[0].toString(), (Long) row[1]));

        Map<String, Long> studentsPerStatus = new LinkedHashMap<>();
        studentRepository.countStudentsPerStatus()
                .forEach(row -> studentsPerStatus.put(row[0].toString(), (Long) row[1]));

        return DashboardDTO.builder()
                .totalStudents(studentRepository.count())
                .activeStudents(studentRepository.countByStatus(StudentStatus.ACTIVE))
                .graduatedStudents(studentRepository.countByStatus(StudentStatus.GRADUATED))
                .suspendedStudents(studentRepository.countByStatus(StudentStatus.SUSPENDED))
                .totalFilieres(filiereRepository.count())
                .totalProfessors(professorRepository.count())
                .totalCourses(courseRepository.count())
                .studentsPerFiliere(studentsPerFiliere)
                .studentsPerLevel(studentsPerLevel)
                .studentsPerStatus(studentsPerStatus)
                .build();
    }
}
