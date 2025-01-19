package com.jeewaeducation.user_management.dto.counselor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorSaveDTO {
    private int branch;
    private String counselorName;
    private String counselorPhoneNumber;
    private String counselorEmail;
}
