package ma.university.management.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {

    private long totalStudents;
    private long activeStudents;
    private long graduatedStudents;
    private long suspendedStudents;
    private long totalFilieres;
    private long totalProfessors;
    private long totalCourses;

    private Map<String, Long> studentsPerFiliere;
    private Map<String, Long> studentsPerLevel;
    private Map<String, Long> studentsPerStatus;
}
