package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "nav_menu")
public class NavMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int menu_id;

    @Column(nullable = false)
    private String href;

    @Column( nullable = false)
    private String label;

}
