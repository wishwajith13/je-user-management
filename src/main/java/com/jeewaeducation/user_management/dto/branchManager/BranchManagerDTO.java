package com.jeewaeducation.user_management.dto.branchManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BranchManagerDTO {
    private int branchManagerId;
    private String branchManagerName;
    private int branchManagerContactNumber;
    private String branchManagerEmail;
    private int BranchID;
}
