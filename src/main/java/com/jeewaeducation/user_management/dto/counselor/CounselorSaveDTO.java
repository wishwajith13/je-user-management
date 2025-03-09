package com.jeewaeducation.user_management.dto.counselor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorSaveDTO {
    private int branch;
    @NotEmpty(message = "Counselor Name is required")
    private String counselorName;
    @NotEmpty(message = "Mobile contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile contact number must be 10 digits")
    private String counselorPhoneNumber;
    @Email(message = "Email should be valid")
    private String counselorEmail;
}
