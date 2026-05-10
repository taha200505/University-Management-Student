package ma.university.management.service;

import ma.university.management.dto.StudentDTO;

import java.io.Writer;
import java.util.List;

public interface CsvExportService {

    void exportStudentsToCsv(List<StudentDTO> students, Writer writer);
}
