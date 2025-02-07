package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.dto.branch.Branch_BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManager_BranchDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.BranchManager;
import com.jeewaeducation.user_management.exception.AlreadyAssignedException;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.*;
import com.jeewaeducation.user_management.utility.mappers.BranchMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceIMPLTest {
    @Mock
    private BranchRepo branchRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CounselorRepo counselorRepo;
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private ReceptionRepo receptionRepo;
    @Mock
    private BranchManagerRepo branchManagerRepo;
    @InjectMocks
    private BranchServiceIMPL branchServiceIMPL;

    @Test
    public void saveBranch_returnString(){
        //Arrange
        BranchSaveDTO branchSaveDTO = mock(BranchSaveDTO.class);
        Branch branch = new Branch();
        branch.setBranchId(0);
        branch.setBranchName(branchSaveDTO.getBranchName());
        BranchManager branchManager = mock(BranchManager.class);

        branch.setBranchManager(branchManager);

        //Mocking
        when(modelMapper.map(branchSaveDTO,Branch.class))
                .thenReturn(branch);
        when(branchRepo.save(branch))
                .thenReturn(branch);

        String string = branchServiceIMPL.saveBranch(branchSaveDTO);

        assertEquals(branch.getBranchId() + " Saved",string);
        verify(modelMapper,times(1))
                .map(branchSaveDTO,Branch.class);
        verify(branchRepo,times(1))
                .save(branch);

    }

    @Test
    public void updateBranch_WhenBranchUpdated_ReturnString(){

        int branchId = 1;
        String branchName = "Test";
        int branchManagerId = 100;
        //Arrange
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchID(branchId);
        branchDTO.setBranchName(branchName);
        branchDTO.setBranchManagerId(branchManagerId);

        Branch existingBranch = new Branch();
        existingBranch.setBranchId(branchId);
        existingBranch.setBranchName("Old Branch Name");

        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerId(branchManagerId);
        branchManager.setBranch(null);

        when(branchRepo.findById(branchDTO.getBranchID()))
                .thenReturn(Optional.of(existingBranch));
        when(branchManagerRepo.findById(branchDTO.getBranchManagerId()))
                .thenReturn(Optional.of(branchManager));

        ArgumentCaptor<Branch> branchArgumentCaptor = ArgumentCaptor
                .forClass(Branch.class);
        ArgumentCaptor<BranchManager> branchManagerArgumentCaptor = ArgumentCaptor
                .forClass(BranchManager.class);

        //Act
        String result = branchServiceIMPL.updateBranch(branchDTO);
        assertEquals(branchId + " Updated",result);

        verify(branchRepo).findById(branchId);
        verify(branchManagerRepo).findById(branchManagerId);

        verify(branchRepo).save(branchArgumentCaptor.capture());
        verify(branchManagerRepo).save(branchManagerArgumentCaptor.capture());

        Branch savedBranch = branchArgumentCaptor.getValue();
        assertEquals(branchName,savedBranch.getBranchName());
        assertEquals(branchManager,savedBranch.getBranchManager());

        BranchManager savedBranchManager = branchManagerArgumentCaptor.getValue();
        assertEquals(savedBranch,savedBranchManager.getBranch());


        verify(branchRepo,times(1))
                .findById(branchDTO.getBranchID());
        verify(branchManagerRepo,times(1))
                .findById(branchDTO.getBranchManagerId());
        verify(branchRepo,times(1))
                .save(savedBranch);
        verify(branchManagerRepo,times(1))
                .save(branchManager);

    }

    @Test
    public void updateBranch_WhenBranchNotFound_ThrowsNotFoundException() {
        BranchDTO dto = new BranchDTO();
        dto.setBranchID(999);

        when(branchRepo.findById(999)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                branchServiceIMPL.updateBranch(dto)
        );

        assertEquals("Branch not found with ID: " + dto.getBranchID(), exception.getMessage());

        verify(branchRepo).findById(999);
        verify(branchManagerRepo, never()).findById(anyInt());
        verify(branchRepo, never()).save(any());
        verify(branchManagerRepo, never()).save(any());
    }

    @Test
    public void updateBranch_WhenBranchManagerNotFound_ThrowsNotFoundException() {
        BranchDTO dto = new BranchDTO();
        dto.setBranchID(1);
        dto.setBranchManagerId(999);

        Branch branch = new Branch();
        branch.setBranchId(1);

        when(branchRepo.findById(1)).thenReturn(Optional.of(branch));
        when(branchManagerRepo.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                branchServiceIMPL.updateBranch(dto)
        );

        verify(branchRepo).findById(1);
        verify(branchManagerRepo).findById(999);

        verify(branchRepo, never()).save(any());
        verify(branchManagerRepo, never()).save(any());
    }

    @Test
    public void updateBranch_WhenManagerAssignedToOtherBranch_ThrowsAlreadyAssignedException() {
        int targetBranchId = 1;
        int otherBranchId = 2;
        int managerId = 100;

        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchID(targetBranchId);
        branchDTO.setBranchManagerId(managerId);

        Branch existingBranch = new Branch();
        existingBranch.setBranchId(targetBranchId);

        BranchManager manager = new BranchManager();
        manager.setBranchManagerId(managerId);
        manager.setBranch(new Branch(
                otherBranchId,
                "Other Branch",
                null
        ));

        when(branchRepo.findById(targetBranchId))
                .thenReturn(Optional.of(existingBranch));
        when(branchManagerRepo.findById(managerId))
                .thenReturn(Optional.of(manager));

        AlreadyAssignedException exception = assertThrows(
                AlreadyAssignedException.class,
                () -> branchServiceIMPL.updateBranch(branchDTO)
        );

        assertEquals("Branch Manager is already assigned to another branch", exception.getMessage());

        // Verify no saves occurred
        verify(branchRepo, never()).save(any());
        verify(branchManagerRepo, never()).save(any());
    }

    @Test
    public void deleteBranch_WhenBranchNotFound_ThrowsNotFoundException() {
        int branchId = 1;

        when(branchRepo.findById(branchId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                branchServiceIMPL.deleteBranch(branchId)
        );

        assertEquals("Branch not found", exception.getMessage());

        verify(branchRepo).findById(branchId);
        verify(branchRepo, never()).deleteById(anyInt());
    }

    @Test
    public void deleteBranch_WhenBranchReferencedByBranchManager_ThrowsForeignKeyConstraintViolationException() {
        int branchId = 1;

        Branch branch = new Branch();
        branch.setBranchId(branchId);

        when(branchRepo.findById(branchId))
                .thenReturn(Optional.of(branch));
        when(branchManagerRepo.existsByBranch(branch))
                .thenReturn(true);

        ForeignKeyConstraintViolationException exception = assertThrows(ForeignKeyConstraintViolationException.class, () ->
                branchServiceIMPL.deleteBranch(branchId)
        );

        assertEquals("Cannot delete branch as it is referenced by other records with Branch Manager", exception.getMessage());

        verify(branchRepo).findById(branchId);
        verify(branchManagerRepo).existsByBranch(branch);

        verify(branchRepo, never()).deleteById(anyInt());
    }

    @Test
    public void deleteBranch_WhenBranchReferencedByReception_ThrowsForeignKeyConstraintViolationException() {
        int branchId = 1;

        Branch branch = new Branch();
        branch.setBranchId(branchId);

        when(branchRepo.findById(branchId)).thenReturn(Optional.of(branch));
        when(receptionRepo.existsByBranch(branch)).thenReturn(true);

        ForeignKeyConstraintViolationException exception = assertThrows(ForeignKeyConstraintViolationException.class, () ->
                branchServiceIMPL.deleteBranch(branchId)
        );

        assertEquals("Cannot delete branch as it is referenced by other records with Reception", exception.getMessage());

        verify(branchRepo).findById(branchId);
        verify(receptionRepo).existsByBranch(branch);

        verify(branchRepo, never()).deleteById(anyInt());
    }

    @Test
    public void deleteBranch_WhenBranchReferencedByCounselor_ThrowsForeignKeyConstraintViolationException() {
        int branchId = 1;

        Branch branch = new Branch();
        branch.setBranchId(branchId);

        when(branchRepo.findById(branchId)).thenReturn(Optional.of(branch));
        when(counselorRepo.existsByBranch(branch)).thenReturn(true);

        ForeignKeyConstraintViolationException exception = assertThrows(ForeignKeyConstraintViolationException.class, () ->
                branchServiceIMPL.deleteBranch(branchId)
        );

        assertEquals("Cannot delete branch as it is referenced by other records with Counselor", exception.getMessage());


        verify(branchRepo).findById(branchId);
        verify(counselorRepo).existsByBranch(branch);

        verify(branchRepo, never()).deleteById(anyInt());
    }

    @Test
    public void deleteBranch_WhenBranchReferencedByStudent_ThrowsForeignKeyConstraintViolationException() {
        int branchId = 1;

        Branch branch = new Branch();
        branch.setBranchId(branchId);

        when(branchRepo.findById(branchId)).thenReturn(Optional.of(branch));
        when(studentRepo.existsByBranchId(branch)).thenReturn(true);

        ForeignKeyConstraintViolationException exception = assertThrows(ForeignKeyConstraintViolationException.class, () ->
                branchServiceIMPL.deleteBranch(branchId)
        );

        assertEquals("Cannot delete branch as it is referenced by other records with Student", exception.getMessage());


        verify(branchRepo).findById(branchId);
        verify(studentRepo).existsByBranchId(branch);

        verify(branchRepo, never()).deleteById(anyInt());
    }

    @Test
    public void deleteBranch_WhenNoReferences_DeletesBranch() {
        int branchId = 1;

        Branch branch = new Branch();
        branch.setBranchId(branchId);

        when(branchRepo.findById(branchId))
                .thenReturn(Optional.of(branch));
        when(branchManagerRepo.existsByBranch(branch))
                .thenReturn(false);
        when(receptionRepo.existsByBranch(branch))
                .thenReturn(false);
        when(counselorRepo.existsByBranch(branch))
                .thenReturn(false);
        when(studentRepo.existsByBranchId(branch))
                .thenReturn(false);

        String result = branchServiceIMPL.deleteBranch(branchId);

        assertEquals("Branch " + branchId + " Deleted", result);

        verify(branchRepo,times(1))
                .findById(branchId);
        verify(branchManagerRepo,times(1))
                .existsByBranch(branch);
        verify(receptionRepo,times(1))
                .existsByBranch(branch);
        verify(counselorRepo,times(1))
                .existsByBranch(branch);
        verify(studentRepo,times(1))
                .existsByBranchId(branch);
        verify(branchRepo,times(1))
                .deleteById(branchId);


    }

    @Test
    public void getBranch_WhenBranchNotFound_ThrowsNotFoundException() {
        int branchId = 1;

        when(branchRepo.findById(branchId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                branchServiceIMPL.getBranch(branchId)
        );

        assertEquals("Branch not found", exception.getMessage());

        verify(branchRepo,times(1))
                .findById(branchId);
    }

    @Test
    public void testPrivateMethod_getBranchManagerDto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerId(100);
        branchManager.setBranchManagerName("Doe");
        branchManager.setBranchManagerEmail("test@gmail.com");
        branchManager.setBranchManagerContactNumber(01124567);

        Branch branch = new Branch(1,"Colombo",branchManager);

        Method getBranchManagerBranchDTO =
                BranchServiceIMPL.class
                        .getDeclaredMethod("getBranchManagerBranchDTO", Branch.class);
        getBranchManagerBranchDTO.setAccessible(true);

        BranchManager_BranchDTO branchManagerBranchDTO =
                (BranchManager_BranchDTO) getBranchManagerBranchDTO.invoke(branchServiceIMPL,branch);

        assertEquals(branchManagerBranchDTO.getBranchManagerName()
                ,branchManager.getBranchManagerName());
        assertEquals(branchManagerBranchDTO.getBranchManagerEmail()
                ,branchManager.getBranchManagerEmail());
        assertEquals(branchManagerBranchDTO.getBranchManagerId()
                ,branchManager.getBranchManagerId());
        assertEquals(branchManagerBranchDTO.getBranchManagerContactNumber()
                ,branchManager.getBranchManagerContactNumber());

    }

    @Test
    public void getBranch_WhenBranchFound_ReturnBranch_BranchManagerDTO() {
        int branchId = 1;
        String branchName = "Test Branch";
        int branchManagerId = 100;

        Branch branch = new Branch();
        branch.setBranchId(branchId);
        branch.setBranchName(branchName);

        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerId(branchManagerId);
        branch.setBranchManager(branchManager);

        when(branchRepo.findById(branchId))
                .thenReturn(Optional.of(branch));


        Branch_BranchManagerDTO result = branchServiceIMPL.getBranch(branchId);

        System.out.println(result);
//        assertEquals(branch.getBranchId(),result.getBranchID());
//        assertEquals(branch.getBranchName(),result.getBranchName());
//

    }



}