package ma.university.management.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.FiliereDTO;
import ma.university.management.entity.Filiere;
import ma.university.management.exception.DuplicateResourceException;
import ma.university.management.exception.ResourceNotFoundException;
import ma.university.management.mapper.FiliereMapper;
import ma.university.management.repository.FiliereRepository;
import ma.university.management.service.FiliereService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@SuppressWarnings("null")
public class FiliereServiceImpl implements FiliereService {

    private final FiliereRepository filiereRepository;
    private final FiliereMapper filiereMapper;

    @Override
    public List<FiliereDTO> getAllFilieres() {
        return filiereRepository.findAll().stream()
                .map(filiereMapper::toDto)
                .toList();
    }

    @Override
    public FiliereDTO getFiliereById(Long id) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", id));
        return filiereMapper.toDto(filiere);
    }

    @Override
    @Transactional
    public FiliereDTO createFiliere(FiliereDTO dto) {
        log.info("Creating filière: {}", dto.getCode());
        if (filiereRepository.existsByCode(dto.getCode())) {
            throw new DuplicateResourceException("Filière", "code", dto.getCode());
        }
        Filiere filiere = filiereMapper.toEntity(dto);
        return filiereMapper.toDto(filiereRepository.save(filiere));
    }

    @Override
    @Transactional
    public FiliereDTO updateFiliere(Long id, FiliereDTO dto) {
        log.info("Updating filière id: {}", id);
        Filiere existing = filiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filière", "id", id));

        filiereRepository.findByCode(dto.getCode()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new DuplicateResourceException("Filière", "code", dto.getCode());
            }
        });

        filiereMapper.updateEntityFromDto(dto, existing);
        return filiereMapper.toDto(filiereRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteFiliere(Long id) {
        log.info("Deleting filière id: {}", id);
        if (!filiereRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filière", "id", id);
        }
        filiereRepository.deleteById(id);
    }
}
