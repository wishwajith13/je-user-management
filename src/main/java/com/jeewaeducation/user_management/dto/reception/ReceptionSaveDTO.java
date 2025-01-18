package com.jeewaeducation.user_management.dto.reception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceptionSaveDTO {
    private String receptionName;
    private String receptionAddress;
    private int receptionContact;
    private String receptionEmail;
    private int branchId;
}
