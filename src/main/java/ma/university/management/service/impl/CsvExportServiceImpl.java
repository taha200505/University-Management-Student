package ma.university.management.service.impl;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.StudentDTO;
import ma.university.management.service.CsvExportService;
import org.springframework.stereotype.Service;

import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class CsvExportServiceImpl implements CsvExportService {

    private static final String[] CSV_HEADER = {
            "ID", "Prénom", "Nom", "CIN", "CNE", "Date de naissance",
            "Email", "Téléphone", "Adresse", "Statut", "Niveau",
            "Filière", "Date d'inscription"
    };

    @Override
    public void exportStudentsToCsv(List<StudentDTO> students, Writer writer) {
        log.info("Exporting {} students to CSV", students.size());

        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(CSV_HEADER);

            for (StudentDTO s : students) {
                String[] row = {
                        s.getId() != null ? s.getId().toString() : "",
                        s.getFirstName(),
                        s.getLastName(),
                        s.getCin(),
                        s.getCne(),
                        s.getDateOfBirth() != null ? s.getDateOfBirth().toString() : "",
                        s.getEmail(),
                        s.getPhone() != null ? s.getPhone() : "",
                        s.getAddress() != null ? s.getAddress() : "",
                        s.getStatus() != null ? s.getStatus().name() : "",
                        s.getLevel() != null ? s.getLevel().name() : "",
                        s.getFiliereName() != null ? s.getFiliereName() : "",
                        s.getRegistrationDate() != null ? s.getRegistrationDate().toString() : ""
                };
                csvWriter.writeNext(row);
            }
        } catch (Exception e) {
            log.error("Error exporting CSV", e);
            throw new RuntimeException("Erreur lors de l'export CSV", e);
        }
    }
}
