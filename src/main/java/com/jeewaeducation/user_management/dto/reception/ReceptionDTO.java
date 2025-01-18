package com.jeewaeducation.user_management.dto.reception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceptionDTO {
    private int receptionId;
    private String receptionName;
    private String receptionAddress;
    private int receptionContact;
    private String receptionEmail;
    //TODO:remove last 3
}
