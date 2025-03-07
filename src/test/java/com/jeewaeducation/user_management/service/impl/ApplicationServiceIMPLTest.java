package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.application.*;
import com.jeewaeducation.user_management.dto.reception.ReceptionForApplicationDTO;
import com.jeewaeducation.user_management.entity.*;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.repo.ReceptionRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import com.jeewaeducation.user_management.utility.mappers.ApplicationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceIMPLTest {
    @Mock
    private ApplicationRepo applicationRepo;
    @Mock
    private ReceptionRepo receptionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ApplicationMapper applicationMapper;
    @Mock
    private StudentRepo studentRepo;
    @InjectMocks
    private ApplicationServiceIMPL applicationServiceIMPL;

    @Test
    public void saveApplication_whenReceptionNotFound_throwsNotFoundException() {
        ApplicationSaveDTO applicationSaveDTO = mock(ApplicationSaveDTO.class);
        Application application = new Application();

        when(modelMapper.map(applicationSaveDTO, Application.class))
                .thenReturn(application);
        when(receptionRepo.findById(applicationSaveDTO.getReception()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.saveApplication(applicationSaveDTO));

        assertEquals("Reception not found with ID: " + applicationSaveDTO.getReception(), exception.getMessage());

        verify(receptionRepo, times(1)).findById(applicationSaveDTO.getReception());
    }

    @Test
    public void saveApplication_whenApplicationAlreadyExists_throwsDuplicateKeyException() {
        ApplicationSaveDTO applicationSaveDTO = mock(ApplicationSaveDTO.class);
        Application application = new Application();

        Branch branch = new Branch();
        branch.setBranchId(1);

        Reception reception = new Reception();
        reception.setReceptionId(1);
        reception.setBranch(branch);


        when(modelMapper.map(applicationSaveDTO, Application.class))
                .thenReturn(application);
        when(receptionRepo.findById(applicationSaveDTO.getReception()))
                .thenReturn(Optional.of(reception));
        when(applicationRepo.existsById(application.getApplicationId()))
                .thenReturn(true);

        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class,
                () -> applicationServiceIMPL.saveApplication(applicationSaveDTO));

        assertEquals("Application already exists with ID: " + application.getApplicationId(), exception.getMessage());

        verify(applicationRepo, times(1))
                .existsById(application.getApplicationId());
        verify(modelMapper, times(1))
                .map(applicationSaveDTO, Application.class);
    }

    @Test
    public void saveApplication_whenAllDataIsValid_savesApplication() {
        ApplicationSaveDTO applicationSaveDTO = new ApplicationSaveDTO(
                new Date(),
                "Application Title",
                new Date(),
                "Place of Birth",
                20,
                "Family Name",
                "Given Name",
                "male",
                "Passport",
                "Married",
                new Date(),
                2,
                "1234567890",
                "1234567890",
                "email",
                "postal address",
                "school",
                "olExamType",
                new Date(),
                new Date(),
                "OL Details",
                "AL Exam Type",
                new Date(),
                new Date(),
                "AL Details",
                "Degree Type",
                new Date(),
                new Date(),
                "Degree Details",
                "Other Type",
                new Date(),
                new Date(),
                "Other Details",
                "Experience Type",
                new Date(),
                new Date(),
                "Experience Details",
                "IELTS Type",
                new Date(),
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country",
                true,
                "Refusal Details",
                List.of("Sponsor Relationship"),
                List.of("Study Country"),
                "Preferred Study Country Other",
                List.of("City"),
                List.of("Method of Knowing"),
                false,
                1
        );

        Branch branch = new Branch();
        branch.setBranchId(1);

        Reception reception = new Reception();
        reception.setReceptionId(1);
        reception.setBranch(branch);

        Application application = new Application();

        when(modelMapper.map(applicationSaveDTO, Application.class))
                .thenReturn(application);
        when(receptionRepo.findById(applicationSaveDTO.getReception()))
                .thenReturn(Optional.of(reception));
        when(applicationRepo.existsById(application.getApplicationId()))
                .thenReturn(false);

        application.setReception(reception);

        Student student = new Student();
        student.setStudentRating("Not Rated");
        student.setStudentStatus("New");
        student.setBranchId(reception.getBranch());
        student.setApplication(application);


        when(studentRepo.save(student))
                .thenReturn(student);

        when(applicationRepo.save(application))
                .thenReturn(application);

        String result = applicationServiceIMPL.saveApplication(applicationSaveDTO);

        assertEquals("Application ID: " + application.getApplicationId() + " and Student ID: " + student.getStudentId() + " Saved", result);

        verify(modelMapper, times(1))
                .map(applicationSaveDTO, Application.class);
        verify(receptionRepo, times(1))
                .findById(application.getReception().getReceptionId());
        verify(applicationRepo, times(1))
                .save(application);
        verify(studentRepo, times(1))
                .save(student);

    }

    @Test
    public void deleteApplication_whenApplicationNotFound_throwsNotFoundException() {
        int applicationId = 1;
        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.deleteApplication(applicationId));

        assertEquals("Application not found with ID: " + applicationId, exception.getMessage());

        verify(applicationRepo, times(1)).findById(applicationId);
        verify(applicationRepo, never()).deleteById(applicationId);
    }

    @Test
    public void deleteApplication_whenApplicationFound_deletesApplication() {
        int applicationId = 1;
        Application application = mock(Application.class);
        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.of(application));

        String result = applicationServiceIMPL.deleteApplication(applicationId);


        verify(applicationRepo, times(1))
                .findById(applicationId);
        verify(applicationRepo, times(1))
                .deleteById(applicationId);

        assertEquals(applicationId + " Deleted", result);
    }

    @Test
    public void getApplication_whenApplicationNotFound_throwsNotFoundException() {
        int applicationId = 1;
        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.getApplication(applicationId));

        assertEquals("Application not found with ID: " + applicationId, exception.getMessage());

        verify(applicationRepo, times(1))
                .findById(applicationId);

        verify(modelMapper, never())
                .map(any(), any());
    }

    @Test
    public void getApplication_whenApplicationFound_returnsApplicationGetDTO() {
        Reception reception = mock(Reception.class);
        Counselor counselor = mock(Counselor.class);

        ReceptionForApplicationDTO receptionDTO = new ReceptionForApplicationDTO();
        receptionDTO.setReceptionId(reception.getReceptionId());

        Application application = new Application();
        application.setApplicationId(1);

        Student student = new Student();
        student.setApplication(application);
        student.setCounselorId(counselor);

        application.setReception(reception);

        ApplicationGetDTO applicationGetDTO = new ApplicationGetDTO();
        applicationGetDTO.setApplicationId(1);
        applicationGetDTO.setReception(receptionDTO);

        when(applicationRepo.findById(1))
                .thenReturn(Optional.of(application));
        when(studentRepo.findByApplication(application))
                .thenReturn(student);
        when(modelMapper.map(application, ApplicationGetDTO.class))
                .thenReturn(applicationGetDTO);
        when(modelMapper.map(reception, ReceptionForApplicationDTO.class))
                .thenReturn(receptionDTO);

        ApplicationGetDTO result = applicationServiceIMPL.getApplication(1);

        assertEquals(applicationGetDTO, result);
        assertEquals(application.getReception(), reception);

        verify(applicationRepo, times(1))
                .findById(1);
        verify(modelMapper, times(1)).map(application, ApplicationGetDTO.class);

    }

    @Test
    public void updateApplication_whenApplicationNotFound_throwsNotFoundException() {
        int applicationId = 1;

        ApplicationUpdateDTO applicationUpdateDTO = new ApplicationUpdateDTO(
                applicationId,
                new Date(),
                "Application Title",
                new Date(),
                "Place of Birth",
                20,
                "Family Name",
                "Given Name",
                "male",
                "Passport",
                "Married",
                new Date(),
                2,
                "1234567890",
                "1234567890",
                "email@example.com",
                "postal address",
                "school",
                "OL Exam Type",
                new Date(),
                new Date(),
                "OL Details",
                "AL Exam Type",
                new Date(),
                new Date(),
                "AL Details",
                "Degree Type",
                new Date(),
                new Date(),
                "Degree Details",
                "Other Type",
                new Date(),
                new Date(),
                "Other Details",
                "Experience Type",
                new Date(),
                new Date(),
                "Experience Details",
                "IELTS Type",
                new Date(),
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country Visa Type",
                true,
                "Refusal Details",
                Arrays.asList("Parent", "Guardian"),
                Arrays.asList("USA", "Canada"),
                "Other Country",
                Arrays.asList("New York", "Toronto"),
                Arrays.asList("Internet", "Friends"),
                true,
                1
        );

        when(applicationRepo.findById(applicationId)).
                thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.updateApplication(applicationUpdateDTO));


        assertEquals("Application not found with ID: " + applicationId, exception.getMessage());

        verify(applicationRepo, times(1)).findById(applicationId);
        verify(applicationRepo, never()).save(any());
    }

    @Test
    public void updateApplication_whenReceptionNotFound_throwsNotFoundException() {
        int applicationId = 1;
        ApplicationUpdateDTO applicationUpdateDTO = new ApplicationUpdateDTO(
                applicationId,
                new Date(),
                "Application Title",
                new Date(),
                "Place of Birth",
                20,
                "Family Name",
                "Given Name",
                "male",
                "Passport",
                "Married",
                new Date(),
                2,
                "1234567890",
                "1234567890",
                "email@example.com",
                "postal address",
                "school",
                "OL Exam Type",
                new Date(),
                new Date(),
                "OL Details",
                "AL Exam Type",
                new Date(),
                new Date(),
                "AL Details",
                "Degree Type",
                new Date(),
                new Date(),
                "Degree Details",
                "Other Type",
                new Date(),
                new Date(),
                "Other Details",
                "Experience Type",
                new Date(),
                new Date(),
                "Experience Details",
                "IELTS Type",
                new Date(),
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country Visa Type",
                true,
                "Refusal Details",
                Arrays.asList("Parent", "Guardian"),
                Arrays.asList("USA", "Canada"),
                "Other Country",
                Arrays.asList("New York", "Toronto"),
                Arrays.asList("Internet", "Friends"),
                true,
                1
        );

        Application application = mock(Application.class);
        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.of(application));
        when(receptionRepo.findById(applicationUpdateDTO.getReception()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.updateApplication(applicationUpdateDTO));

        assertEquals("Reception not found with ID: " + applicationUpdateDTO.getReception(), exception.getMessage());

        verify(applicationRepo, times(1))
                .findById(applicationId);
        verify(receptionRepo, times(1))
                .findById(applicationUpdateDTO.getReception());
        verify(applicationRepo, never())
                .save(any());
    }

    @Test
    public void updateApplication_whenAllDataIsValid_updatesApplication() {
        int applicationId = 1;

        ApplicationUpdateDTO applicationUpdateDTO = new ApplicationUpdateDTO(
                applicationId,
                new Date(),
                "Application Title",
                new Date(),
                "Place of Birth",
                20,
                "Family Name",
                "Given Name",
                "male",
                "Passport",
                "Married",
                new Date(),
                2,
                "1234567890",
                "1234567890",
                "email@example.com",
                "postal address",
                "school",
                "OL Exam Type",
                new Date(),
                new Date(),
                "OL Details",
                "AL Exam Type",
                new Date(),
                new Date(),
                "AL Details",
                "Degree Type",
                new Date(),
                new Date(),
                "Degree Details",
                "Other Type",
                new Date(),
                new Date(),
                "Other Details",
                "Experience Type",
                new Date(),
                new Date(),
                "Experience Details",
                "IELTS Type",
                new Date(),
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country Visa Type",
                true,
                "Refusal Details",
                Arrays.asList("Parent", "Guardian"),
                Arrays.asList("USA", "Canada"),
                "Other Country",
                Arrays.asList("New York", "Toronto"),
                Arrays.asList("Internet", "Friends"),
                true,
                1
        );

        Application existingApplication = getExistingApplication(applicationId);

        Application newApplication = new Application();
        newApplication.setApplicationId(applicationId);
        newApplication.setTitle(applicationUpdateDTO.getTitle());

        Reception newReception = new Reception();
        newReception.setReceptionId(applicationUpdateDTO.getReception());

        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.of(existingApplication));
        when(receptionRepo.findById(applicationUpdateDTO.getReception()))
                .thenReturn(Optional.of(newReception));
        when(modelMapper.map(applicationUpdateDTO, Application.class))
                .thenReturn(newApplication);
        ArgumentCaptor<Application> applicationArgumentCaptor = ArgumentCaptor.forClass(Application.class);
        when(applicationRepo.save(applicationArgumentCaptor.capture()))
                .thenReturn(newApplication);

        String result = applicationServiceIMPL.updateApplication(applicationUpdateDTO);

        assertEquals(applicationId + "Updated", result);

        verify(applicationRepo, times(1))
                .findById(applicationId);
        verify(receptionRepo, times(1))
                .findById(applicationUpdateDTO.getReception());
        verify(applicationRepo, times(1))
                .save(applicationArgumentCaptor.capture());

        Application updatedApplication = applicationArgumentCaptor.getValue();

        assertEquals(applicationId, updatedApplication.getApplicationId());
        assertEquals(applicationUpdateDTO.getTitle(), updatedApplication.getTitle());
        assertEquals(applicationUpdateDTO.getReception(), updatedApplication.getReception().getReceptionId());

    }

    @Test
    public void updateApplicationVerification_whenApplicationNotFound_throwsNotFoundException() {
        int applicationId = 1;
        ApplicationVerificationUpdateDTO applicationVerificationUpdateDTO = new ApplicationVerificationUpdateDTO(
                false
        );

        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.updateApplicationVerification(applicationVerificationUpdateDTO, applicationId));

        assertEquals("Application not found with ID: " + applicationId, exception.getMessage());

        verify(applicationRepo, times(1))
                .findById(applicationId);
        verify(applicationRepo, never()).save(any());
    }

    @Test
    public void updateApplicationVerification_whenAllDataIsValid_updatesApplicationVerification() {
        int applicationId = 1;
        ApplicationVerificationUpdateDTO applicationVerificationUpdateDTO = new ApplicationVerificationUpdateDTO(
                true
        );
        Application existingApplication = getExistingApplication(applicationId);

        Application newApplication = new Application();
        newApplication.setApplicationId(applicationId);
        newApplication.setVerified(applicationVerificationUpdateDTO.isVerified());

        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.of(existingApplication));
        existingApplication.setVerified(applicationVerificationUpdateDTO.isVerified());
        when(applicationRepo.save(existingApplication))
                .thenReturn(newApplication);

        String result = applicationServiceIMPL.updateApplicationVerification(applicationVerificationUpdateDTO, applicationId);

        assertEquals(applicationId + "Updated " + "Verification Status: " + applicationVerificationUpdateDTO.isVerified(), result);

        verify(applicationRepo, times(1))
                .findById(applicationId);
        verify(applicationRepo, times(1))
                .save(existingApplication);

    }

    @Test
    public void getStudentBasicDetails_whenApplicationNotFound_throwsNotFoundException() {
        int applicationId = 1;

        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.getStudentBasicDetails(applicationId));

        assertEquals("Application not found with ID: " + applicationId, exception.getMessage());

        verify(applicationRepo, times(1))
                .findById(applicationId);
        verify(modelMapper, never())
                .map(any(), any());
    }

    @Test
    public void getStudentBasicDetails_whenApplicationFound_returnsStudentBasicDetails() {
        int applicationId = 1;
        Application application = new Application();

        application.setApplicationId(1);

        ApplicationStudentBasicDetailsGetDTO applicationStudentBasicDetailsGetDTO = new ApplicationStudentBasicDetailsGetDTO();

        when(applicationRepo.findById(applicationId))
                .thenReturn(Optional.of(application));
        when(modelMapper.map(application, ApplicationStudentBasicDetailsGetDTO.class))
                .thenReturn(applicationStudentBasicDetailsGetDTO);

        ApplicationStudentBasicDetailsGetDTO result = applicationServiceIMPL.getStudentBasicDetails(applicationId);

        assertEquals(applicationStudentBasicDetailsGetDTO, result);
    }

    @Test
    public void getAllStudentBasicDetails_whenNoApplicationsFound_throwsNotFoundException() {
        when(applicationRepo.findAll())
                .thenReturn(emptyList());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.getAllStudentBasicDetails());

        assertEquals("No applications found", exception.getMessage());

        verify(applicationRepo, times(1))
                .findAll();
        verify(applicationMapper, never())
                .entityListToDtoList(any());
    }

    @Test
    public void getAllStudentBasicDetails_WhenApplicationsFound_returnsApplicationStudentBasicDetailsGetDTOList() {
        Reception reception = mock(Reception.class);

        ReceptionForApplicationDTO receptionDTO = new ReceptionForApplicationDTO();
        receptionDTO.setReceptionId(reception.getReceptionId());

        Application application = new Application();
        application.setApplicationId(1);
        application.setReception(reception);

        ApplicationStudentBasicDetailsGetDTO applicationStudentBasicDetailsGetDTO = new ApplicationStudentBasicDetailsGetDTO();
        applicationStudentBasicDetailsGetDTO.setApplicationId(1);
        applicationStudentBasicDetailsGetDTO.setReception(receptionDTO);

        List<Application> applications = List.of(application);

        when(applicationRepo.findAll())
                .thenReturn(applications);
        when(applicationMapper.entityListToDtoList(applications))
                .thenReturn(List.of(applicationStudentBasicDetailsGetDTO));

        List<ApplicationStudentBasicDetailsGetDTO> result = applicationServiceIMPL.getAllStudentBasicDetails();

        assertEquals(1, result.size());
        assertEquals(applicationStudentBasicDetailsGetDTO, result.get(0));
        assertEquals(applicationStudentBasicDetailsGetDTO.getApplicationId(), result.get(0).getApplicationId());
        assertEquals(applicationStudentBasicDetailsGetDTO.getReception(), result.get(0).getReception());

        verify(applicationRepo, times(1))
                .findAll();
        verify(applicationMapper, times(1))
                .entityListToDtoList(applications);
    }



    private static Application getExistingApplication(int applicationId) {
        Application existingApplication = new Application();
        existingApplication.setApplicationId(applicationId);
        existingApplication.setApplicationDate(new Date());
        existingApplication.setTitle("Application Title Before Update");
        existingApplication.setDateOfBirth(new Date());
        existingApplication.setAge(19);
        existingApplication.setFamilyName("Family Name Before Update");
        existingApplication.setGivenName("Given Name Before Update");
        existingApplication.setGender("Male");
        existingApplication.setPassportOrNic("Passport Before Update");
        existingApplication.setRelationshipStatus("Single");
        existingApplication.setDateOfMarriage(new Date());
        existingApplication.setNumberOfChildren(1);
        existingApplication.setMobileContactNumber("1234567890");
        existingApplication.setHomeContactNumber("1234567890");
        existingApplication.setEmail("email Before Update");
        existingApplication.setPostalAddress("postal address Before Update");
        existingApplication.setSchoolAttended("school Before Update");
        existingApplication.setOlExamType("OL Exam Type Before Update");
        existingApplication.setOlExamStartYear(new Date());
        existingApplication.setOlExamEndYear(new Date());
        existingApplication.setOlExamDetails("OL Details Before Update");
        existingApplication.setAlExamType("AL Exam Type Before Update");
        existingApplication.setAlExamStartYear(new Date());
        existingApplication.setAlExamEndYear(new Date());
        existingApplication.setAlExamDetails("AL Details Before Update");
        existingApplication.setDegreeType("Degree Type Before Update");
        existingApplication.setDegreeStartYear(new Date());
        existingApplication.setDegreeEndYear(new Date());
        existingApplication.setDegreeDetails("Degree Details Before Update");
        existingApplication.setExperienceType("Experience Type Before Update");
        existingApplication.setExperienceStartYear(new Date());
        existingApplication.setExperienceEndYear(new Date());
        existingApplication.setExperienceDetails("Experience Details Before Update");
        existingApplication.setIeltsPteType("IELTS Type Before Update");
        existingApplication.setIeltsPteStartYear(new Date());
        existingApplication.setIeltsPteEndYear(new Date());
        existingApplication.setIeltsPteDetails("IELTS Details Before Update");
        existingApplication.setPreferredAreaOfStudy("Preferred Area of Study Before Update");
        existingApplication.setAppliedForVisaBefore(false);
        existingApplication.setCountryVisaTypeApplied("Visa Country Before Update");
        existingApplication.setVisaRefusals(false);
        existingApplication.setVisaRefusalsDetails("Refusal Details Before Update");
        existingApplication.setSponsorRelationship(List.of("Sponsor Relationship Before Update"));
        existingApplication.setPreferredStudyCountry(List.of("Study Country Before Update"));
        existingApplication.setPreferredCity(List.of("City Before Update"));
        existingApplication.setMethodeOfKnowing(List.of("Method of Knowing Before Update"));
        existingApplication.setVerified(false);

        Reception initialReception = new Reception();
        initialReception.setReceptionId(10);
        existingApplication.setReception(initialReception);

        return existingApplication;
    }

}

