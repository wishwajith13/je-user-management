package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.branchManager.BranchManagerDTO;
import com.jeewaeducation.user_management.dto.branchManager.BranchManagerSaveDTO;
import com.jeewaeducation.user_management.entity.BranchManager;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.BranchManagerRepo;
import com.jeewaeducation.user_management.service.BranchManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jeewaeducation.user_management.utility.mappers.BranchManagerMapper;

import java.util.List;

@Service
public class BranchManagerServiceIMPL implements BranchManagerService {
    @Autowired
    private BranchManagerRepo branchManagerRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BranchManagerMapper branchManagerMapper;

    @Override
    public String saveBranchManager(BranchManagerSaveDTO branchManagerSaveDTO) {
        BranchManager branchManager = modelMapper.map(branchManagerSaveDTO, BranchManager.class);
        branchManagerRepo.findById(branchManager.getBranchManagerId()).ifPresent(e -> {
            throw new DuplicateKeyException("BranchManager already exists");
        });
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
    public List<BranchManagerDTO> getAllBranchManager() {
        List<BranchManager> branchManagers = branchManagerRepo.findAll();
        if (branchManagers.isEmpty()) {
            throw new NotFoundException("No Branch Manager found");
        }
        return branchManagerMapper.entityListToDtoList(branchManagers);
    }

    @Override
    public String updateBranchManager(BranchManagerDTO branchManagerDTO) {
        BranchManager branchManager = modelMapper.map(branchManagerDTO, BranchManager.class);
        branchManagerRepo.findById(branchManager.getBranchManagerId()).orElseThrow(() -> new NotFoundException("Branch Manager not found"));
        branchManagerRepo.save(branchManager);
        return branchManager.getBranchManagerId() + " Updated";
    }

    @Override
    public BranchManagerDTO getBranchManagerById(int id) {
        BranchManager branchManager = branchManagerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch Manager not found"));
        return modelMapper.map(branchManager, BranchManagerDTO.class);
    }
}