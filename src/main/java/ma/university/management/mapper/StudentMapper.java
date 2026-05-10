package ma.university.management.mapper;

import ma.university.management.dto.StudentDTO;
import ma.university.management.entity.Filiere;
import ma.university.management.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDTO toDto(Student student) {
        if (student == null) return null;

        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .cin(student.getCin())
                .cne(student.getCne())
                .dateOfBirth(student.getDateOfBirth())
                .email(student.getEmail())
                .phone(student.getPhone())
                .address(student.getAddress())
                .registrationDate(student.getRegistrationDate())
                .status(student.getStatus())
                .level(student.getLevel())
                .filiereId(student.getFiliere() != null ? student.getFiliere().getId() : null)
                .filiereName(student.getFiliere() != null ? student.getFiliere().getName() : null)
                .filiereCode(student.getFiliere() != null ? student.getFiliere().getCode() : null)
                .build();
    }

    public Student toEntity(StudentDTO dto) {
        if (dto == null) return null;

        Student student = Student.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .cin(dto.getCin())
                .cne(dto.getCne())
                .dateOfBirth(dto.getDateOfBirth())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .status(dto.getStatus())
                .level(dto.getLevel())
                .build();

        if (dto.getFiliereId() != null) {
            Filiere filiere = new Filiere();
            filiere.setId(dto.getFiliereId());
            student.setFiliere(filiere);
        }

        return student;
    }

    public void updateEntityFromDto(StudentDTO dto, Student student) {
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setCin(dto.getCin());
        student.setCne(dto.getCne());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setStatus(dto.getStatus());
        student.setLevel(dto.getLevel());

        if (dto.getFiliereId() != null) {
            Filiere filiere = new Filiere();
            filiere.setId(dto.getFiliereId());
            student.setFiliere(filiere);
        }
    }
}
