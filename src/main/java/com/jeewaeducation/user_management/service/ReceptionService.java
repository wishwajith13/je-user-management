package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;

import java.util.List;

public interface ReceptionService {
    String saveReception(ReceptionSaveDTO receptionSaveDTO);

    String deleteReception(int id);

    List<ReceptionDTO> getAllReception();

    ReceptionDTO getReception(int id);

    String updateReception(ReceptionSaveDTO receptionSaveDTO, int receptionId);
}
