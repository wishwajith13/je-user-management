package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reception {
    @Id
    @Column(name = "reception_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int receptionId;
    @Column(name = "reception_name", nullable = false)
    private String receptionName;
    @Column(name = "reception_address", nullable = false)
    private String receptionAddress;
    @Column(name = "reception_contact", nullable = false)
    private int receptionContact;
    @Column(name = "reception_email", nullable = false)
    private String receptionEmail;
    @OneToMany(mappedBy = "reception")
    private List<Application> applications;
    //branch_id is a foreign key
}
