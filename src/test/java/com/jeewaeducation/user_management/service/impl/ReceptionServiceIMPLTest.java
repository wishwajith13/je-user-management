package com.jeewaeducation.user_management.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Reception;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.repo.ReceptionRepo;
import com.jeewaeducation.user_management.utility.mappers.ReceptionMapper;
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
public class ReceptionServiceIMPLTest {

    @Mock
    private ReceptionRepo receptionRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ReceptionMapper receptionMapper;

    @Mock
    private BranchRepo branchRepo;

    @Mock
    private ApplicationRepo applicationRepo;

    @InjectMocks
    private ReceptionServiceIMPL receptionService;

    private ReceptionSaveDTO receptionSaveDTO;
    private Reception reception;
    private ReceptionDTO receptionDTO;
    private Branch branch;

    @BeforeEach
    public void setUp() {
        receptionSaveDTO = new ReceptionSaveDTO();
        receptionDTO = new ReceptionDTO();
        reception = new Reception();
        reception.setReceptionId(1);
        branch = new Branch();
        branch.setBranchId(1);
    }

    @Test
    public void testSaveReception_Success() {
        // Arrange
        receptionSaveDTO.setBranchId(branch.getBranchId());
        when(branchRepo.findById(branch.getBranchId())).thenReturn(Optional.of(branch));
        when(modelMapper.map(receptionSaveDTO, Reception.class)).thenReturn(reception);
        when(receptionRepo.save(reception)).thenReturn(reception);

        // Act
        String result = receptionService.saveReception(receptionSaveDTO);

        // Assert
        verify(receptionRepo).save(reception);
        assertEquals("0 Saved", result);
    }

    @Test
    public void testSaveReception_BranchNotFound() {
        // Arrange
        receptionSaveDTO.setBranchId(branch.getBranchId());
        when(branchRepo.findById(branch.getBranchId())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            receptionService.saveReception(receptionSaveDTO);
        });

        assertEquals("Branch not found", exception.getMessage());
        verify(receptionRepo, never()).save(any());
    }

    @Test
    public void testDeleteReception_Success() {
        // Arrange
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.of(reception));
        when(applicationRepo.existsByReception_ReceptionId(reception.getReceptionId())).thenReturn(false);

        // Act
        String result = receptionService.deleteReception(reception.getReceptionId());

        // Assert
        verify(receptionRepo).deleteById(reception.getReceptionId());
        assertEquals("Reception 1 Deleted", result);
    }

    @Test
    public void testDeleteReception_NotFound() {
        // Arrange
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            receptionService.deleteReception(reception.getReceptionId());
        });

        assertEquals("Reception not found", exception.getMessage());
        verify(receptionRepo, never()).deleteById(any());
    }

    @Test
    public void testDeleteReception_ForeignKeyConstraintViolation() {
        // Arrange
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.of(reception));
        when(applicationRepo.existsByReception_ReceptionId(reception.getReceptionId())).thenReturn(true);

        // Act & Assert
        ForeignKeyConstraintViolationException exception = assertThrows(ForeignKeyConstraintViolationException.class, () -> {
            receptionService.deleteReception(reception.getReceptionId());
        });

        assertEquals("Cannot delete reception as it is referenced by other records", exception.getMessage());
        verify(receptionRepo, never()).deleteById(any());
    }

    @Test
    public void testGetAllReception_Success() {
        // Arrange
        List<Reception> receptions = new ArrayList<>();
        receptions.add(reception);
        when(receptionRepo.findAll()).thenReturn(receptions);
        when(receptionMapper.entitListToDtoList(receptions)).thenReturn(new ArrayList<>()); // Mock the DTO conversion

        // Act
        List<ReceptionDTO> result = receptionService.getAllReception();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(receptionRepo).findAll();
        verify(receptionMapper).entitListToDtoList(receptions);
    }

    @Test
    public void testGetAllReception_NotFound() {
        // Arrange
        when(receptionRepo.findAll()).thenReturn(new ArrayList<>()); // Return an empty list

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            receptionService.getAllReception();
        });

        assertEquals("No receptions found", exception.getMessage());
        verify(receptionRepo).findAll();
        verify(receptionMapper, never()).entitListToDtoList(any());
    }

    @Test
    public void testGetReception_Success() {
        // Arrange
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.of(reception));
        when(modelMapper.map(reception, ReceptionDTO.class)).thenReturn(receptionDTO);

        // Act
        ReceptionDTO result = receptionService.getReception(reception.getReceptionId());

        // Assert
        assertNotNull(result);
        assertEquals(receptionDTO, result);
        verify(receptionRepo).findById(reception.getReceptionId());
        verify(modelMapper).map(reception, ReceptionDTO.class);
    }

    @Test
    public void testGetReception_NotFound() {
        // Arrange
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            receptionService.getReception(reception.getReceptionId());
        });

        assertEquals("Reception not found", exception.getMessage());
        verify(receptionRepo).findById(reception.getReceptionId());
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    public void testUpdateReception_Success() {
        // Arrange
        receptionSaveDTO.setBranchId(branch.getBranchId());
        when(branchRepo.findById(branch.getBranchId())).thenReturn(Optional.of(branch));
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.of(reception));
        when(modelMapper.map(receptionSaveDTO, Reception.class)).thenReturn(reception);
        when(receptionRepo.save(reception)).thenReturn(reception);

        // Act
        String result = receptionService.updateReception(receptionSaveDTO, reception.getReceptionId());

        // Assert
        verify(receptionRepo).save(reception);
        assertEquals("1 Updated", result);
    }

    @Test
    public void testUpdateReception_BranchNotFound() {
        // Arrange
        receptionSaveDTO.setBranchId(branch.getBranchId());
        when(branchRepo.findById(branch.getBranchId())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            receptionService.updateReception(receptionSaveDTO, reception.getReceptionId());
        });

        assertEquals("Branch not found", exception.getMessage());
        verify(receptionRepo, never()).save(any());
    }

    @Test
    public void testUpdateReception_NotFound() {
        // Arrange
        receptionSaveDTO.setBranchId(branch.getBranchId());
        when(branchRepo.findById(branch.getBranchId())).thenReturn(Optional.of(branch));
        when(receptionRepo.findById(reception.getReceptionId())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            receptionService.updateReception(receptionSaveDTO, reception.getReceptionId());
        });

        assertEquals("Reception not found", exception.getMessage());
        verify(receptionRepo, never()).save(any());
    }
}