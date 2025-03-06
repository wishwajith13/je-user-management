package com.jeewaeducation.user_management.dto.student;

import com.jeewaeducation.user_management.dto.counselor.CounselorForStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentGetDTO {
    private int studentId;
    private int applicationId;
    private String title;
    private String givenName;
    private String familyName;
    private String email;
    private String mobileContactNumber;
    private String homeContactNumber;
    private CounselorForStudentDTO counselor;
}
