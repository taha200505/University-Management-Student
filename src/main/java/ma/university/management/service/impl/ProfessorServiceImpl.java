package ma.university.management.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.ProfessorDTO;
import ma.university.management.entity.Filiere;
import ma.university.management.entity.Professor;
import ma.university.management.exception.DuplicateResourceException;
import ma.university.management.exception.ResourceNotFoundException;
import ma.university.management.mapper.ProfessorMapper;
import ma.university.management.repository.FiliereRepository;
import ma.university.management.repository.ProfessorRepository;
import ma.university.management.service.ProfessorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@SuppressWarnings("null")
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final FiliereRepository filiereRepository;
    private final ProfessorMapper professorMapper;

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll().stream()
                .map(professorMapper::toDto)
                .toList();
    }

    @Override
    public ProfessorDTO getProfessorById(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professeur", "id", id));
        return professorMapper.toDto(professor);
    }

    @Override
    @Transactional
    public ProfessorDTO createProfessor(ProfessorDTO dto) {
        log.info("Creating professor: {} {}", dto.getFirstName(), dto.getLastName());
        if (professorRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Professeur", "email", dto.getEmail());
        }

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", dto.getFiliereId()));

        Professor professor = professorMapper.toEntity(dto);
        professor.setFiliere(filiere);
        return professorMapper.toDto(professorRepository.save(professor));
    }

    @Override
    @Transactional
    public ProfessorDTO updateProfessor(Long id, ProfessorDTO dto) {
        log.info("Updating professor id: {}", id);
        Professor existing = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professeur", "id", id));

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", dto.getFiliereId()));

        professorMapper.updateEntityFromDto(dto, existing);
        existing.setFiliere(filiere);
        return professorMapper.toDto(professorRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteProfessor(Long id) {
        log.info("Deleting professor id: {}", id);
        if (!professorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Professeur", "id", id);
        }
        professorRepository.deleteById(id);
    }

    @Override
    public List<ProfessorDTO> getProfessorsByFiliere(Long filiereId) {
        return professorRepository.findByFiliereId(filiereId).stream()
                .map(professorMapper::toDto)
                .toList();
    }
}
