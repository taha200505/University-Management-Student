package ma.university.management.mapper;

import ma.university.management.dto.FiliereDTO;
import ma.university.management.entity.Filiere;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {

    public FiliereDTO toDto(Filiere filiere) {
        if (filiere == null) return null;

        return FiliereDTO.builder()
                .id(filiere.getId())
                .name(filiere.getName())
                .code(filiere.getCode())
                .description(filiere.getDescription())
                .studentCount(filiere.getStudents() != null ? filiere.getStudents().size() : 0)
                .professorCount(filiere.getProfessors() != null ? filiere.getProfessors().size() : 0)
                .courseCount(filiere.getCourses() != null ? filiere.getCourses().size() : 0)
                .build();
    }

    public FiliereDTO toDtoLight(Filiere filiere) {
        if (filiere == null) return null;

        return FiliereDTO.builder()
                .id(filiere.getId())
                .name(filiere.getName())
                .code(filiere.getCode())
                .description(filiere.getDescription())
                .build();
    }

    public Filiere toEntity(FiliereDTO dto) {
        if (dto == null) return null;

        return Filiere.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .build();
    }

    public void updateEntityFromDto(FiliereDTO dto, Filiere filiere) {
        filiere.setName(dto.getName());
        filiere.setCode(dto.getCode());
        filiere.setDescription(dto.getDescription());
    }
}
