package ma.university.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professors", uniqueConstraints = {
        @UniqueConstraint(name = "uk_professor_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_professor_filiere"))
    private Filiere filiere;
}
