package com.jeewaeducation.user_management.dto.branchManager;

import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchManagerGetDTO {
    private int branchManagerId;
    private String branchManagerName;
    private int branchManagerContactNumber;
    private String branchManagerEmail;
    private BranchGetDTO BranchID;
}
