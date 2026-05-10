package ma.university.management.service;

import ma.university.management.dto.ProfessorDTO;

import java.util.List;

public interface ProfessorService {

    List<ProfessorDTO> getAllProfessors();

    ProfessorDTO getProfessorById(Long id);

    ProfessorDTO createProfessor(ProfessorDTO dto);

    ProfessorDTO updateProfessor(Long id, ProfessorDTO dto);

    void deleteProfessor(Long id);

    List<ProfessorDTO> getProfessorsByFiliere(Long filiereId);
}
