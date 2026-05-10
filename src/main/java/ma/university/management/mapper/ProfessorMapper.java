package ma.university.management.mapper;

import ma.university.management.dto.ProfessorDTO;
import ma.university.management.entity.Filiere;
import ma.university.management.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public ProfessorDTO toDto(Professor professor) {
        if (professor == null) return null;

        return ProfessorDTO.builder()
                .id(professor.getId())
                .firstName(professor.getFirstName())
                .lastName(professor.getLastName())
                .email(professor.getEmail())
                .filiereId(professor.getFiliere() != null ? professor.getFiliere().getId() : null)
                .filiereName(professor.getFiliere() != null ? professor.getFiliere().getName() : null)
                .build();
    }

    public Professor toEntity(ProfessorDTO dto) {
        if (dto == null) return null;

        Professor professor = Professor.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();

        if (dto.getFiliereId() != null) {
            Filiere filiere = new Filiere();
            filiere.setId(dto.getFiliereId());
            professor.setFiliere(filiere);
        }

        return professor;
    }

    public void updateEntityFromDto(ProfessorDTO dto, Professor professor) {
        professor.setFirstName(dto.getFirstName());
        professor.setLastName(dto.getLastName());
        professor.setEmail(dto.getEmail());

        if (dto.getFiliereId() != null) {
            Filiere filiere = new Filiere();
            filiere.setId(dto.getFiliereId());
            professor.setFiliere(filiere);
        }
    }
}
