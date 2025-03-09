package com.jeewaeducation.user_management.dto.reception;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceptionSaveDTO {
    private int receptionId;
    @NotEmpty(message = "Reception Name is required")
    private String receptionName;
    @NotEmpty(message = "Reception Name is required")
    private String receptionAddress;
    @NotNull(message = "Reception Contact contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile contact number must be 10 digits")
    private String receptionContact;
    @Email(message = "Email should be valid")
    private String receptionEmail;
    @Min(value = 0, message = "Branch Id must be a positive number")
    private int branchId;
}
