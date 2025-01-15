package com.jeewaeducation.user_management.service;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;

import java.util.List;

public interface BranchService {

    String saveBranch(BranchSaveDTO branchSaveDTO);
    String updateBranch(BranchDTO branchDTO);
    String deleteBranch(int id);
    BranchDTO getBranch(int id);
    List<BranchDTO> getAllBranch();

}
