package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.student.*;

import java.util.List;

public interface StudentService {

    String saveStudent(StudentSaveDTO studentSaveDTO);

    String updateStudent(StudentUpdateDTO studentSaveDTO, int studentId);

    String setCounselor(int studentId, int counselor);

    String updateStudentDetails(int studentId, StudentDetailsUpdateDTo studentDetailsUpdateDTo);

    String deleteStudent(int id);

    StudentDTO getStudent(int id);

    StudentGetDTO getStudentBasicDetails(int id);

    List<StudentDTO> getAllStudents();
}
