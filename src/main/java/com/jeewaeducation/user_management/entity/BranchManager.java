package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch_manager")

public class BranchManager {
    @Id
    @Column(name = "branch_manager_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int branchManagerId;

    @Column(name = "branch_manager_name", nullable = false)
    private String branchManagerName;

    @Column(name = "branch_manager_contact_number",nullable = false)
    private int branchManagerContactNumber;

    @Column(name = "branch_manager_email", nullable = false)
    private String branchManagerEmail;

    @OneToOne(mappedBy = "branchManager", cascade = CascadeType.ALL)
    private Branch branch;
}
