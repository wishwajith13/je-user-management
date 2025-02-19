package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationUpdateDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionForApplicationDTO;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Reception;
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

import java.util.Date;
import java.util.Optional;

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

//    private Application application;
//    private Reception reception;
//    private ApplicationSaveDTO applicationSaveDTO;
//
//    @BeforeEach
//    void setUp() {
//        application = new Application();
//        application.setApplicationId(1);
//
//        reception = new Reception();
//        reception.setReceptionId(1);
//
//        applicationSaveDTO = new ApplicationSaveDTO();
//        applicationSaveDTO.setReception(1);
//    }

    @Test
    public void saveApplication_whenReceptionNotFound_throwsNotFoundException() {
        ApplicationSaveDTO applicationSaveDTO = mock(ApplicationSaveDTO.class);
        when(receptionRepo.findById(applicationSaveDTO.getReception()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> applicationServiceIMPL.saveApplication(applicationSaveDTO));

        assertEquals("Reception not found with ID: " + applicationSaveDTO.getReception(), exception.getMessage());

        verify(receptionRepo, times(1)).findById(applicationSaveDTO.getReception());
    }

    @Test
    public void saveApplication_whenAllDataIsValid_savesApplication() {
        ApplicationSaveDTO applicationSaveDTO = mock(ApplicationSaveDTO.class);
        Application application = mock(Application.class);
        Reception reception = mock(Reception.class);

        when(receptionRepo.findById(applicationSaveDTO.getReception()))
                .thenReturn(Optional.of(reception));
        when(modelMapper.map(applicationSaveDTO, Application.class))
                .thenReturn(application);
        when(applicationRepo.existsById(application.getApplicationId()))
                .thenReturn(false);
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
    public  void deleteApplication_whenApplicationFound_deletesApplication() {
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

        ReceptionForApplicationDTO receptionDTO = new ReceptionForApplicationDTO();
        receptionDTO.setReceptionId(reception.getReceptionId());

        Application application = new Application();
        application.setApplicationId(1);

        application.setReception(reception);

        ApplicationGetDTO applicationGetDTO = new ApplicationGetDTO();
        applicationGetDTO.setApplicationId(1);
        applicationGetDTO.setReception(receptionDTO);

        when(applicationRepo.findById(1))
                .thenReturn(Optional.of(application));
        when(modelMapper.map(reception, ReceptionForApplicationDTO.class))
                .thenReturn(receptionDTO);
        when(modelMapper.map(application, ApplicationGetDTO.class))
                .thenReturn(applicationGetDTO);

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
                1234567890,
                1234567890,
                "email",
                "postal address",
                "school",
                new Date(),
                "OL Details",
                new Date(),
                "AL Details",
                new Date(),
                "Degree Details",
                new Date(),
                "Experience Details",
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country",
                true,
                "Refusal Details",
                "Sponsor Relationship",
                "Study Country",
                "City",
                "Method of Knowing",
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
                1234567890,
                1234567890,
                "email",
                "postal address",
                "school",
                new Date(),
                "OL Details",
                new Date(),
                "AL Details",
                new Date(),
                "Degree Details",
                new Date(),
                "Experience Details",
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country",
                true,
                "Refusal Details",
                "Sponsor Relationship",
                "Study Country",
                "City",
                "Method of Knowing",
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
                1234567890,
                1234567890,
                "email",
                "postal address",
                "school",
                new Date(),
                "OL Details",
                new Date(),
                "AL Details",
                new Date(),
                "Degree Details",
                new Date(),
                "Experience Details",
                new Date(),
                "IELTS Details",
                "Preferred Area",
                true,
                "Country",
                true,
                "Refusal Details",
                "Sponsor Relationship",
                "Study Country",
                "City",
                "Method of Knowing",
                11
        );

        Application existingApplication = getExistingApplication(applicationId);

        Application newApplication = new Application();
        newApplication.setApplicationId(applicationId);
        newApplication.setApplicationTitle(applicationUpdateDTO.getApplicationTitle());


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
        assertEquals(applicationUpdateDTO.getApplicationTitle(), updatedApplication.getApplicationTitle());
        assertEquals(applicationUpdateDTO.getReception(), updatedApplication.getReception().getReceptionId());

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

    private static Application getExistingApplication(int applicationId) {
        Application existingApplication = new Application();
        existingApplication.setApplicationId(applicationId);
        existingApplication.setApplicationDate(new Date());
        existingApplication.setApplicationTitle("Application Title Before Update");
        existingApplication.setDateOfBirth(new Date());
        existingApplication.setAge(19);
        existingApplication.setFamilyName("Family Name Before Update");
        existingApplication.setGivenName("Given Name Before Update");
        existingApplication.setGender("Male");
        existingApplication.setPassportOrNic("Passport Before Update");
        existingApplication.setRelationshipStatus("Single");
        existingApplication.setDateOfMarriage(new Date());
        existingApplication.setNumberOfChildren(1);
        existingApplication.setMobileContactNumber(1234567890);
        existingApplication.setHomeContactNumber(1234567890);
        existingApplication.setEmail("email Before Update");
        existingApplication.setPostalAddress("postal address Before Update");
        existingApplication.setSchoolAttended("school Before Update");
        existingApplication.setOlExamYear(new Date());
        existingApplication.setOlExamDetails("OL Details Before Update");
        existingApplication.setAlExamYear(new Date());
        existingApplication.setAlExamDetails("AL Details Before Update");
        existingApplication.setDegreeYear(new Date());
        existingApplication.setDegreeDetails("Degree Details Before Update");
        existingApplication.setExperienceYear(new Date());
        existingApplication.setExperienceDetails("Experience Details Before Update");
        existingApplication.setIeltsPteYear(new Date());
        existingApplication.setIeltsPteDetails("IELTS Details Before Update");
        existingApplication.setAppliedForVisaBefore(false);
        existingApplication.setCountryVisaTypeApplied("Visa Country Before Update");
        existingApplication.setVisaRefusals(false);
        existingApplication.setVisaRefusalsDetails("Refusal Details Before Update");
        existingApplication.setSponsorRelationship("Sponsor Relationship Before Update");
        existingApplication.setPreferredStudyCountry("Study Country Before Update");
        existingApplication.setPreferredCity("City Before Update");
        existingApplication.setMethodeOfKnowing("Method of Knowing Before Update");

        Reception initialReception = new Reception();
        initialReception.setReceptionId(10);
        existingApplication.setReception(initialReception);
        return existingApplication;
    }

}

