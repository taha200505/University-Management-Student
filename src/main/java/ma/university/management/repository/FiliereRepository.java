package ma.university.management.repository;

import ma.university.management.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {

    Optional<Filiere> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
