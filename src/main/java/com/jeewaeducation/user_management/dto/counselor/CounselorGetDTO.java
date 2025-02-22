package com.jeewaeducation.user_management.dto.counselor;

import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorGetDTO {
    private int counselorId;
    private BranchGetDTO branch;
    private String counselorName;
    private String counselorPhoneNumber;
    private String counselorEmail;
}
