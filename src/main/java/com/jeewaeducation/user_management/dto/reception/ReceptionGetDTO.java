package com.jeewaeducation.user_management.dto.reception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceptionGetDTO {
    private int receptionId;
    private String receptionName;
    private String receptionAddress;
    private int receptionContact;
    private String receptionEmail;
    private List<Integer> applicationIds;
}
