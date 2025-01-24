package com.jeewaeducation.user_management.dto.student;

import com.jeewaeducation.user_management.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSaveDTO {
    private String studentRating;
    private String studentStatus;
    private int counselorId;
    private int branchId;
    private Application applicationId;
}
