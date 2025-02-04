package com.jeewaeducation.user_management.service.impl;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.dto.branch.Branch_BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManager_BranchDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.exception.AlreadyAssignedException;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.*;
import com.jeewaeducation.user_management.service.BranchService;
import com.jeewaeducation.user_management.utility.mappers.BranchMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jeewaeducation.user_management.entity.BranchManager;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchServiceIMPL implements BranchService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private CounselorRepo counselorRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ReceptionRepo receptionRepo;

    @Autowired
    private BranchManagerRepo branchManagerRepo;

    @Override
    public String saveBranch(BranchSaveDTO branchSaveDTO) {
        Branch branch = modelMapper.map(branchSaveDTO, Branch.class);
/*       BranchManager branchManager = branchManagerRepo.findById(branchSaveDTO.getBranchManagerId()).orElseThrow(() ->
                new NotFoundException("Branch Manager not found with ID: " + branchSaveDTO.getBranchManagerId()));

      if (branchManager.getBranch() != null) {
            throw new AlreadyAssignedException("Branch Manager is already assigned to another branch");
        }
        branch.setBranchManager(branchManager);*/
        branch.setBranchId(0); // Ensure BranchID is not set from DTO
        branchRepo.save(branch);
        return branch.getBranchId() + " Saved";
    }

    @Override
    public String updateBranch(BranchDTO branchDTO) {
        Branch branch = branchRepo.findById(branchDTO.getBranchID()).orElseThrow(() ->
                new NotFoundException("Branch not found with ID: " + branchDTO.getBranchID()));

        BranchManager branchManager = branchManagerRepo.findById(branchDTO.getBranchManagerId()).orElseThrow(() ->
                new NotFoundException("Branch Manager not found with ID: " + branchDTO.getBranchManagerId()));

        if (branchManager.getBranch() != null && branchManager.getBranch().getBranchId() != branch.getBranchId()) {
            throw new AlreadyAssignedException("Branch Manager is already assigned to another branch");
        }

        branch.setBranchName(branchDTO.getBranchName());
        branch.setBranchManager(branchManager);
        branchManager.setBranch(branch);

        branchRepo.save(branch);
        branchManagerRepo.save(branchManager);

        return branch.getBranchId() + " Updated";
    }

    @Override
    public String deleteBranch(int id) {
        Branch branch = branchRepo.findById(id).orElseThrow(() -> new NotFoundException("Branch not found"));
        boolean isReferencedByCounselor = counselorRepo.existsByBranch(branch);
        boolean isReferencedByStudent = studentRepo.existsByBranchId(branch);
        boolean isReferencedByReception = receptionRepo.existsByBranch(branch);
        boolean isReferencedByBranchManager = branchManagerRepo.existsByBranch(branch);
        if(isReferencedByBranchManager){
            throw new ForeignKeyConstraintViolationException("Cannot delete branch as it is referenced by other records with Branch Manager");
        }
        if(isReferencedByReception){
            throw new ForeignKeyConstraintViolationException("Cannot delete branch as it is referenced by other records with Reception");
        }
        if (isReferencedByCounselor) {
            throw new ForeignKeyConstraintViolationException("Cannot delete branch as it is referenced by other records with Counselor");
        }
        if (isReferencedByStudent) {
            throw new ForeignKeyConstraintViolationException("Cannot delete branch as it is referenced by other records with Student");
        }
        branchRepo.deleteById(id);
        return "Branch " + id + " Deleted";
    }

    @Override
    public Branch_BranchManagerDTO getBranch(int id) {
        Branch branch = branchRepo.findById(id).orElseThrow(() -> new NotFoundException("Branch not found"));

        Branch_BranchManagerDTO branchDTO = new Branch_BranchManagerDTO();
        branchDTO.setBranchID(branch.getBranchId());
        branchDTO.setBranchName(branch.getBranchName());

        BranchManager_BranchDTO branchManagerDTO = getBranchManagerBranchDTO(branch);
        branchDTO.setBranchManagerId(branchManagerDTO);

        return branchDTO;
    }

    @Override
    public List<Branch_BranchManagerDTO> getAllBranch() {
        List<Branch> branches = branchRepo.findAll();
        if (branches.isEmpty()) {
            throw new NotFoundException("No branches found");
        }

        List<Branch_BranchManagerDTO> branchDTOs = new ArrayList<>();
        for (Branch branch : branches) {
            Branch_BranchManagerDTO branchDTO = new Branch_BranchManagerDTO();
            branchDTO.setBranchID(branch.getBranchId());
            branchDTO.setBranchName(branch.getBranchName());

            BranchManager_BranchDTO branchManagerDTO = getBranchManagerBranchDTO(branch);
            branchDTO.setBranchManagerId(branchManagerDTO);

            branchDTOs.add(branchDTO);
        }
        return branchDTOs;
    }

    private static BranchManager_BranchDTO getBranchManagerBranchDTO(Branch branch) {
        BranchManager_BranchDTO branchManagerDTO = new BranchManager_BranchDTO();
        BranchManager branchManager = branch.getBranchManager();
        if (branchManager != null) {
            branchManagerDTO.setBranchManagerId(branchManager.getBranchManagerId());
            branchManagerDTO.setBranchManagerName(branchManager.getBranchManagerName());
            branchManagerDTO.setBranchManagerContactNumber(branchManager.getBranchManagerContactNumber());
            branchManagerDTO.setBranchManagerEmail(branchManager.getBranchManagerEmail());
        }
        return branchManagerDTO;
    }

}
