package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerGetDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.BranchManager;
import com.jeewaeducation.user_management.exception.AlreadyAssignedException;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.BranchManagerRepo;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.service.BranchManagerService;
import com.jeewaeducation.user_management.service.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BranchManagerServiceIMPL implements BranchManagerService {
    private BranchManagerRepo branchManagerRepo;
//    private ModelMapper modelMapper;
    private BranchRepo branchRepo;
    private BranchService branchService;
//    private BranchManagerMapper branchManagerMapper;

    @Override
    public String saveBranchManager(BranchManagerSaveDTO branchManagerSaveDTO) {
        BranchManager branchManager = new BranchManager();
        branchManager.setBranchManagerName(branchManagerSaveDTO.getBranchManagerName());
        branchManager.setBranchManagerContactNumber(branchManagerSaveDTO.getBranchManagerContactNumber());
        branchManager.setBranchManagerEmail(branchManagerSaveDTO.getBranchManagerEmail());

        branchManagerRepo.findById(branchManager.getBranchManagerId()).ifPresent(e -> {
            throw new DuplicateKeyException("BranchManager already exists");
        });

        Branch branch = branchRepo.findById(branchManagerSaveDTO.getBranch()).orElseThrow(() ->
                new NotFoundException("Branch not found with ID: " + branchManagerSaveDTO.getBranch()));

        if (branch.getBranchManager() != null) {
            throw new AlreadyAssignedException("A Branch Manager is already assigned to given branch");
        }

        branch.setBranchManager(branchManager);

        branchManager.setBranch(branch);
        branchManager.setBranchManagerId(0);

        branchManagerRepo.save(branchManager);
        return branchManager.getBranchManagerId() + " Saved";
    }

    @Override
    public String deleteBranchManager(int id) {
        branchManagerRepo.findById(id).orElseThrow(() -> new NotFoundException("BranchManager not found"));
        branchManagerRepo.deleteById(id);
        return "BranchManager " + id + " Deleted";
    }

    @Override
    public List<BranchManagerGetDTO> getAllBranchManager() {
        List<BranchManager> branchManagers = branchManagerRepo.findAll();
        if (branchManagers.isEmpty()) {
            throw new NotFoundException("No Branch Manager found");
        }

        List<BranchManagerGetDTO> branchManagerDTOs = new ArrayList<>();
        for (BranchManager branchManager : branchManagers) {
            branchManagerDTOs.add(mapToBranchManagerGetDTO(branchManager));
        }
        return branchManagerDTOs;
    }

    @Override
    public BranchManagerGetDTO getBranchManagerById(int id) {
        BranchManager branchManager = branchManagerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch Manager not found"));
        return mapToBranchManagerGetDTO(branchManager);
    }

    @Transactional
    public String updateBranchManager(BranchManagerDTO branchManagerDTO) {
        BranchManager branchManager = branchManagerRepo.findById(branchManagerDTO.getBranchManagerId())
                .orElseThrow(() -> new NotFoundException("Branch Manager not found"));

        // Clear existing branch manager from the branch if necessary
        Branch existingBranch = branchManager.getBranch();
        if (existingBranch != null) {
            existingBranch.setBranchManager(null);
            branchManager.setBranch(null);
            branchRepo.save(existingBranch);
            branchManagerRepo.save(branchManager);
        }

        // Update branch manager details
        branchManager.setBranchManagerName(branchManagerDTO.getBranchManagerName());
        branchManager.setBranchManagerContactNumber(branchManagerDTO.getBranchManagerContactNumber());
        branchManager.setBranchManagerEmail(branchManagerDTO.getBranchManagerEmail());

        // Find and assign the new branch
        Branch branch = branchRepo.findById(branchManagerDTO.getBranchID())
                .orElseThrow(() -> new NotFoundException("Branch not found with ID: " + branchManagerDTO.getBranchID()));
        if (branch.getBranchManager() != null && branch.getBranchManager().getBranchManagerId() != branchManager.getBranchManagerId()) {
            throw new AlreadyAssignedException("A Branch Manager is already assigned to the given branch");
        }

        branchManager.setBranch(branch);
        branch.setBranchManager(branchManager);

        // Update the branch details
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchID(branch.getBranchId());
        branchDTO.setBranchName(branch.getBranchName());
        branchDTO.setBranchManagerId(branchManager.getBranchManagerId());

        try {
            branchService.updateBranch(branchDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        branchManagerRepo.save(branchManager);

        return branchManager.getBranchManagerId() + " Updated";
    }

    private BranchManagerGetDTO mapToBranchManagerGetDTO(BranchManager branchManager) {
        BranchManagerGetDTO branchManagerDTO = new BranchManagerGetDTO();
        branchManagerDTO.setBranchManagerId(branchManager.getBranchManagerId());
        branchManagerDTO.setBranchManagerName(branchManager.getBranchManagerName());
        branchManagerDTO.setBranchManagerContactNumber(branchManager.getBranchManagerContactNumber());
        branchManagerDTO.setBranchManagerEmail(branchManager.getBranchManagerEmail());

        BranchGetDTO branchDTO = new BranchGetDTO();
        if (branchManager.getBranch() != null) {
            branchDTO.setId(branchManager.getBranch().getBranchId());
            branchDTO.setBranchName(branchManager.getBranch().getBranchName());
        }
        branchManagerDTO.setBranchID(branchDTO);

        return branchManagerDTO;
    }
}