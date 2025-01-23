package com.jeewaeducation.user_management.service.impl;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.*;
import com.jeewaeducation.user_management.service.BranchService;
import com.jeewaeducation.user_management.utility.mappers.BranchMapper;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jeewaeducation.user_management.entity.BranchManager;

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
        BranchManager branchManager = branchManagerRepo.findById(branchSaveDTO.getBranchManagerId()).orElseThrow(() ->
                new NotFoundException("Branch Manager not found with ID: " + branchSaveDTO.getBranchManagerId()));
        branch.setBranchManager(branchManager);
        //branch.setBranchID(0);//Ensure BranchID is not set from DTO
        branchRepo.findById(branch.getBranchID()).ifPresent(e -> {
            throw new NotFoundException("Branch already exists");
        });
        branchRepo.save(branch);
        return branch.getBranchID() + " Saved";

    }
    @Override
    public String updateBranch(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        BranchManager branchManager = branchManagerRepo.findById(branchDTO.getBranchManagerId()).orElseThrow(() ->
                new NotFoundException("Branch Manager not found with ID: " + branchDTO.getBranchManagerId()));
        branch.setBranchManager(branchManager);
        branchRepo.findById(branch.getBranchID()).orElseThrow(() ->
                new EntityNotFoundException("Branch not found with ID: " + branch.getBranchID()));
        branchRepo.save(branch);
        return branch.getBranchID() + " Updated";

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
    public BranchDTO getBranch(int id) {
        Branch branch = branchRepo.findById(id).orElseThrow(() -> new NotFoundException("Branch not found"));
        return modelMapper.map(branch, BranchDTO.class);
    }

    @Override
    public List<BranchDTO> getAllBranch() {
        List<Branch> branches = branchRepo.findAll();
        if (branches.isEmpty()) {
            throw new NotFoundException("No branches found");
        }
        return branchMapper.entityListToDtoList(branches);
    }


}
