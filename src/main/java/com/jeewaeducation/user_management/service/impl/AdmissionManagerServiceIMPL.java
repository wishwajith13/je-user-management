package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerDTO;
import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerSaveDTO;
import com.jeewaeducation.user_management.entity.AdmissionManager;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.AdmissionManagerRepo;
import com.jeewaeducation.user_management.service.AdmissionManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jeewaeducation.user_management.utility.mappers.AdmissionManagerMapper;

import java.util.List;

@Service
public class AdmissionManagerServiceIMPL implements AdmissionManagerService {
    @Autowired
    private AdmissionManagerRepo admissionManagerRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AdmissionManagerMapper admissionManagerMapper;

    @Override
    public String saveAdmissionManager(AdmissionManagerSaveDTO admissionManagerSaveDTO) {
        AdmissionManager admissionManager = modelMapper.map(admissionManagerSaveDTO, AdmissionManager.class);
        admissionManagerRepo.findById(admissionManager.getAdmissionManagerId()).ifPresent(e -> {
            throw new DuplicateKeyException("AdmissionManager already exists");
        });
        admissionManagerRepo.save(admissionManager);
        return admissionManager.getAdmissionManagerId() + " Saved";
    }

    @Override
    public String deleteAdmissionManager(int id) {
        admissionManagerRepo.findById(id).orElseThrow(() -> new NotFoundException("AdmissionManager not found"));
        admissionManagerRepo.deleteById(id);
        return "AdmissionManager " + id + " Deleted";
    }

    @Override
    public List<AdmissionManagerDTO> getAllAdmissionManager() {
        List<AdmissionManager> admissionManagers = admissionManagerRepo.findAll();
        if (admissionManagers.isEmpty()) {
            throw new NotFoundException("No Admission Manager found");
        }
        return admissionManagerMapper.entityListToDtoList(admissionManagers);
    }

    @Override
    public String updateAdmissionManager(AdmissionManagerDTO admissionManagerDTO) {
        AdmissionManager admissionManager = modelMapper.map(admissionManagerDTO, AdmissionManager.class);
        admissionManagerRepo.findById(admissionManager.getAdmissionManagerId()).orElseThrow(() -> new NotFoundException("AdmissionManager not found"));
        admissionManagerRepo.save(admissionManager);
        return admissionManager.getAdmissionManagerId() + " Updated";
    }

    @Override
    public AdmissionManagerDTO getAdmissionManagerById(int id) {
        AdmissionManager admissionManager = admissionManagerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("AdmissionManager not found"));
        return modelMapper.map(admissionManager, AdmissionManagerDTO.class);
    }
}