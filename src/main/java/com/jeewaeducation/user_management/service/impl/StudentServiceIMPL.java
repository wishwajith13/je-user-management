package com.jeewaeducation.user_management.service.impl;


import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorGetDTO;
import com.jeewaeducation.user_management.dto.student.StudentDTO;
import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.entity.Student;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.repo.CounselorRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import com.jeewaeducation.user_management.service.StudentService;
import com.jeewaeducation.user_management.utility.mappers.StudentMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private BranchRepo branchRepo;

    @Override
    public String saveStudent(StudentSaveDTO studentSaveDTO) {
            Counselor counselor = counselorRepo.findById(studentSaveDTO.getCounselorId())
                    .orElseThrow(() -> new NotFoundException("Counselor not found"));
            Branch branch = branchRepo.findById(studentSaveDTO.getBranchId())
                    .orElseThrow(() -> new NotFoundException("Branch not found"));
            Student student = new Student();
            student.setStudentRating(studentSaveDTO.getStudentRating());
            student.setStudentStatus(studentSaveDTO.getStudentStatus());
            student.setCounselorId(counselor);
            student.setBranchId(branch);
            student.setStudentId(0);
            System.out.println(student);
            studentRepo.save(student);
            return "Student ID: " + student.getStudentId() + " Saved";
    }

    @Override
    public String updateStudent(StudentSaveDTO studentSaveDTO, int studentId) {
        studentRepo.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        Counselor counselor = counselorRepo.findById(studentSaveDTO.getCounselorId())
                .orElseThrow(() -> new NotFoundException("Counselor not found with ID: " + studentSaveDTO.getCounselorId()));
        Branch branch = branchRepo.findById(studentSaveDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found with ID: " + studentSaveDTO.getBranchId()));
        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentRating(studentSaveDTO.getStudentRating());
        student.setStudentStatus(studentSaveDTO.getStudentStatus());
        student.setCounselorId(counselor);
        student.setBranchId(branch);
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
        return students.stream().map(student -> {
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
            if (student.getBranchId() != null) {
                studentDTO.setBranchId(modelMapper.map(student.getBranchId(), BranchGetDTO.class));
            }
            if (student.getCounselorId() != null) {
                studentDTO.setCounselorId(modelMapper.map(student.getCounselorId(), CounselorGetDTO.class));
            }
            return studentDTO;
        }).collect(Collectors.toList());
    }
}
