package com.jeewaeducation.user_management.controller;


import com.jeewaeducation.user_management.dto.navMenu.NavigationMenuGetDTO;
import com.jeewaeducation.user_management.service.NavigationMenuService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/navigation")
@CrossOrigin
public class NavigationMenuController {

    @Autowired
    private NavigationMenuService navigationMenuService;

    @GetMapping(
            path = {"/get"}
    )
    public ResponseEntity<StandardResponse> getAllNavigationMenu() {
        List<NavigationMenuGetDTO> navigationMenuGetDTO = navigationMenuService.getAllNavigationMenu();
        return new ResponseEntity<StandardResponse>(new StandardResponse(201, "Success", navigationMenuGetDTO), HttpStatus.OK);
    }

}
