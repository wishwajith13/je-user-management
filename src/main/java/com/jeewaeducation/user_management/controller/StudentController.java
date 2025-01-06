package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.dto.student.StudentDTO;
import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;
import com.jeewaeducation.user_management.service.StudentService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(
            path = {"/save"}
    )
    public ResponseEntity<StandardResponse> saveStudent(@RequestBody StudentSaveDTO studentSaveDTO) {
        String message = studentService.saveStudent(studentSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @PutMapping(
            path = {"/update"}
    )
    public ResponseEntity<StandardResponse> updateStudent(@RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", studentService.updateStudent(studentDTO)), HttpStatus.OK);
    }

    @DeleteMapping(
            path = {"/delete/{id}"}
    )
    public ResponseEntity<StandardResponse> deleteStudent(@PathVariable int id) {
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", studentService.deleteStudent(id)), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/get/{id}"}
    )
    public ResponseEntity<StandardResponse> getStudent(@PathVariable int id) {
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", studentService.getStudent(id)), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/get-all"}
    )
    public ResponseEntity<StandardResponse> getAllStudents() {
        List<StudentDTO> studentDTO = studentService.getAllStudents();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200, "Success", studentService.getAllStudents()), HttpStatus.OK);
    }
}
