package com.jeewaeducation.user_management.controller;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.service.BranchService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/branch")
@CrossOrigin

public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping(path = {"/save"})
    public ResponseEntity<StandardResponse> saveBranch(@RequestBody BranchSaveDTO branchSaveDTO) {
        String message = branchService.saveBranch(branchSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @PutMapping(path = {"/update"})
    public ResponseEntity<StandardResponse> updateBranch(@RequestBody BranchDTO branchDTO) {
        String message = branchService.updateBranch(branchDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<StandardResponse> deleteBranch(@PathVariable int id) {
        String message = branchService.deleteBranch(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(path = {"/getall"})
    public ResponseEntity<StandardResponse> getAllBranch() {
        List<BranchDTO> message = branchService.getAllBranch();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(path = {"/get/{id}"})
    public ResponseEntity<StandardResponse> getBranch(@PathVariable int id) {
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", branchService.getBranch(id)), HttpStatus.OK);
    }




}
