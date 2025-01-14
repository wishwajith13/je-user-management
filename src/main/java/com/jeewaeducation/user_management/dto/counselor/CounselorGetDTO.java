package com.jeewaeducation.user_management.dto.counselor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorGetDTO {
    private int counselorId;
    private int branchId;
    private String counselorName;
    private String counselorPhoneNumber;
}
