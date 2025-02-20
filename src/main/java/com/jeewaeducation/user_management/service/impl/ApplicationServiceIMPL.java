package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationStudentBasicDetailsGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationUpdateDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionForApplicationDTO;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Reception;
import com.jeewaeducation.user_management.entity.Student;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.repo.ReceptionRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import com.jeewaeducation.user_management.service.ApplicationService;
import com.jeewaeducation.user_management.utility.mappers.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceIMPL implements ApplicationService {
    private final ApplicationRepo applicationRepo;
    private final ReceptionRepo receptionRepo;
    private final ModelMapper modelMapper;
    private final ApplicationMapper applicationMapper;
    private final StudentRepo studentRepo;

    @Transactional
    public String saveApplication(ApplicationSaveDTO applicationSaveDTO) {
            Application application = modelMapper.map(applicationSaveDTO, Application.class);

            Reception reception = receptionRepo.findById(applicationSaveDTO.getReception())
                    .orElseThrow(() -> new NotFoundException("Reception not found with ID: " + applicationSaveDTO.getReception()));

            if (applicationRepo.existsById(application.getApplicationId())) {
                throw new DuplicateKeyException("Application already exists with ID: " + application.getApplicationId());
            }

            application.setReception(reception);

            Student student = new Student();
            student.setStudentRating("NA");
            student.setStudentStatus("NA");
            student.setBranchId(reception.getBranch());
            student.setApplication(application);

            studentRepo.save(student);

            Application savedApplication = applicationRepo.save(application);

            return "Application ID: " + savedApplication.getApplicationId() + " and Student ID: " + student.getStudentId() + " Saved";
        }

    @Override
    public String deleteApplication(int applicationId) {
        applicationRepo.findById(applicationId).orElseThrow(() ->
                new NotFoundException("Application not found with ID: " + applicationId));
        applicationRepo.deleteById(applicationId);
        return applicationId + " Deleted";
    }

    @Override
    public ApplicationGetDTO getApplication(int applicationId) {
        Application application = applicationRepo.findById(applicationId).orElseThrow(() -> new NotFoundException("Application not found with ID: " + applicationId));
        ApplicationGetDTO applicationGetDTO = modelMapper.map(application, ApplicationGetDTO.class);
        Reception reception = application.getReception();
        ReceptionForApplicationDTO receptionDTO = modelMapper.map(reception, ReceptionForApplicationDTO.class);//only got id and name
        applicationGetDTO.setReception(receptionDTO);
        return applicationGetDTO;
    }

    @Override
    public String updateApplication(ApplicationUpdateDTO applicationUpdateDTO) { //Not given to update reception details for company security reason
        applicationRepo.findById(applicationUpdateDTO.getApplicationId()).orElseThrow(() ->
                new NotFoundException("Application not found with ID: " + applicationUpdateDTO.getApplicationId()));
        Reception reception = receptionRepo.findById((applicationUpdateDTO.getReception())).orElseThrow(() ->
                new NotFoundException("Reception not found with ID: " + applicationUpdateDTO.getReception()));
        Application application = modelMapper.map(applicationUpdateDTO, Application.class);
        application.setReception(reception);
        applicationRepo.save(application);
        return application.getApplicationId() + "Updated";
    }

    @Override
    public ApplicationStudentBasicDetailsGetDTO getStudentBasicDetails(int id) {
        Application application = applicationRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Application not found with ID: " + id));
        return modelMapper.map(application, ApplicationStudentBasicDetailsGetDTO.class);
    }

    @Override
    public List<ApplicationStudentBasicDetailsGetDTO> getAllStudentBasicDetails() {
        List<Application> applications = applicationRepo.findAll();
        if (applications.isEmpty()) {
            throw new NotFoundException("No applications found");
        }
        return applicationMapper.entityListToDtoList(applications);

    }

}
