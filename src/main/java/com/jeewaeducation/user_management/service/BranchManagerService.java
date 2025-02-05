package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.branchManager.BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerGetDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerSaveDTO;

import java.util.List;

public interface BranchManagerService {
    String saveBranchManager(BranchManagerSaveDTO branchManagerSaveDTO);

    String deleteBranchManager(int id);

    List<BranchManagerGetDTO> getAllBranchManager();

    String updateBranchManager(BranchManagerDTO branchManagerDTO);

    BranchManagerGetDTO getBranchManagerById(int id);
}
