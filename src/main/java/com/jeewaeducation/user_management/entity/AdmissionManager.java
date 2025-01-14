package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdmissionManager {
    @Id
    @Column(name = "admission_manager_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int admissionManagerId;
    @Column(name = "admission_manager_name", nullable = false)
    private String admissionManagerName;
    @Column(name = "admission_manager_contact_number",nullable = false)
    private int admissionManagerContactNumber;
    @Column(name = "admission_manager_email", nullable = false)
    private String admissionManagerEmail;
}
