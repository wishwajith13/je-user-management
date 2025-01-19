package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.branchManager.BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerSaveDTO;

import java.util.List;

public interface BranchManagerService {
    String saveBranchManager(BranchManagerSaveDTO branchManagerSaveDTO);

    String deleteBranchManager(int id);

    List<BranchManagerDTO> getAllBranchManager();

    String updateBranchManager(BranchManagerDTO branchManagerDTO);

    BranchManagerDTO getBranchManagerById(int id);
}
