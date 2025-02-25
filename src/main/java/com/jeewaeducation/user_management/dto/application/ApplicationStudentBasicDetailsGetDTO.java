package com.jeewaeducation.user_management.dto.application;

import com.jeewaeducation.user_management.dto.reception.ReceptionForApplicationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationStudentBasicDetailsGetDTO {
    private int applicationId;
    private Date applicationDate;
    private String title;
    private String familyName;
    private String givenName;
    private int mobileContactNumber;
    private int homeContactNumber;
    private String email;
    private boolean isVerified;
    private ReceptionForApplicationDTO reception;
}
