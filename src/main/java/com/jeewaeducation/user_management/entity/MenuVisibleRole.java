package com.jeewaeducation.user_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "menu_visible_role")
public class MenuVisibleRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;

    private String roleName;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private NavMenu navMenu;
}
