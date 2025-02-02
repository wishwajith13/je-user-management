package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    @Column(name = "student_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentId;
    @Column(name = "Student_rating", nullable = false)
    private String studentRating;
    @Column(name = "student_status", nullable = false)
    private String studentStatus;
    @ManyToOne
    @JoinColumn(name = "counselor_id", referencedColumnName = "counselor_id")
    private Counselor counselorId;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    private Branch branchId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", referencedColumnName = "application_id")
    private Application application;
}
