package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.student.StudentSaveDTO;
import com.jeewaeducation.user_management.dto.student.StudentUpdateDTO;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.entity.Student;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.repo.CounselorRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceIMPLTest {
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private CounselorRepo counselorRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BranchRepo branchRepo;
    @Mock
    private ApplicationRepo applicationRepo;
    @InjectMocks
    private StudentServiceIMPL studentServiceIMPL;

    @Test
    void saveStudent_WhenCounselorNotFound_ThrowNotFoundException() {
        StudentSaveDTO studentSaveDTO = mock(StudentSaveDTO.class);
        // Mocking the behavior of the findById method of the counselorRepo
        when(counselorRepo.findById(studentSaveDTO.getCounselorId()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> studentServiceIMPL.saveStudent(studentSaveDTO));

        // Assertion
        assertEquals("Counselor not found", exception.getMessage());

        verify(counselorRepo, times(1))
                .findById(studentSaveDTO.getCounselorId());
        verify(applicationRepo, never())
                .save(any());
    }

    @Test
    void saveStudent_WhenBranchNotFound_ThrowNotFoundException() {
        StudentSaveDTO studentSaveDTO = mock(StudentSaveDTO.class);
        // Mocking the behavior of the findById method of the counselorRepo
        when(counselorRepo.findById(studentSaveDTO.getCounselorId()))
                .thenReturn(Optional.of(mock(Counselor.class)));
        // Mocking the behavior of the findById method of the branchRepo
        when(branchRepo.findById(studentSaveDTO.getBranchId()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> studentServiceIMPL.saveStudent(studentSaveDTO));

        // Assertion
        assertEquals("Branch not found", exception.getMessage());

        verify(counselorRepo, times(1))
                .findById(studentSaveDTO.getCounselorId());
        verify(branchRepo, times(1))
                .findById(studentSaveDTO.getBranchId());
        verify(applicationRepo, never())
                .save(any());
    }

    @Test
    void saveStudent_WhenApplicationNotFound_ThrowNotFoundException() {
        StudentSaveDTO studentSaveDTO = mock(StudentSaveDTO.class);
        // Mocking the behavior of the findById method of the counselorRepo
        when(counselorRepo.findById(studentSaveDTO.getCounselorId()))
                .thenReturn(Optional.of(mock(Counselor.class)));
        // Mocking the behavior of the findById method of the branchRepo
        when(branchRepo.findById(studentSaveDTO.getBranchId()))
                .thenReturn(Optional.of(mock(Branch.class)));
        // Mocking the behavior of the findById method of the applicationRepo
        when(applicationRepo.findById(studentSaveDTO.getApplicationId()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> studentServiceIMPL.saveStudent(studentSaveDTO));

        // Assertion
        assertEquals("Application not found", exception.getMessage());

        verify(counselorRepo, times(1))
                .findById(studentSaveDTO.getCounselorId());
        verify(branchRepo, times(1))
                .findById(studentSaveDTO.getBranchId());
        verify(applicationRepo, times(1))
                .findById(studentSaveDTO.getApplicationId());
        verify(studentRepo, never())
                .existsByApplication(any());
        verify(applicationRepo, never())
                .save(any());
    }

    @Test
    void saveStudent_WhenStudentExistsWithApplication_ThrowNotFoundException() {
        StudentSaveDTO studentSaveDTO = mock(StudentSaveDTO.class);
        // Mocking the behavior of the findById method of the counselorRepo
        when(counselorRepo.findById(studentSaveDTO.getCounselorId()))
                .thenReturn(Optional.of(mock(Counselor.class)));
        // Mocking the behavior of the findById method of the branchRepo
        when(branchRepo.findById(studentSaveDTO.getBranchId()))
                .thenReturn(Optional.of(mock(Branch.class)));
        // Mocking the behavior of the findById method of the applicationRepo
        when(applicationRepo.findById(studentSaveDTO.getApplicationId()))
                .thenReturn(Optional.of(mock(Application.class)));
        // Mocking the behavior of the existsByApplication method of the studentRepo
        when(studentRepo.existsByApplication(any()))
                .thenReturn(true);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> studentServiceIMPL.saveStudent(studentSaveDTO));

        // Assertion
        assertEquals("Student already exists with application ID: "
                + studentSaveDTO.getApplicationId(), exception.getMessage());

        verify(counselorRepo, times(1))
                .findById(studentSaveDTO.getCounselorId());
        verify(branchRepo, times(1))
                .findById(studentSaveDTO.getBranchId());
        verify(applicationRepo, times(1))
                .findById(studentSaveDTO.getApplicationId());
        verify(studentRepo, times(1)).
                existsByApplication(any());
        verify(applicationRepo, never())
                .save(any());

    }

    @Test
    void saveStudent_WhenAllDataIsValid_SaveStudent() {
        StudentSaveDTO studentSaveDTO = new StudentSaveDTO(
                "best",
                "completed",
                3,
                4,
                5
        );

        Counselor counselor = new Counselor();
        counselor.setCounselorId(3);

        Branch branch = new Branch();
        branch.setBranchId(4);

        Application application = new Application();
        application.setApplicationId(5);

        // Mocking the behavior of the findById method of the counselorRepo
        when(counselorRepo.findById(studentSaveDTO.getCounselorId()))
                .thenReturn(Optional.of(counselor));
        // Mocking the behavior of the findById method of the branchRepo
        when(branchRepo.findById(studentSaveDTO.getBranchId()))
                .thenReturn(Optional.of(branch));
        // Mocking the behavior of the findById method of the applicationRepo
        when(applicationRepo.findById(studentSaveDTO.getApplicationId()))
                .thenReturn(Optional.of(application));
        // Mocking the behavior of the existsByApplication method of the studentRepo
        when(studentRepo.existsByApplication(any()))
                .thenReturn(false);

        Student student = new Student();
        student.setStudentRating(studentSaveDTO.getStudentRating());
        student.setStudentStatus(studentSaveDTO.getStudentStatus());
        student.setCounselorId(counselor);
        student.setBranchId(branch);
        student.setApplication(application);
        student.setStudentId(100);

        application.setStudent(student);

        ArgumentCaptor<Application> applicationArgumentCaptor = ArgumentCaptor.forClass(Application.class);

        when(applicationRepo.save(applicationArgumentCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));


        String result = studentServiceIMPL.saveStudent(studentSaveDTO);

        // Assertion
        assertEquals("Student ID: " + application.getStudent().getStudentId() + " Saved", result);

        Application savedApplication = applicationArgumentCaptor.getValue();

        assertNotNull(savedApplication);
        assertEquals(application.getApplicationId(), savedApplication.getApplicationId());
        assertEquals(application.getStudent(), savedApplication.getStudent());

        Student savedStudent = savedApplication.getStudent();

        assertEquals(student.getStudentRating(), savedStudent.getStudentRating());
        assertEquals(student.getStudentStatus(), savedStudent.getStudentStatus());
        assertEquals(student.getCounselorId(), savedStudent.getCounselorId());
        assertEquals(student.getBranchId(), savedStudent.getBranchId());
        assertEquals(student.getApplication(), savedStudent.getApplication());

        verify(counselorRepo, times(1))
                .findById(studentSaveDTO.getCounselorId());
        verify(branchRepo, times(1))
                .findById(studentSaveDTO.getBranchId());
        verify(applicationRepo, times(1))
                .findById(studentSaveDTO.getApplicationId());
        verify(studentRepo, times(1)).
                existsByApplication(any());
        verify(applicationRepo, times(1))
                .save(application);

    }

    @Test
    public void updateStudent_WhenStudentNotFound_ThrowNotFoundException(){
        int studentID = 1;
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO(
                "best",
                "new",
                2,
                3
        );

        when(studentRepo.findById(studentID)).
                thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                ()-> studentServiceIMPL.updateStudent(studentUpdateDTO,studentID));

        assertEquals("Student not found",exception.getMessage());

        verify(studentRepo,times(1))
                .findById(studentID);
        verify(studentRepo,never())
                .save(any());


    }

    @Test
    public void updateStudent_WhenCounselorNotFound_ThrowNotFoundException(){
        int studentID = 1;
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO(
                "best",
                "new",
                2,
                3
        );

        Student student = mock(Student.class);
        when(studentRepo.findById(studentID)).
                thenReturn(Optional.of(student));

        when(counselorRepo.findById(studentUpdateDTO.getCounselorId()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                ()-> studentServiceIMPL.updateStudent(studentUpdateDTO,studentID));

        assertEquals("Counselor not found with ID: " + studentUpdateDTO.getCounselorId(),exception.getMessage());

        verify(studentRepo,times(1))
                .findById(studentID);
        verify(counselorRepo,times(1))
                .findById(studentUpdateDTO.getCounselorId());
        verify(studentRepo,never())
                .save(any());
    }

    @Test
    public void updateStudent_WhenBranchNotFound_ThrowNotFoundException(){
        int studentID = 1;
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO(
                "best",
                "new",
                2,
                3
        );

        Student student = mock(Student.class);
        Counselor counselor = mock(Counselor.class);

        when(studentRepo.findById(studentID)).
                thenReturn(Optional.of(student));

        when(counselorRepo.findById(studentUpdateDTO.getCounselorId()))
                .thenReturn(Optional.of(counselor));

        when(branchRepo.findById(studentUpdateDTO.getBranchId()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                ()-> studentServiceIMPL.updateStudent(studentUpdateDTO,studentID));

        assertEquals("Branch not found with ID: " + studentUpdateDTO.getBranchId(),exception.getMessage());

        verify(studentRepo,times(1))
                .findById(studentID);
        verify(counselorRepo,times(1))
                .findById(studentUpdateDTO.getCounselorId());
        verify(branchRepo,times(1))
                .findById(studentUpdateDTO.getBranchId());

        verify(studentRepo,never())
                .save(any());
    }

    @Test
    public void updateStudent_WhenAllDataValid_UpdateStudent(){
        int studentID = 1;
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO(
                "best",
                "new",
                2,
                3
        );

        Student student = new Student();
        student.setStudentId(studentID);
        student.setStudentRating(studentUpdateDTO.getStudentRating());
        student.setStudentStatus(studentUpdateDTO.getStudentStatus());

        Counselor counselor = new Counselor();
        counselor.setCounselorId(studentUpdateDTO.getCounselorId());

        Branch branch = new Branch();
        branch.setBranchId(studentUpdateDTO.getBranchId());

        student.setBranchId(branch);
        student.setCounselorId(counselor);


        when(studentRepo.findById(studentID)).
                thenReturn(Optional.of(student));

        when(counselorRepo.findById(studentUpdateDTO.getCounselorId()))
                .thenReturn(Optional.of(counselor));

        when(branchRepo.findById(studentUpdateDTO.getBranchId()))
                .thenReturn(Optional.of(branch));

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        when(studentRepo.save(studentArgumentCaptor.capture())).thenAnswer(
                invocationOnMock -> invocationOnMock.getArgument(0)
        );

        System.out.println(studentArgumentCaptor.getValue());

        String message = studentServiceIMPL.updateStudent(studentUpdateDTO,studentID);

        assertEquals(student.getStudentId() + " Updated",message);

        verify(studentRepo,times(1))
                .findById(studentID);
        verify(counselorRepo,times(1))
                .findById(studentUpdateDTO.getCounselorId());
        verify(branchRepo,times(1))
                .findById(studentUpdateDTO.getBranchId());
        verify(studentRepo,times(1))
                .save(student);
    }


}