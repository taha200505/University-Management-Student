package ma.university.management.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.university.management.entity.enums.Level;
import ma.university.management.entity.enums.StudentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_cin", columnNames = "cin"),
        @UniqueConstraint(name = "uk_student_cne", columnNames = "cne"),
        @UniqueConstraint(name = "uk_student_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 20)
    private String cin;

    @Column(nullable = false, unique = true, length = 20)
    private String cne;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StudentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_student_filiere"))
    private Filiere filiere;

    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDateTime.now();
        if (this.status == null) {
            this.status = StudentStatus.ACTIVE;
        }
    }
}
