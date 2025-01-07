package com.jeewaeducation.user_management.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentBasicDetailsGetDTO {
    private String familyName;
    private int mobileContactNumber;
    private String email;
}
