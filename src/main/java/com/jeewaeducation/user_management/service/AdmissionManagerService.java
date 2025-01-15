package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerDTO;
import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerSaveDTO;

import java.util.List;

public interface AdmissionManagerService {
    String saveAdmissionManager(AdmissionManagerSaveDTO admissionManagerSaveDTO);

    String deleteAdmissionManager(int id);

    List<AdmissionManagerDTO> getAllAdmissionManager();

    String updateAdmissionManager(AdmissionManagerDTO admissionManagerDTO);

    AdmissionManagerDTO getAdmissionManagerById(int id);
}
