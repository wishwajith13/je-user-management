package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.application.ApplicationGetDTO;
import com.jeewaeducation.user_management.dto.application.ApplicationSaveDTO;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

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
        when(application.getReception())
                .thenReturn(reception);
        when(modelMapper.map(reception, ReceptionForApplicationDTO.class))
                .thenReturn(receptionDTO);
        when(modelMapper.map(application, ApplicationGetDTO.class))
                .thenReturn(applicationGetDTO);

        TypeMap<Application, ApplicationGetDTO> typeMap = mock(TypeMap.class);
        when(modelMapper.typeMap(Application.class, ApplicationGetDTO.class))
                .thenReturn(typeMap);

        ApplicationGetDTO result = applicationServiceIMPL.getApplication(1);

        assertEquals(applicationGetDTO, result);

        verify(applicationRepo, times(1))
                .findById(1);
        verify(modelMapper, times(1)).map(application, ApplicationGetDTO.class);



    }
}

