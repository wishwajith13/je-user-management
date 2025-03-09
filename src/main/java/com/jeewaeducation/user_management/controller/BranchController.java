package com.jeewaeducation.user_management.controller;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.dto.branch.Branch_BranchManagerDTO;
import com.jeewaeducation.user_management.service.BranchService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/branch")
@CrossOrigin
@AllArgsConstructor
public class BranchController {

    private BranchService branchService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveBranch(@Valid @RequestBody BranchSaveDTO branchSaveDTO) {
        try {
            return new ResponseEntity<>(new StandardResponse(200, "Success", branchService.saveBranch(branchSaveDTO)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse(500, "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<StandardResponse> updateBranch(@Valid @RequestBody BranchDTO branchDTO) {
        try {
            return new ResponseEntity<>(new StandardResponse(200, "Success", branchService.updateBranch(branchDTO)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse(500, "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<StandardResponse> deleteBranch(@PathVariable int id) {
        return new ResponseEntity<>(new StandardResponse(200, "Success", branchService.deleteBranch(id)), HttpStatus.OK);
    }

    @GetMapping
        public ResponseEntity<StandardResponse> getAllBranch() {
        return new ResponseEntity<>(new StandardResponse(200, "Success", branchService.getAllBranch()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getBranchById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(new StandardResponse(200, "Success", branchService.getBranch(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse(500, "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
