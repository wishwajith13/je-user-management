package com.jeewaeducation.user_management.dto.application;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationVerificationUpdateDTO {
    private boolean isVerified;
}
