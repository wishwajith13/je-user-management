package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.counselor.CounselorGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorSaveDTO;

import java.util.List;

public interface CounselorService {
    String saveCounselor(CounselorSaveDTO counselorSaveDTO);

    String deleteCounselor(int counselorId);

    String updateCounselor(CounselorSaveDTO counselorSaveDTO, int counselorId);

    CounselorGetDTO getCounselor(int counselorId);

    List<CounselorGetDTO> getAllCounselors();
}
