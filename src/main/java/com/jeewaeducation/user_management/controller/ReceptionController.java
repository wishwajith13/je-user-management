package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;
import com.jeewaeducation.user_management.service.ReceptionService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reception")
@CrossOrigin
public class ReceptionController {
    @Autowired
    private ReceptionService receptionService;

    @PostMapping(
            path = {"/save"}
    )
    private ResponseEntity<StandardResponse> saveReception(ReceptionSaveDTO receptionSaveDTO) {
        String message = receptionService.saveReception(receptionSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = {"/delete/{id}"}
    )
    private ResponseEntity<StandardResponse> deleteReception(@PathVariable int id) {
        String message = receptionService.deleteReception(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/getall"}
    )
    public ResponseEntity<StandardResponse> getAllReception() {
        List<ReceptionDTO> message = receptionService.getAllReception();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/get/{id}"}
    )
    public ResponseEntity<StandardResponse> getReception(@PathVariable int id) {
        ReceptionDTO message = receptionService.getReception(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @PutMapping(
            path = {"/update"}
    )
    public ResponseEntity<StandardResponse> updateReception(@RequestBody ReceptionSaveDTO receptionSaveDTO, int receptionId) {
        String message = receptionService.updateReception(receptionSaveDTO, receptionId);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }
}
