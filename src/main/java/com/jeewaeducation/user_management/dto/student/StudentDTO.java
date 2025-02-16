package com.jeewaeducation.user_management.dto.student;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorForStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private int studentId;
    private String studentRating;
    private String studentStatus;
    private CounselorForStudentDTO counselorId;
    private BranchGetDTO branchId;
    private ApplicationGetDTO applicationId;
}
