package com.jeewaeducation.user_management.service.impl;


import com.jeewaeducation.user_management.dto.student.StudentDTO;
import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.entity.Student;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.CounselorRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import com.jeewaeducation.user_management.service.StudentService;
import com.jeewaeducation.user_management.utility.mappers.StudentMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceIMPL implements StudentService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private CounselorRepo counselorRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public String saveStudent(StudentSaveDTO studentSaveDTO)    {

        Counselor counselor = counselorRepo.findById(studentSaveDTO.getCounselorId()).orElseThrow(() -> new NotFoundException("Counselor not found"));

        Student student = modelMapper.map(studentSaveDTO, Student.class);
        student.setCounselorId(counselor);
        System.out.println(student);
        studentRepo.save(student);
        return "Student ID: " + student.getStudentId() + " Saved";
    }

    @Override
    public String updateStudent(StudentSaveDTO studentSaveDTO, int studentId) {
        Student student = modelMapper.map(studentSaveDTO, Student.class);
        studentRepo.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        student.setStudentId(studentId);
        Counselor counselor = counselorRepo.findById(studentSaveDTO.getCounselorId())
                .orElseThrow(() -> new NotFoundException("Counselor not found with ID: " + studentSaveDTO.getCounselorId()));
        student.setCounselorId(counselor);
        studentRepo.save(student);
        return student.getStudentId() + " Updated";
    }

    @Override
    public String deleteStudent(int id) {
        studentRepo.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        studentRepo.deleteById(id);
        return "Student " + id + " Deleted";
    }

    @Override
    public StudentDTO getStudent(int id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        if (students.isEmpty()) {
            throw new NotFoundException("No students found");
        }
        return studentMapper.entityListToDtoList(students);
    }
}
