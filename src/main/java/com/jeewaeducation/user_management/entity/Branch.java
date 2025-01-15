package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Branch {

    @Id
    @Column(name = "branch_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BranchID;

    @Column(name = "branch_name", nullable = false)
    private String BranchName;



    //add to branchManagerId


}
