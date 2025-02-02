package com.jeewaeducation.user_management.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateDTO {
    private String studentRating;
    private String studentStatus;
    private int counselorId;
    private int branchId;
}
