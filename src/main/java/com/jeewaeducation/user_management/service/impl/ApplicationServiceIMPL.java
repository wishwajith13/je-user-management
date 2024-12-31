package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationUpdateDTO;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceIMPL implements ApplicationService {
    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveApplication(ApplicationSaveDTO applicationSaveDTO) {
        Application application = modelMapper.map(applicationSaveDTO, Application.class);
        if (!applicationRepo.existsById(application.getApplicationId())) {
            applicationRepo.save(application);
            return application.getApplicationId() + " Saved";
        } else {
            throw new DuplicateKeyException("Item already exists");
        }
    }

    @Override
    public String deleteApplication(int applicationId) {
        if (applicationRepo.existsById(applicationId)) {
            applicationRepo.deleteById(applicationId);
            return applicationId + " Deleted";
        } else {
            throw new NotFoundException("Application not found");
        }
    }

    @Override
    public ApplicationGetDTO getApplication(int applicationId) {
        Application application = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found with ID: " + applicationId));
        return modelMapper.map(application, ApplicationGetDTO.class);
    }

    @Override
    public String updateApplication(ApplicationUpdateDTO applicationUpdateDTO) {
        Application application = modelMapper.map(applicationUpdateDTO, Application.class);
        if (applicationRepo.existsById(application.getApplicationId())) {
            applicationRepo.save(application);
            return application.getApplicationId() + "Updated";
        } else {
            throw new NotFoundException("Application not found");
        }
    }
}
