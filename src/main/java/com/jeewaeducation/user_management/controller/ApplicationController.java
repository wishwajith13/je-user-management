package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.application.*;
import com.jeewaeducation.user_management.service.ApplicationService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/application")
@CrossOrigin
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveApplication(@Valid @RequestBody ApplicationSaveDTO applicationSaveDTO) {
        String message = applicationService.saveApplication(applicationSaveDTO);
        return new ResponseEntity<>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> deleteApplication(@PathVariable(value = "id") int applicationId) {
        String message = applicationService.deleteApplication(applicationId);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> getApplication(@PathVariable(value = "id") int applicationId) {
        ApplicationGetDTO applicationGetDTO = applicationService.getApplication(applicationId);
        return new ResponseEntity<>(new StandardResponse(200, "Success", applicationGetDTO), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/basic/{id}"}
    )
    public ResponseEntity<StandardResponse> getStudentBasicDetails(@PathVariable int id) { //retiring basic details of student
        ApplicationStudentBasicDetailsGetDTO applicationStudentBasicDetailsGetDTO = applicationService.getStudentBasicDetails(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", applicationStudentBasicDetailsGetDTO), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/basic"}
    )
    private ResponseEntity<StandardResponse> getAllStudentBasicDetails() { //retiring basic details of student
        List<ApplicationStudentBasicDetailsGetDTO> applicationStudentBasicDetailsGetDTO = applicationService.getAllStudentBasicDetails();
        return new ResponseEntity<>(new StandardResponse(200, "Success", applicationStudentBasicDetailsGetDTO), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/basic/reception/{id}"}
    )
    private ResponseEntity<StandardResponse> getStudentBasicDetailsByReceptionId(@PathVariable int id) { 
        List<ApplicationStudentBasicDetailsGetDTO> applicationStudentBasicDetailsGetDTO = applicationService.getStudentBasicDetailsByReceptionId(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", applicationStudentBasicDetailsGetDTO), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/basic/counselor/{id}"}
    )
    private ResponseEntity<StandardResponse> getStudentBasicDetailsByCounselorId(@PathVariable int id) {
        List<ApplicationStudentBasicDetailsGetDTO> applicationStudentBasicDetailsGetDTO = applicationService.getStudentBasicDetailsByCounselorId(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", applicationStudentBasicDetailsGetDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StandardResponse> updateApplication(@Valid @RequestBody ApplicationUpdateDTO applicationUpdateDTO) {
        String message = applicationService.updateApplication(applicationUpdateDTO);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @PutMapping("/verification/{id}")
    public  ResponseEntity<StandardResponse> updateApplicationVerification(@PathVariable int id, @RequestBody ApplicationVerificationUpdateDTO applicationVerificationUpdateDTO) {
        String message = applicationService.updateApplicationVerification(applicationVerificationUpdateDTO, id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }
}
