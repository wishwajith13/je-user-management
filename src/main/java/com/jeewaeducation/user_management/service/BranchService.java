package com.jeewaeducation.user_management.service;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.dto.branch.Branch_BranchManagerDTO;

import java.util.List;

public interface BranchService {

    String saveBranch(BranchSaveDTO branchSaveDTO) throws Exception;
    String updateBranch(BranchDTO branchDTO) throws Exception;
    String deleteBranch(int id);
    Branch_BranchManagerDTO getBranch(int id) throws Exception;
    List<Branch_BranchManagerDTO> getAllBranch();

}
