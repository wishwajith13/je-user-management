package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.application.*;

import java.util.List;

public interface ApplicationService {

    String saveApplication(ApplicationSaveDTO applicationSaveDTO);

    String deleteApplication(int applicationId);

    ApplicationGetDTO getApplication(int applicationId);

    String updateApplication(ApplicationUpdateDTO applicationUpdateDTO);

    String updateApplicationVerification(ApplicationVerificationUpdateDTO applicationVerificationUpdateDTO, int applicationId);

    ApplicationStudentBasicDetailsGetDTO getStudentBasicDetails(int id);

    List<ApplicationStudentBasicDetailsGetDTO> getAllStudentBasicDetails();

    List<ApplicationStudentBasicDetailsGetDTO> getStudentBasicDetailsByReceptionId(int receptionId);

    List<ApplicationStudentBasicDetailsGetDTO> getStudentBasicDetailsByCounselorId(int counselorId);
}
