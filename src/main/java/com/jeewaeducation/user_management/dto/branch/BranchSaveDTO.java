package com.jeewaeducation.user_management.dto.branch;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchSaveDTO {
    @NotEmpty(message = "Branch Name is required")
    private String branchName;

}
