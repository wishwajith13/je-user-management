package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Reception;
import com.jeewaeducation.user_management.exception.ForeignKeyConstraintViolationException;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.ApplicationRepo;
import com.jeewaeducation.user_management.repo.BranchRepo;
import com.jeewaeducation.user_management.repo.ReceptionRepo;
import com.jeewaeducation.user_management.service.ReceptionService;
import com.jeewaeducation.user_management.utility.mappers.ReceptionMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionServiceIMPL implements ReceptionService {
    @Autowired
    private ReceptionRepo receptionRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReceptionMapper receptionMapper;
    @Autowired
    private BranchRepo branchRepo;
    @Autowired
    private ApplicationRepo applicationRepo;

    @Override
    public String saveReception(ReceptionSaveDTO receptionSaveDTO) {
        Branch branch = branchRepo.findById(receptionSaveDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found"));
        Reception reception = modelMapper.map(receptionSaveDTO, Reception.class);
        reception.setReceptionId(0);
        reception.setBranch(branch);
        System.out.println(reception);
        receptionRepo.save(reception);
        return reception.getReceptionId() + " Saved";
    }

    @Override
    public String deleteReception(int id) {
        receptionRepo.findById(id).orElseThrow(() -> new NotFoundException("Reception not found"));
        boolean isReferenced = applicationRepo.existsByReception_ReceptionId(id);
        if (isReferenced) {
            throw new ForeignKeyConstraintViolationException("Cannot delete reception as it is referenced by other records");
        }
        receptionRepo.deleteById(id);
        return "Reception " + id + " Deleted";
    }

    @Override
    public List<ReceptionDTO> getAllReception() {
        List<Reception> receptions = receptionRepo.findAll();
        if (receptions.isEmpty()) {
            throw new NotFoundException("No receptions found");
        }
        return receptionMapper.entitListToDtoList(receptions);
    }

    @Override
    public ReceptionDTO getReception(int id) {
        Reception reception = receptionRepo.findById(id).orElseThrow(() -> new NotFoundException("Reception not found"));
        return modelMapper.map(reception, ReceptionDTO.class);
    }

    @Override
    public String updateReception(ReceptionSaveDTO receptionSaveDTO, int receptionId) {
        Branch branch = branchRepo.findById(receptionSaveDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found"));
        receptionRepo.findById(receptionId).orElseThrow(() -> new NotFoundException("Reception not found"));
        Reception reception = modelMapper.map(receptionSaveDTO, Reception.class);
        reception.setReceptionId(receptionId);
        reception.setBranch(branch);
        receptionRepo.save(reception);
        return reception.getReceptionId() + " Updated";
    }
}
