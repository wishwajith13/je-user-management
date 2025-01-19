package com.jeewaeducation.user_management.dto.reception;

import com.jeewaeducation.user_management.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceptionDTO {
    private int receptionId;
    private String receptionName;
    private String receptionAddress;
    private int receptionContact;
    private String receptionEmail;
    private Branch branch;
}
