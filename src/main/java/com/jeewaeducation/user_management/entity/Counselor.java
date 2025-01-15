package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "counselor")
@Data
public class Counselor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "counselor_id")
    private int counselorId;

    @Column(name = "branch_id")
    private int branchId;

    @Column(name = "counselor_name")
    private String counselorName;

    @Column(name = "counselor_email")
    private String counselorPhoneNumber;
}
