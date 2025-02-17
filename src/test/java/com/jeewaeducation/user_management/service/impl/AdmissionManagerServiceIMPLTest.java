package com.jeewaeducation.user_management.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerDTO;
import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerSaveDTO;
import com.jeewaeducation.user_management.entity.AdmissionManager;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.AdmissionManagerRepo;
import com.jeewaeducation.user_management.utility.mappers.AdmissionManagerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdmissionManagerServiceIMPLTest {

    @Mock
    private AdmissionManagerRepo admissionManagerRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AdmissionManagerMapper admissionManagerMapper;

    @InjectMocks
    private AdmissionManagerServiceIMPL admissionManagerService; // Use the concrete implementation

    private AdmissionManagerSaveDTO admissionManagerSaveDTO;
    private AdmissionManager admissionManager;

    @BeforeEach
    public void setUp() {
        admissionManagerSaveDTO = new AdmissionManagerSaveDTO();
        admissionManager = new AdmissionManager();
        admissionManager.setAdmissionManagerId(123);
    }

    @Test
    public void testSaveAdmissionManager_Success() {
        // Arrange
        when(modelMapper.map(admissionManagerSaveDTO, AdmissionManager.class)).thenReturn(admissionManager);
        //optional - used to avoid null checks and to explicitly handle cases where a value might be absent
        when(admissionManagerRepo.findById(admissionManager.getAdmissionManagerId())).thenReturn(Optional.empty());

        // Act
        String result = admissionManagerService.saveAdmissionManager(admissionManagerSaveDTO);

        // Assert
        verify(admissionManagerRepo).save(admissionManager);
        assertEquals("123 Saved", result);
    }

    @Test
    public void testSaveAdmissionManager_DuplicateKeyException() {
        // Arrange
        when(modelMapper.map(admissionManagerSaveDTO, AdmissionManager.class)).thenReturn(admissionManager);
        when(admissionManagerRepo.findById(admissionManager.getAdmissionManagerId())).thenReturn(Optional.of(admissionManager));

        // Act & Assert
        assertThrows(DuplicateKeyException.class, () -> {
            admissionManagerService.saveAdmissionManager(admissionManagerSaveDTO);
        });

        //This verifies that the save method of admissionManagerRepo was never called during the execution of the test.
        //any() argument matcher ensures that no arguments were passed to the save method.
        verify(admissionManagerRepo, never()).save(any());
    }

    @Test
    public void testDeleteAdmissionManager_Success() {
        // Arrange
        when(admissionManagerRepo.findById(admissionManager.getAdmissionManagerId())).thenReturn(Optional.of(admissionManager));

        // Act
        String result = admissionManagerService.deleteAdmissionManager(123);

        // Assert
        verify(admissionManagerRepo).deleteById(123);
        assertEquals("AdmissionManager 123 Deleted", result);
    }

    @Test
    public void testDeleteAdmissionManager_NotFound() {
        // Arrange
        when(admissionManagerRepo.findById(admissionManager.getAdmissionManagerId())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            admissionManagerService.deleteAdmissionManager(123);
        });

        assertEquals("AdmissionManager not found", exception.getMessage());
        verify(admissionManagerRepo, never()).deleteById(123);
    }

    @Test
    public void testGetAllAdmissionManager_Success() {
        // Arrange
        List<AdmissionManager> admissionManagers = new ArrayList<>();
        AdmissionManager admissionManager1 = new AdmissionManager();
        AdmissionManager admissionManager2 = new AdmissionManager();
        admissionManagers.add(admissionManager1);
        admissionManagers.add(admissionManager2);

        when(admissionManagerRepo.findAll()).thenReturn(admissionManagers);
        when(admissionManagerMapper.entityListToDtoList(admissionManagers)).thenReturn(new ArrayList<>()); // Mock the DTO conversion

        // Act
        List<AdmissionManagerDTO> result = admissionManagerService.getAllAdmissionManager();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size()); // Adjust based on what your mapper returns
        verify(admissionManagerRepo).findAll();
        verify(admissionManagerMapper).entityListToDtoList(admissionManagers);
    }
}