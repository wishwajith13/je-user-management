package com.jeewaeducation.user_management.dto.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationStudentBasicDetailsGetDTO {
    private String familyName;
    private int mobileContactNumber;
    private String email;
}
