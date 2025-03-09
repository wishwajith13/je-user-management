package com.jeewaeducation.user_management.dto.branch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchDTO {

    private int BranchID;
    @NotEmpty(message = "Branch Name is required")
    private String BranchName;
    @Min(value = 0, message = "Branch ManagerId must be a positive number")
    private int branchManagerId;

}
