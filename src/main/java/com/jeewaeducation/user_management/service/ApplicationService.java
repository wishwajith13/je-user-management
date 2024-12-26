package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;

public interface ApplicationService {

    String saveApplication(ApplicationSaveDTO applicationSaveDTO);

    String deleteApplication(int applicationId);

    ApplicationGetDTO getApplication(int applicationId);
}
