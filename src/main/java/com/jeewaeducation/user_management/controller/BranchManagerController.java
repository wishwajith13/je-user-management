package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.branchManager.BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerGetDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerSaveDTO;
import com.jeewaeducation.user_management.service.BranchManagerService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/branch_manager")
@CrossOrigin

public class BranchManagerController {
    @Autowired
    private BranchManagerService branchManagerService;

    @PostMapping(
            path = {"/save"}
    )

    private ResponseEntity<StandardResponse> saveBranchManager(BranchManagerSaveDTO branchManagerSaveDTO){
        String message = branchManagerService.saveBranchManager(branchManagerSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"Success",message), HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = {"/delete/{id}"}
    )
    private ResponseEntity<StandardResponse> deleteBranchManager(@PathVariable int id) {
        String message = branchManagerService.deleteBranchManager(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/getall"}
    )
    public ResponseEntity<StandardResponse> getAllBranchManager() {
        List<BranchManagerGetDTO> message = branchManagerService.getAllBranchManager();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @PutMapping(path = {"/update"})
    public ResponseEntity<StandardResponse> updateBranchManager(@RequestBody BranchManagerDTO branchManagerDTO) {
        String message = branchManagerService.updateBranchManager(branchManagerDTO);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/get/{id}"}
    )
    public ResponseEntity<StandardResponse> getBranchManagerById(@PathVariable int id) {
        BranchManagerGetDTO message = branchManagerService.getBranchManagerById(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

}
