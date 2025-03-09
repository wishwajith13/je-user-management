package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionGetDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;
import com.jeewaeducation.user_management.service.ReceptionService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reception")
@CrossOrigin
@RequiredArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;

    @PostMapping
    private ResponseEntity<StandardResponse> saveReception(@Valid @RequestBody ReceptionSaveDTO receptionSaveDTO) {
        String message = receptionService.saveReception(receptionSaveDTO);
        return new ResponseEntity<>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = {"/{id}"}
    )
    private ResponseEntity<StandardResponse> deleteReception(@PathVariable int id) {
        String message = receptionService.deleteReception(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllReception() {
        List<ReceptionDTO> message = receptionService.getAllReception();
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> getReception(@PathVariable int id) {
        ReceptionDTO message = receptionService.getReception(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/branch/{branchId}"}
    )
    public ResponseEntity<StandardResponse> getReceptionsByBranchId(@PathVariable int branchId) {
        List<ReceptionGetDTO> message = receptionService.getReceptionsByBranchId(branchId);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StandardResponse> updateReception(@Valid @RequestBody ReceptionSaveDTO receptionSaveDTO, int receptionId) {
        String message = receptionService.updateReception(receptionSaveDTO, receptionId);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }
}
