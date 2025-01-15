package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.counselor.CounselorGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorSaveDTO;
import com.jeewaeducation.user_management.service.CounselorService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/counselor")
@CrossOrigin
public class CounselorController {

    @Autowired
    private CounselorService counselorService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> saveCounselor(@RequestBody CounselorSaveDTO counselorSaveDTO){
        String message = counselorService.saveCounselor(counselorSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"success",message), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse> getCounselor(@PathVariable int id){
        CounselorGetDTO counselor = counselorService.getCounselor(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"success",counselor), HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<StandardResponse> getAllCounselors(){
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"success",counselorService.getAllCounselors()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteCounselor(@PathVariable int id){
        String message = counselorService.deleteCounselor(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"success",message), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse> updateCounselor(@RequestBody CounselorSaveDTO counselorSaveDTO,@PathVariable int id){
        String message = counselorService.updateCounselor(counselorSaveDTO,id);
        return new ResponseEntity<>(new StandardResponse(201,"updated",message ),HttpStatus.ACCEPTED);
    }
}
