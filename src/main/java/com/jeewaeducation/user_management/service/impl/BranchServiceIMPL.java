package com.jeewaeducation.user_management.service.impl;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.repo.CounselorRepo;
import com.jeewaeducation.user_management.repo.ReceptionRepo;
import com.jeewaeducation.user_management.repo.StudentRepo;
import com.jeewaeducation.user_management.service.BranchService;
import com.jeewaeducation.user_management.utility.mappers.BranchMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public String saveBranch(BranchSaveDTO branchSaveDTO) {
        Branch branch = modelMapper.map(branchSaveDTO, Branch.class);
        branch.setBranchID(0);//Ensure BranchID is not set from DTO
        branchRepo.save(branch);
        return branch.getBranchID() + " Saved";

    }
    @Override
    public String updateBranch(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        branchRepo.findById(branch.getBranchID()).orElseThrow(() -> new NotFoundException("Branch not found"));
        branchRepo.save(branch);
        return branch.getBranchID() + " Updated";

    }

    @Override
    public String deleteBranch(int id) {
        Branch branch = branchRepo.findById(id).orElseThrow(() -> new NotFoundException("Branch not found"));
        boolean isReferencedByCounselor = counselorRepo.existsByBranch(branch);
        boolean isReferencedByStudent = studentRepo.existsByBranchId(branch);
        boolean isReferencedByReception = receptionRepo.existsByBranch(branch);
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
