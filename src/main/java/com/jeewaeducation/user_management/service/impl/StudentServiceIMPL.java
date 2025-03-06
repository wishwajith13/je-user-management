package com.jeewaeducation.user_management.service.impl;


import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorForStudentDTO;
import com.jeewaeducation.user_management.dto.student.*;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.entity.Student;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.repo.CounselorRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import com.jeewaeducation.user_management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceIMPL implements StudentService {
    private final StudentRepo studentRepo;
    private final CounselorRepo counselorRepo;
    private final ModelMapper modelMapper;
    private final BranchRepo branchRepo;
    private final ApplicationRepo applicationRepo;

    @Transactional
        public String saveStudent(StudentSaveDTO studentSaveDTO) {
            Counselor counselor = counselorRepo.findById(studentSaveDTO.getCounselorId())
                    .orElseThrow(() -> new NotFoundException("Counselor not found"));
            Branch branch = branchRepo.findById(studentSaveDTO.getBranchId())
                    .orElseThrow(() -> new NotFoundException("Branch not found"));
            Application application = applicationRepo.findById(studentSaveDTO.getApplicationId())
                    .orElseThrow(() -> new NotFoundException("Application not found"));
            if(studentRepo.existsByApplication(application)){
                throw new NotFoundException("Student already exists with application ID: " + studentSaveDTO.getApplicationId());
            }
        Student student = new Student();
        student.setStudentRating(studentSaveDTO.getStudentRating());
        student.setStudentStatus(studentSaveDTO.getStudentStatus());
        student.setCounselorId(counselor);
        student.setBranchId(branch);
        student.setApplication(application);

        application.setStudent(student);
        applicationRepo.save(application);

        return "Student ID: " + application.getStudent().getStudentId() + " Saved";
    }

    @Override
    public String updateStudent(StudentUpdateDTO studentUpdateDTO, int studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Counselor counselor = counselorRepo.findById(studentUpdateDTO.getCounselorId())
                .orElseThrow(() -> new NotFoundException("Counselor not found with ID: " + studentUpdateDTO.getCounselorId()));
        Branch branch = branchRepo.findById(studentUpdateDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found with ID: " + studentUpdateDTO.getBranchId()));

        student.setStudentId(studentId);
        student.setStudentRating(studentUpdateDTO.getStudentRating());
        student.setStudentStatus(studentUpdateDTO.getStudentStatus());
        student.setCounselorId(counselor);
        student.setBranchId(branch);
        studentRepo.save(student);
        return student.getStudentId() + " Updated";
    }

    @Override
    public String updateStudentDetails(int studentId, StudentDetailsUpdateDTo studentDetailsUpdateDTo) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        student.setStudentRating(studentDetailsUpdateDTo.getStudentRating());
        student.setStudentStatus(studentDetailsUpdateDTo.getStudentStatus());
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
        modelMapper.typeMap(Student.class, StudentDTO.class).addMappings(mapper -> {
            mapper.map(Student::getCounselorId, StudentDTO::setCounselorId);
            mapper.map(Student::getBranchId, StudentDTO::setBranchId);
            mapper.map(Student::getApplication, StudentDTO::setApplicationId);
        });

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentGetDTO getStudentBasicDetails(int id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        StudentGetDTO studentGetDTO = modelMapper.map(student, StudentGetDTO.class);

        if (student.getApplication() != null) {
            studentGetDTO.setApplicationId(student.getApplication().getApplicationId());
            studentGetDTO.setTitle(student.getApplication().getTitle());
            studentGetDTO.setGivenName(student.getApplication().getGivenName());
            studentGetDTO.setFamilyName(student.getApplication().getFamilyName());
            studentGetDTO.setEmail(student.getApplication().getEmail());
            studentGetDTO.setMobileContactNumber(student.getApplication().getMobileContactNumber());
            studentGetDTO.setHomeContactNumber(student.getApplication().getHomeContactNumber());
        }

        return studentGetDTO;
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
                studentDTO.setCounselorId(modelMapper.map(student.getCounselorId(), CounselorForStudentDTO.class));
            }
            if (student.getApplication() != null) {
                studentDTO.setApplicationId(modelMapper.map(student.getApplication(), ApplicationGetDTO.class));
            }
            return studentDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public String setCounselor(int studentId, int counselorId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        Counselor counselor = counselorRepo.findById(counselorId).orElseThrow(() -> new NotFoundException("Counselor not found"));
        student.setCounselorId(counselor);
        studentRepo.save(student);
        return "Counselor set for student ID: " + studentId;
    }
}
