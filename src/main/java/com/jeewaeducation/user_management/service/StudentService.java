package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.student.StudentDTO;
import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;

import java.util.List;

public interface StudentService {

    String saveStudent(StudentSaveDTO studentSaveDTO);

    String updateStudent(StudentDTO studentDTO);

    String deleteStudent(int id);

    StudentDTO getStudent(int id);

    List<StudentDTO> getAllStudents();
}
