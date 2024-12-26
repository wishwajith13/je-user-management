package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        if(!applicationRepo.existsById(application.getApplicationId())){
            applicationRepo.save(application);
            return application.getApplicationId() + " Saved";
        }else {
            throw new DuplicateKeyException("Item already exists");
        }
    }
}
