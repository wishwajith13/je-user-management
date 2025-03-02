package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.branchManager.BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.BranchManager;
import com.jeewaeducation.user_management.exception.AlreadyAssignedException;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.BranchManagerRepo;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.service.BranchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchManagerServiceIMPLTest {

    @Mock
    private BranchManagerRepo branchManagerRepo;

    @Mock
    private BranchRepo branchRepo;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchManagerServiceIMPL branchManagerServiceIMPL;

    @Test
    public void saveBranchManager_WhenBranchManagerAlreadyExists_ThrowsDuplicateKeyException() {
        BranchManagerSaveDTO branchManagerSaveDTO = new BranchManagerSaveDTO();
        branchManagerSaveDTO.setBranchManagerName("Test Manager");
        branchManagerSaveDTO.setBranchManagerContactNumber(1234567890);
        branchManagerSaveDTO.setBranchManagerEmail("test@example.com");
        branchManagerSaveDTO.setBranch(1);


        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerId(0); // Default ID is 0

        when(branchManagerRepo.findById(branchManager.getBranchManagerId())).thenReturn(Optional.of(branchManager));

        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> {
            branchManagerServiceIMPL.saveBranchManager(branchManagerSaveDTO);
        });

        assertEquals("BranchManager already exists", exception.getMessage());
        verify(branchManagerRepo, times(1)).findById(branchManager.getBranchManagerId());
    }

    @Test
    public void saveBranchManager_WhenBranchNotFound_ThrowsNotFoundException() {
        BranchManagerSaveDTO branchManagerSaveDTO = new BranchManagerSaveDTO();
        branchManagerSaveDTO.setBranchManagerName("Test Manager");
        branchManagerSaveDTO.setBranchManagerContactNumber(1234567890);
        branchManagerSaveDTO.setBranchManagerEmail("test@example.com");
        branchManagerSaveDTO.setBranch(1);

        when(branchRepo.findById(branchManagerSaveDTO.getBranch())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            branchManagerServiceIMPL.saveBranchManager(branchManagerSaveDTO);
        });

        assertEquals("Branch not found with ID: 1", exception.getMessage());
        verify(branchRepo, times(1)).findById(branchManagerSaveDTO.getBranch());
    }

    @Test
    public void saveBranchManager_WhenBranchManagerAlreadyAssigned_ThrowsAlreadyAssignedException() {
        BranchManagerSaveDTO branchManagerSaveDTO = new BranchManagerSaveDTO();
        branchManagerSaveDTO.setBranchManagerName("Test Manager");
        branchManagerSaveDTO.setBranchManagerContactNumber(1234567890);
        branchManagerSaveDTO.setBranchManagerEmail("test@example.com");
        branchManagerSaveDTO.setBranch(1);

        Branch branch = new Branch();
        branch.setBranchId(1);
        branch.setBranchManager(new BranchManager());

        when(branchRepo.findById(branchManagerSaveDTO.getBranch())).thenReturn(Optional.of(branch));

        AlreadyAssignedException exception = assertThrows(AlreadyAssignedException.class, () -> {
            branchManagerServiceIMPL.saveBranchManager(branchManagerSaveDTO);
        });

        assertEquals("A Branch Manager is already assigned to given branch", exception.getMessage());
        verify(branchRepo, times(1)).findById(branchManagerSaveDTO.getBranch());
    }

    @Test
    public void saveBranchManager_Success() {
        BranchManagerSaveDTO branchManagerSaveDTO = new BranchManagerSaveDTO();
        branchManagerSaveDTO.setBranchManagerName("Test Manager");
        branchManagerSaveDTO.setBranchManagerContactNumber(1234567890);
        branchManagerSaveDTO.setBranchManagerEmail("test@example.com");
        branchManagerSaveDTO.setBranch(1);

        Branch branch = new Branch();
        branch.setBranchId(1);

        when(branchRepo.findById(branchManagerSaveDTO.getBranch())).thenReturn(Optional.of(branch));
        when(branchManagerRepo.save(any(BranchManager.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = branchManagerServiceIMPL.saveBranchManager(branchManagerSaveDTO);

        assertTrue(result.contains("Saved"));
        verify(branchRepo, times(1)).findById(branchManagerSaveDTO.getBranch());
        verify(branchManagerRepo, times(1)).save(any(BranchManager.class));
    }

    @Test
    public void deleteBranchManager_WhenBranchManagerNotFound_ThrowsNotFoundException() {
        int branchManagerId = 1;

        when(branchManagerRepo.findById(branchManagerId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            branchManagerServiceIMPL.deleteBranchManager(branchManagerId);
        });

        assertEquals("BranchManager not found", exception.getMessage());
        verify(branchManagerRepo, times(1)).findById(branchManagerId);
    }

    @Test
    public void deleteBranchManager_Success() {
        int branchManagerId = 1;

        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerId(branchManagerId);

        when(branchManagerRepo.findById(branchManagerId)).thenReturn(Optional.of(branchManager));

        String result = branchManagerServiceIMPL.deleteBranchManager(branchManagerId);

        assertEquals("BranchManager " + branchManagerId + " Deleted", result);
        verify(branchManagerRepo, times(1)).findById(branchManagerId);
        verify(branchManagerRepo, times(1)).deleteById(branchManagerId);
    }

    @Test
    public void updateBranchManager_WhenBranchManagerNotFound_ThrowsNotFoundException() {
        BranchManagerDTO branchManagerDTO = new BranchManagerDTO();
        branchManagerDTO.setBranchManagerId(1);
        branchManagerDTO.setBranchID(1);

        when(branchManagerRepo.findById(branchManagerDTO.getBranchManagerId())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            branchManagerServiceIMPL.updateBranchManager(branchManagerDTO);
        });

        assertEquals("Branch Manager not found", exception.getMessage());
        verify(branchManagerRepo, times(1)).findById(branchManagerDTO.getBranchManagerId());
    }

    @Test
    public void updateBranchManager_Success() {
        BranchManagerDTO branchManagerDTO = new BranchManagerDTO();
        branchManagerDTO.setBranchManagerId(1);
        branchManagerDTO.setBranchManagerName("Updated Manager");
        branchManagerDTO.setBranchManagerContactNumber(1234567890);
        branchManagerDTO.setBranchManagerEmail("updated@example.com");
        branchManagerDTO.setBranchID(1);

        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerId(1);

        Branch branch = new Branch();
        branch.setBranchId(1);

        when(branchManagerRepo.findById(branchManagerDTO.getBranchManagerId())).thenReturn(Optional.of(branchManager));
        when(branchRepo.findById(branchManagerDTO.getBranchID())).thenReturn(Optional.of(branch));
        when(branchManagerRepo.save(any(BranchManager.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = branchManagerServiceIMPL.updateBranchManager(branchManagerDTO);

        assertTrue(result.contains("Updated"));
        verify(branchManagerRepo, times(1)).findById(branchManagerDTO.getBranchManagerId());
        verify(branchRepo, times(1)).findById(branchManagerDTO.getBranchID());
        verify(branchManagerRepo, times(1)).save(any(BranchManager.class));
    }
}