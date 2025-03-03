package com.jeewaeducation.user_management.dto.branch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchGetDTO {
    private int id;
    private String branchName;
}
