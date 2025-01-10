package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationStudentBasicDetailsGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationUpdateDTO;

import java.util.List;

public interface ApplicationService {

    String saveApplication(ApplicationSaveDTO applicationSaveDTO);

    String deleteApplication(int applicationId);

    ApplicationGetDTO getApplication(int applicationId);

    String updateApplication(ApplicationUpdateDTO applicationUpdateDTO);

    ApplicationStudentBasicDetailsGetDTO getStudentBasicDetails(int id);

    List<ApplicationStudentBasicDetailsGetDTO> getAllStudentBasicDetails();
}
