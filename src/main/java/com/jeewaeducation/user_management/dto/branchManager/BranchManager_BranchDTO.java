package com.jeewaeducation.user_management.dto.branchManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchManager_BranchDTO {
    private int branchManagerId;
    private String branchManagerName;
    private int branchManagerContactNumber;
    private String branchManagerEmail;
}
