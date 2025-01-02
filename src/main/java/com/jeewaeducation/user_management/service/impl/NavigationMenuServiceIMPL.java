package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.navMenu.NavigationMenuGetDTO;
import com.jeewaeducation.user_management.repo.NavigationMenuRepository;
import com.jeewaeducation.user_management.service.NavigationMenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NavigationMenuServiceIMPL implements NavigationMenuService {

    @Autowired
    private NavigationMenuRepository menuRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NavigationMenuGetDTO getAllNavigationMenu() {
        return  menuRepository.findAll().stream().map(menu -> modelMapper.map(menu, NavigationMenuGetDTO.class)).findFirst().orElse(null);
    }
}
