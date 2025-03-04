package com.jeewaeducation.user_management.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentBasicDetailsGetDTO {
    private int studentId;
    private String studentRating;
    private String studentStatus;
}
