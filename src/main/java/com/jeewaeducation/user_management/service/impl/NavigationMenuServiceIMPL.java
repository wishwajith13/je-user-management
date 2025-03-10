package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.navMenu.NavigationMenuGetDTO;
import com.jeewaeducation.user_management.entity.MenuVisibleRole;
import com.jeewaeducation.user_management.entity.NavMenu;
import com.jeewaeducation.user_management.repo.NavigationMenuRepository;
import com.jeewaeducation.user_management.service.NavigationMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NavigationMenuServiceIMPL implements NavigationMenuService {

    private NavigationMenuRepository menuRepository;
//    private ModelMapper modelMapper;

    @Override
    public List<NavigationMenuGetDTO> getAllNavigationMenu() {
        List<NavMenu> navMenus = menuRepository.findAll();
        List<NavigationMenuGetDTO> navMenuDTOs = new ArrayList<>();

        for (NavMenu navMenu : navMenus) {
            NavigationMenuGetDTO navMenuDTO = new NavigationMenuGetDTO();
            navMenuDTO.setHref(navMenu.getHref());
            navMenuDTO.setLabel(navMenu.getLabel());
            // Map MenuVisibleRole entities to role names
            List<String> visibleRoles = navMenu.getMenuVisibleRoles().stream()
                    .map(MenuVisibleRole::getRoleName)
                    .collect(Collectors.toList());
            navMenuDTO.setVisible(visibleRoles);
            navMenuDTOs.add(navMenuDTO);
        }

        return navMenuDTOs;
    }
}
