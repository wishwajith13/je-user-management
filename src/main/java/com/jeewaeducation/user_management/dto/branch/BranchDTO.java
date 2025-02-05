package com.jeewaeducation.user_management.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchDTO {

    private int BranchID;
    private String BranchName;
    private int branchManagerId;

}
