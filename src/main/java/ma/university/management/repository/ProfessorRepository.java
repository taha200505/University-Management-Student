package ma.university.management.repository;

import ma.university.management.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByFiliereId(Long filiereId);

    boolean existsByEmail(String email);
}
