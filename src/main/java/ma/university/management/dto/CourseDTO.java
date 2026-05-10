package ma.university.management.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 2, max = 150)
    private String title;

    @NotBlank(message = "Le code est obligatoire")
    @Size(min = 2, max = 20)
    private String code;

    @NotNull(message = "Le nombre de crédits est obligatoire")
    @Min(value = 1, message = "Minimum 1 crédit")
    @Max(value = 30, message = "Maximum 30 crédits")
    private Integer credits;

    private Long professorId;
    private String professorName;

    @NotNull(message = "La filière est obligatoire")
    private Long filiereId;
    private String filiereName;
}
