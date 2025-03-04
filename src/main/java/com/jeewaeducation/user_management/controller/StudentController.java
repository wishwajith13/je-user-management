package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;
import com.jeewaeducation.user_management.dto.student.StudentUpdateDTO;
import com.jeewaeducation.user_management.service.StudentService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/student")
@CrossOrigin
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveStudent(@RequestBody StudentSaveDTO studentSaveDTO) {
        String message = studentService.saveStudent(studentSaveDTO);
        return new ResponseEntity<>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @PutMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO, @PathVariable int id) {
        String message = studentService.updateStudent(studentUpdateDTO, id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @DeleteMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> deleteStudent(@PathVariable int id) {
        return new ResponseEntity<>(new StandardResponse(200, "Success", studentService.deleteStudent(id)), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/{id}"}
    )
    public ResponseEntity<StandardResponse> getStudent(@PathVariable int id) {
        return new ResponseEntity<>(new StandardResponse(200, "Success", studentService.getStudent(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllStudents() {
        return new ResponseEntity<>(new StandardResponse(200, "Success", studentService.getAllStudents()), HttpStatus.OK);
    }

    @PutMapping(
            path = {"/setCounselor/{studentId}/{counselorId}"}
    )
    public ResponseEntity<StandardResponse> setCounselor(@PathVariable int studentId, @PathVariable int counselorId) {
        return new ResponseEntity<>(new StandardResponse(200, "Success", studentService.setCounselor(studentId, counselorId)), HttpStatus.OK);
    }
}
