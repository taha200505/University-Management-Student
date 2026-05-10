package ma.university.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiliereDTO {

    private Long id;

    @NotBlank(message = "Le nom de la filière est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @NotBlank(message = "Le code de la filière est obligatoire")
    @Size(min = 2, max = 20, message = "Le code doit contenir entre 2 et 20 caractères")
    private String code;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    private long studentCount;
    private long professorCount;
    private long courseCount;
}
