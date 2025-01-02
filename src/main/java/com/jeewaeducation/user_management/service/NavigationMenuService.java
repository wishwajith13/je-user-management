package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.navMenu.NavigationMenuGetDTO;

import java.util.List;

public interface NavigationMenuService {
    List<NavigationMenuGetDTO> getAllNavigationMenu();
}
