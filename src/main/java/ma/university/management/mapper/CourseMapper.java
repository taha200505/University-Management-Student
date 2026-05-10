package ma.university.management.mapper;

import ma.university.management.dto.CourseDTO;
import ma.university.management.entity.Course;
import ma.university.management.entity.Filiere;
import ma.university.management.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDto(Course course) {
        if (course == null) return null;

        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .code(course.getCode())
                .credits(course.getCredits())
                .professorId(course.getProfessor() != null ? course.getProfessor().getId() : null)
                .professorName(course.getProfessor() != null
                        ? course.getProfessor().getFirstName() + " " + course.getProfessor().getLastName()
                        : null)
                .filiereId(course.getFiliere() != null ? course.getFiliere().getId() : null)
                .filiereName(course.getFiliere() != null ? course.getFiliere().getName() : null)
                .build();
    }

    public Course toEntity(CourseDTO dto) {
        if (dto == null) return null;

        Course course = Course.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .code(dto.getCode())
                .credits(dto.getCredits())
                .build();

        if (dto.getFiliereId() != null) {
            Filiere filiere = new Filiere();
            filiere.setId(dto.getFiliereId());
            course.setFiliere(filiere);
        }
        if (dto.getProfessorId() != null) {
            Professor professor = new Professor();
            professor.setId(dto.getProfessorId());
            course.setProfessor(professor);
        }

        return course;
    }

    public void updateEntityFromDto(CourseDTO dto, Course course) {
        course.setTitle(dto.getTitle());
        course.setCode(dto.getCode());
        course.setCredits(dto.getCredits());

        if (dto.getFiliereId() != null) {
            Filiere filiere = new Filiere();
            filiere.setId(dto.getFiliereId());
            course.setFiliere(filiere);
        }
        if (dto.getProfessorId() != null) {
            Professor professor = new Professor();
            professor.setId(dto.getProfessorId());
            course.setProfessor(professor);
        }
    }
}
