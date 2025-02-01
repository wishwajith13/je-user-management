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
public class Counselor extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "counselor_id")
    private int counselorId;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    private Branch branch;

    @Column(name = "counselor_name", nullable = false)
    private String counselorName;

    @Column(name = "counselor_phone_number", nullable = false)
    private String counselorPhoneNumber;

    @Column(name = "counselor_email", nullable = false)
    private String counselorEmail;
}
