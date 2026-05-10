package ma.university.management.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import ma.university.management.entity.enums.Level;
import ma.university.management.entity.enums.StudentStatus;
import ma.university.management.validation.MoroccanCIN;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String lastName;

    @NotBlank(message = "Le CIN est obligatoire")
    @MoroccanCIN
    private String cin;

    @NotBlank(message = "Le CNE est obligatoire")
    @Pattern(regexp = "^[A-Z]\\d{9}$", message = "Le CNE doit suivre le format: une lettre majuscule suivie de 9 chiffres")
    private String cne;

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateOfBirth;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    private String email;

    @Pattern(regexp = "^(\\+212|0)[5-7]\\d{8}$", message = "Le numéro de téléphone marocain n'est pas valide")
    private String phone;

    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    private String address;

    private LocalDateTime registrationDate;

    @NotNull(message = "Le statut est obligatoire")
    private StudentStatus status;

    @NotNull(message = "Le niveau est obligatoire")
    private Level level;

    @NotNull(message = "La filière est obligatoire")
    private Long filiereId;

    // Display-only fields
    private String filiereName;
    private String filiereCode;
}
