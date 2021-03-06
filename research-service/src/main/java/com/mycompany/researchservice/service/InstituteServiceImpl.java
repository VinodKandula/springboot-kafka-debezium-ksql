package com.mycompany.researchservice.service;

import com.mycompany.researchservice.exception.InstituteDeletionException;
import com.mycompany.researchservice.exception.InstituteNotFoundException;
import com.mycompany.researchservice.model.Institute;
import com.mycompany.researchservice.repository.InstituteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteServiceImpl implements InstituteService {

    private final InstituteRepository instituteRepository;

    public InstituteServiceImpl(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    @Override
    public List<Institute> getAllInstitutes() {
        return instituteRepository.findAll();
    }

    @Override
    public Institute validateAndGetInstitute(Long id) {
        return instituteRepository.findById(id)
                .orElseThrow(() -> new InstituteNotFoundException(String.format("Institute with id %s not found", id)));
    }

    @Override
    public Institute saveInstitute(Institute institute) {
        return instituteRepository.save(institute);
    }

    @Override
    public void deleteInstitute(Institute institute) {
        try {
            instituteRepository.delete(institute);
        } catch (DataIntegrityViolationException e) {
            throw new InstituteDeletionException(String.format("Institute with id %s cannot be deleted", institute.getId()));
        }
    }
}
