package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;
import com.jeewaeducation.user_management.entity.Reception;
import com.jeewaeducation.user_management.exception.DuplicateKeyException;
import com.jeewaeducation.user_management.exception.NotFoundException;
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

    @Override
    public String saveReception(ReceptionSaveDTO receptionSaveDTO) {
        Reception reception = modelMapper.map(receptionSaveDTO, Reception.class);
        receptionRepo.findById(reception.getReceptionId()).ifPresent(e -> {
            throw new DuplicateKeyException("Reception already exists");
        });
        receptionRepo.save(reception);
        return reception.getReceptionId() + " Saved";
    }

    @Override
    public String deleteReception(int id) {
        receptionRepo.findById(id).orElseThrow(() -> new NotFoundException("Reception not found"));
        receptionRepo.deleteById(id);
        return "Reception " + id + " Deleted";
    }

    @Override
    public List<ReceptionDTO> getAllReception() {
        List<Reception> receptions = receptionRepo.findAll();
        if (!receptions.isEmpty()) {
            return receptionMapper.entitListToDtoList(receptions);
        } else {
            throw new NotFoundException("No receptions found");
        }
    }

    @Override
    public String updateReception(ReceptionDTO receptionDTO) {
        Reception reception = modelMapper.map(receptionDTO, Reception.class);
        receptionRepo.findById(reception.getReceptionId()).orElseThrow(() -> new NotFoundException("Reception not found"));
        receptionRepo.save(reception);
        return reception.getReceptionId() + " Updated";
    }
}
