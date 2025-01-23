package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "branch")

public class Branch {

    @Id
    @Column(name = "branch_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BranchID;

    @Column(name = "branch_name", nullable = false)
    private String BranchName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_manager_id", referencedColumnName = "branch_manager_id")
    private BranchManager branchManager;
    //add to branchManagerId
}
