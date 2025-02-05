package com.jeewaeducation.user_management.dto.branch;

import com.jeewaeducation.user_management.dto.branchManager.BranchManager_BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch_BranchManagerDTO {

    private int BranchID;
    private String BranchName;
    private BranchManager_BranchDTO branchManagerId;

}
