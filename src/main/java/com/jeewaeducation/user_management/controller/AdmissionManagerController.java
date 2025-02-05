package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerDTO;
import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerSaveDTO;
import com.jeewaeducation.user_management.service.AdmissionManagerService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admission-manager")
@CrossOrigin

public class AdmissionManagerController {
    @Autowired
    private AdmissionManagerService admissionManagerService;

    @PostMapping
    private ResponseEntity<StandardResponse> saveAdmissionManager(AdmissionManagerSaveDTO admissionManagerSaveDTO) {
        String message = admissionManagerService.saveAdmissionManager(admissionManagerSaveDTO);
        return new ResponseEntity<>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = {"/{id}"}
    )
    private ResponseEntity<StandardResponse> deleteAdmissionManager(@PathVariable int id) {
        String message = admissionManagerService.deleteAdmissionManager(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllAdmissionManager() {
        List<AdmissionManagerDTO> message = admissionManagerService.getAllAdmissionManager();
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StandardResponse> updateAdmissionManager(@RequestBody AdmissionManagerDTO admissionManagerDTO) {
        String message = admissionManagerService.updateAdmissionManager(admissionManagerDTO);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> getAdmissionManagerById(@PathVariable int id) {
        AdmissionManagerDTO message = admissionManagerService.getAdmissionManagerById(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }
}
