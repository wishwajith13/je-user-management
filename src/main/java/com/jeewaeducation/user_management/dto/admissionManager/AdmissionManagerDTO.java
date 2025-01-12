package com.jeewaeducation.user_management.dto.admissionManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdmissionManagerDTO {
    private int admissionManagerId;
    private String admissionManagerName;
    private int admissionManagerContactNumber;
    private String admissionManagerEmail;
}
