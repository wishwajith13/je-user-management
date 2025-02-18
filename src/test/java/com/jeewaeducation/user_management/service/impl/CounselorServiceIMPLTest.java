package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.counselor.CounselorGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
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
import org.modelmapper.TypeToken;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CounselorServiceIMPLTest {
    @Mock
    private CounselorRepo counselorRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BranchRepo branchRepo;
    @Mock
    private StudentRepo studentRepo;
    @InjectMocks
    private CounselorServiceIMPL counselorServiceIMPL;


    @Test
    public void saveCounselor_WhenBranchNotFound_ThrowNotFoundException() {
        CounselorSaveDTO counselorSaveDTO = new CounselorSaveDTO();
        counselorSaveDTO.setBranch(1);
        when(branchRepo.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> counselorServiceIMPL.saveCounselor(counselorSaveDTO));

        assertEquals("Branch not found", exception.getMessage());
        verify(modelMapper,never()).map(any(), any());
        verify(counselorRepo, never()).save(any());
    }

    @Test
    public void saveCounselor_WhenBranchFound_SaveCounselor() {
        CounselorSaveDTO counselorSaveDTO = new CounselorSaveDTO();
        counselorSaveDTO.setBranch(1);
        counselorSaveDTO.setCounselorName("Test");
        counselorSaveDTO.setCounselorPhoneNumber("1234567890");
        counselorSaveDTO.setCounselorEmail("test@gmail.com");

        Branch branch = new Branch();
        branch.setBranchId(1);

        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        counselor.setBranch(branch);
        counselor.setCounselorName("Test");
        counselor.setCounselorPhoneNumber("1234567890");
        counselor.setCounselorEmail("test@gmail.com");

        when(branchRepo.findById(1)).thenReturn(Optional.of(branch));

        when(modelMapper.map(counselorSaveDTO, Counselor.class))
                .thenReturn(counselor);

        ArgumentCaptor<Counselor> counselorArgumentCaptor = ArgumentCaptor.forClass(Counselor.class);

        when(counselorRepo.save(counselorArgumentCaptor.capture())).thenReturn(counselor);

        String result = counselorServiceIMPL.saveCounselor(counselorSaveDTO);

        assertEquals("0 Saved", result);

        Counselor capturedCounselor = counselorArgumentCaptor.getValue();

        assertEquals(1, capturedCounselor.getBranch().getBranchId());
        assertEquals("Test", capturedCounselor.getCounselorName());
        assertEquals("1234567890", capturedCounselor.getCounselorPhoneNumber());

        verify(modelMapper).map(counselorSaveDTO, Counselor.class);
        verify(counselorRepo).save(any());
    }

    @Test
    public void deleteCounselor_WhenCounselorNotFound_ThrowNotFoundException() {
        int counselorId = 1;
        when(counselorRepo.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> counselorServiceIMPL.deleteCounselor(counselorId));

        assertEquals("Counselor not found", exception.getMessage());

        verify(studentRepo, never()).existsByCounselorId(any());
        verify(counselorRepo, never()).deleteById(any());
    }

    @Test
    public void deleteCounselor_WhenCounselorFoundAndReferenced_ThrowForeignKeyConstraintViolationException() {
        int counselorId = 1;
        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        when(counselorRepo.findById(1)).thenReturn(Optional.of(counselor));
        when(studentRepo.existsByCounselorId(counselor)).thenReturn(true);

        Exception exception = assertThrows(ForeignKeyConstraintViolationException.class,
                () -> counselorServiceIMPL.deleteCounselor(counselorId));

        assertEquals("Cannot delete counselor as it is referenced by other records", exception.getMessage());

        verify(counselorRepo, never()).deleteById(any());
    }

    @Test
    public void deleteCounselor_WhenCounselorFoundAndNotReferenced_DeleteCounselor() {
        int counselorId = 1;
        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        when(counselorRepo.findById(1)).thenReturn(Optional.of(counselor));
        when(studentRepo.existsByCounselorId(counselor)).thenReturn(false);

        String result = counselorServiceIMPL.deleteCounselor(counselorId);

        assertEquals("Counselor with ID 1 has been Deleted", result);

        verify(studentRepo,times(1))
                .existsByCounselorId(counselor);
        verify(counselorRepo,times(1))
                .findById(1);
        verify(counselorRepo,times(1))
                .deleteById(1);
    }

    @Test
    public void updateCounselor_WhenBranchNotFound_ThrowNotFoundException() {
        CounselorSaveDTO counselorSaveDTO = new CounselorSaveDTO();
        counselorSaveDTO.setBranch(1);
        int counselorId = 1;

        when(branchRepo.findById(counselorSaveDTO.getBranch()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> counselorServiceIMPL.updateCounselor(counselorSaveDTO, counselorId));

        assertEquals("Branch not found", exception.getMessage());
        verify(modelMapper,never()).map(any(), any());
        verify(counselorRepo, never()).save(any());
    }

    @Test
    public void updateCounselor_WhenCounselorNotFound_ThrowNotFoundException() {
        CounselorSaveDTO counselorSaveDTO = new CounselorSaveDTO();
        counselorSaveDTO.setBranch(1);
        counselorSaveDTO.setCounselorName("Test");
        counselorSaveDTO.setCounselorPhoneNumber("1234567890");
        int counselorId = 1;

        Branch branch = new Branch();
        branch.setBranchId(1);

        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        counselor.setBranch(branch);
        counselor.setCounselorName("Test");
        counselor.setCounselorPhoneNumber("1234567890");

        when(branchRepo.findById(1))
                .thenReturn(Optional.of(branch));

        when(modelMapper.map(counselorSaveDTO, Counselor.class))
                .thenReturn(counselor);

        when(counselorRepo.existsById(counselorId))
                .thenReturn(false);

        Exception exception = assertThrows(NotFoundException.class,
                () -> counselorServiceIMPL.updateCounselor(counselorSaveDTO, counselorId));

        assertEquals("Counselor Not Found", exception.getMessage());

        verify(branchRepo, times(1))
                .findById(1);
        verify(modelMapper, times(1))
                .map(counselorSaveDTO, Counselor.class);
        verify(counselorRepo, never())
                .save(any());

    }

    @Test
    public void updateCounselor_WhenCounselorFound_UpdateCounselor() {
        CounselorSaveDTO counselorSaveDTO = new CounselorSaveDTO();
        counselorSaveDTO.setBranch(1);
        counselorSaveDTO.setCounselorName("Test");
        counselorSaveDTO.setCounselorPhoneNumber("1234567890");
        int counselorId = 1;

        Branch branch = new Branch();
        branch.setBranchId(1);

        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        counselor.setBranch(branch);
        counselor.setCounselorName("Updated Test");
        counselor.setCounselorPhoneNumber("1234567890");

        when(branchRepo.findById(1))
                .thenReturn(Optional.of(branch));

        when(modelMapper.map(counselorSaveDTO, Counselor.class))
                .thenReturn(counselor);

        when(counselorRepo.existsById(counselorId))
                .thenReturn(true);


        ArgumentCaptor<Counselor> counselorArgumentCaptor = ArgumentCaptor.forClass(Counselor.class);

        when(counselorRepo.save(counselorArgumentCaptor.capture()))
                .thenReturn(counselor);

        String result = counselorServiceIMPL.updateCounselor(counselorSaveDTO, counselorId);

        assertEquals(counselor+"Counselor Updated", result);

        Counselor capturedCounselor = counselorArgumentCaptor.getValue();

        assertEquals(1, capturedCounselor.getBranch().getBranchId());
        assertEquals("Updated Test", capturedCounselor.getCounselorName());
        assertEquals("1234567890", capturedCounselor.getCounselorPhoneNumber());

        verify(branchRepo, times(1))
                .findById(1);
        verify(modelMapper, times(1))
                .map(counselorSaveDTO, Counselor.class);
        verify(counselorRepo, times(1))
                .save(counselor);

    }

    @Test
    public void getCounselor_WhenCounselorNotFound_ThrowNotFoundException() {
        int counselorId = 1;
        when(counselorRepo.existsById(counselorId))
                .thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> counselorServiceIMPL.getCounselor(counselorId));

        assertEquals("Counselor Not Found", exception.getMessage());

        verify(counselorRepo, times(1))
                .existsById(counselorId);
        verify(counselorRepo, never())
                .findById(counselorId);
        verify(modelMapper, never())
                .map(any(), any());
    }

    @Test
    public void getCounselor_WhenCounselorFound_ReturnCounselor() {
        int counselorId = 1;
        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        counselor.setCounselorName("Test");
        counselor.setCounselorPhoneNumber("1234567890");

        CounselorGetDTO counselorGetDTO = new CounselorGetDTO();
        counselorGetDTO.setCounselorId(1);
        counselorGetDTO.setCounselorName("Test");
        counselorGetDTO.setCounselorPhoneNumber("1234567890");


        when(counselorRepo.existsById(counselorId))
                .thenReturn(true);

        when(counselorRepo.findById(counselorId))
                .thenReturn(Optional.of(counselor));

        when(modelMapper.map(counselor, CounselorGetDTO.class))
                .thenReturn(counselorGetDTO);

        CounselorGetDTO result = counselorServiceIMPL.getCounselor(counselorId);

        assertEquals(counselorGetDTO, result);

        verify(counselorRepo, times(1))
                .existsById(counselorId);
        verify(counselorRepo, times(1))
                .findById(counselorId);
        verify(modelMapper, times(1))
                .map(counselor, CounselorGetDTO.class);
    }

    @Test
    public void getAllCounselors_ReturnAllCounselors() {
        Counselor counselor1 = new Counselor();
        counselor1.setCounselorId(1);
        counselor1.setCounselorName("Test1");
        counselor1.setCounselorPhoneNumber("1234567890");

        Counselor counselor2 = new Counselor();
        counselor2.setCounselorId(2);
        counselor2.setCounselorName("Test2");
        counselor2.setCounselorPhoneNumber("1234567890");

        CounselorGetDTO counselorGetDTO1 = new CounselorGetDTO();
        counselorGetDTO1.setCounselorId(1);
        counselorGetDTO1.setCounselorName("Test1");
        counselorGetDTO1.setCounselorPhoneNumber("1234567890");

        CounselorGetDTO counselorGetDTO2 = new CounselorGetDTO();
        counselorGetDTO2.setCounselorId(2);
        counselorGetDTO2.setCounselorName("Test2");
        counselorGetDTO2.setCounselorPhoneNumber("1234567890");

        when(counselorRepo.findAll())
                .thenReturn(java.util.List.of(counselor1, counselor2));

        when(modelMapper.map(java.util.List.of(counselor1, counselor2), new TypeToken<List<CounselorGetDTO>>() {}.getType()))
                .thenReturn(java.util.List.of(counselorGetDTO1, counselorGetDTO2));

        List<CounselorGetDTO> result = counselorServiceIMPL.getAllCounselors();

        assertEquals(java.util.List.of(counselorGetDTO1, counselorGetDTO2), result);

        verify(counselorRepo, times(1))
                .findAll();
        verify(modelMapper, times(1))
                .map(java.util.List.of(counselor1, counselor2), new TypeToken<List<CounselorGetDTO>>() {}.getType());
    }




}