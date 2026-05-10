package ma.university.management.service;

import ma.university.management.dto.FiliereDTO;

import java.util.List;

public interface FiliereService {

    List<FiliereDTO> getAllFilieres();

    FiliereDTO getFiliereById(Long id);

    FiliereDTO createFiliere(FiliereDTO dto);

    FiliereDTO updateFiliere(Long id, FiliereDTO dto);

    void deleteFiliere(Long id);
}
