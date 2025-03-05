package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.student.StudentDTO;
import com.jeewaeducation.user_management.dto.student.StudentDetailsUpdateDTo;
import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;
import com.jeewaeducation.user_management.dto.student.StudentUpdateDTO;

import java.util.List;

public interface StudentService {

    String saveStudent(StudentSaveDTO studentSaveDTO);

    String updateStudent(StudentUpdateDTO studentSaveDTO, int studentId);

    String setCounselor(int studentId, int counselor);

    String updateStudentDetails(int studentId, StudentDetailsUpdateDTo studentDetailsUpdateDTo);

    String deleteStudent(int id);

    StudentDTO getStudent(int id);

    List<StudentDTO> getAllStudents();
}
