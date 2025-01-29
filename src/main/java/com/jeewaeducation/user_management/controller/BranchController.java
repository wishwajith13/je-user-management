package com.jeewaeducation.user_management.controller;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.dto.branch.Branch_BranchManagerDTO;
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

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> saveBranch(@RequestBody BranchSaveDTO branchSaveDTO) {
        try {
            String message = branchService.saveBranch(branchSaveDTO);
            return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse(500, "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateBranch(@RequestBody BranchDTO branchDTO) {
        try {
            String message = branchService.updateBranch(branchDTO);
            return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse(500, "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<StandardResponse> deleteBranch(@PathVariable int id) {
        String message = branchService.deleteBranch(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(path = {"/getall"})
    public ResponseEntity<StandardResponse> getAllBranch() {
        List<Branch_BranchManagerDTO> message = branchService.getAllBranch();
//        List<BranchDTO> message = branchService.getAllBranch();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse> getBranchById(@PathVariable int id) {
        try {
            Branch_BranchManagerDTO branchDTO = branchService.getBranch(id);
            return new ResponseEntity<>(new StandardResponse(200, "Success", branchDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse(500, "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
