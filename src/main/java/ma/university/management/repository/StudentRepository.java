package ma.university.management.repository;

import ma.university.management.entity.Student;
import ma.university.management.entity.enums.Level;
import ma.university.management.entity.enums.StudentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByCin(String cin);

    Optional<Student> findByCne(String cne);

    boolean existsByCin(String cin);

    boolean existsByCne(String cne);

    boolean existsByEmail(String email);

    Page<Student> findByFiliereId(Long filiereId, Pageable pageable);

    Page<Student> findByLevel(Level level, Pageable pageable);

    Page<Student> findByFiliereIdAndLevel(Long filiereId, Level level, Pageable pageable);

    Page<Student> findByStatus(StudentStatus status, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.cin) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.cne) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Student> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " +
           "(:filiereId IS NULL OR s.filiere.id = :filiereId) AND " +
           "(:level IS NULL OR s.level = :level) AND " +
           "(:status IS NULL OR s.status = :status)")
    Page<Student> findByFilters(@Param("filiereId") Long filiereId,
                                @Param("level") Level level,
                                @Param("status") StudentStatus status,
                                Pageable pageable);

    // Dashboard statistics
    long countByStatus(StudentStatus status);

    long countByLevel(Level level);

    long countByFiliereId(Long filiereId);

    @Query("SELECT s.filiere.name, COUNT(s) FROM Student s GROUP BY s.filiere.name")
    List<Object[]> countStudentsPerFiliere();

    @Query("SELECT s.level, COUNT(s) FROM Student s GROUP BY s.level")
    List<Object[]> countStudentsPerLevel();

    @Query("SELECT s.status, COUNT(s) FROM Student s GROUP BY s.status")
    List<Object[]> countStudentsPerStatus();

    List<Student> findAllByFiliereId(Long filiereId);

    List<Student> findAllByLevel(Level level);
}
