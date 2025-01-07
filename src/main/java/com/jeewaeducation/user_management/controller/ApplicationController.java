package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationStudentBasicDetailsGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationUpdateDTO;
import com.jeewaeducation.user_management.service.ApplicationService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/application")
@CrossOrigin
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping(
            path = {"/save"}
    )
    public ResponseEntity<StandardResponse> saveApplication(@RequestBody ApplicationSaveDTO applicationSaveDTO) {
        String message = applicationService.saveApplication(applicationSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = {"/delete/{id}"}
    )
    public ResponseEntity<StandardResponse> deleteApplication(@PathVariable(value = "id") int applicationId) {
        String message = applicationService.deleteApplication(applicationId);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/get/{id}"}
    )
    public ResponseEntity<StandardResponse> getApplication(@PathVariable(value = "id") int applicationId) {
        ApplicationGetDTO applicationGetDTO = applicationService.getApplication(applicationId);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", applicationGetDTO), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/getStu/{id}"}

    )
    public ResponseEntity<StandardResponse> getStudentBasicDetails(@PathVariable int id) { //retiring basic details of student
        ApplicationStudentBasicDetailsGetDTO applicationStudentBasicDetailsGetDTO = applicationService.getStudentBasicDetails(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", applicationStudentBasicDetailsGetDTO), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/getStuAll"}
    )
    private ResponseEntity<StandardResponse> getAllStudentBasicDetails() { //retiring basic details of student
        List<ApplicationStudentBasicDetailsGetDTO> applicationStudentBasicDetailsGetDTO = applicationService.getAllStudentBasicDetails();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", applicationStudentBasicDetailsGetDTO), HttpStatus.OK);
    }

    @PutMapping(
            path = {"/update"}
    )
    public ResponseEntity<StandardResponse> updateApplication(@RequestBody ApplicationUpdateDTO applicationUpdateDTO) {
        String message = applicationService.updateApplication(applicationUpdateDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }
}
