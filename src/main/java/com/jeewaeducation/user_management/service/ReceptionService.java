package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionGetDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;

import java.util.List;

public interface ReceptionService {
    String saveReception(ReceptionSaveDTO receptionSaveDTO);

    String deleteReception(int id);

    List<ReceptionDTO> getAllReception();

    ReceptionDTO getReception(int id);

    List<ReceptionGetDTO> getReceptionsByBranchId(int branchId);

    String updateReception(ReceptionSaveDTO receptionSaveDTO, int receptionId);
}
