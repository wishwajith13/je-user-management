package com.jeewaeducation.user_management.controller;


import com.jeewaeducation.user_management.dto.navMenu.NavigationMenuGetDTO;
import com.jeewaeducation.user_management.service.NavigationMenuService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/navigation")
@CrossOrigin
public class NavigationMenuController {

    @Autowired
    private NavigationMenuService navigationMenuService;

    @PostMapping(
            path = {"/get"}
    )
    public ResponseEntity<StandardResponse> getAllNavigationMenu() {
        NavigationMenuGetDTO navigationMenuGetDTO = navigationMenuService.getAllNavigationMenu();
        return ResponseEntity.ok(new StandardResponse(201, "Success", navigationMenuService.getAllNavigationMenu()));
    }

}
