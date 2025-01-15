package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.counselor.CounselorGetDTO;
import com.jeewaeducation.user_management.dto.counselor.CounselorSaveDTO;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.CounselorRepo;
import com.jeewaeducation.user_management.service.CounselorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounselorServiceIMPL implements CounselorService {

    private final CounselorRepo counselorRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CounselorServiceIMPL(CounselorRepo counselorRepo, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.counselorRepo = counselorRepo;
    }

    @Override
    public String saveCounselor(CounselorSaveDTO counselorSaveDTO) {
        Counselor counselor = modelMapper.map(counselorSaveDTO, Counselor.class);
//        System.out.println("Saving Counselor: " + counselor);
        counselor.setCounselorId(0); // Ensure counselorId is not set from DTO
//        System.out.println("Saving Counselor: " + counselor);
        counselorRepo.save(counselor);
        return counselor.getCounselorId() + " Saved";
    }

    @Override
    public String deleteCounselor(int counselorId) {
        if(counselorRepo.existsById(counselorId)){
            counselorRepo.deleteById(counselorId);
            return "Counselor with ID"+counselorId+"has been Deleted";
        }else{
            throw new NotFoundException("Counselor Not Found");
        }
    }

    @Override
    public String updateCounselor(CounselorSaveDTO counselorSaveDTO, int counselorId) {
        Counselor counselor = modelMapper.map(counselorSaveDTO, Counselor.class);
        if(counselorRepo.existsById(counselorId)){
            counselor.setCounselorId(counselorId);
            return counselorRepo.save(counselor)+"Counselor Updated";
        }else{
            throw new NotFoundException("Counselor Not Found");
        }
    }

    @Override
    public CounselorGetDTO getCounselor(int counselorId) {
        if(counselorRepo.existsById(counselorId)){
            Counselor counselor = counselorRepo.findById(counselorId).orElseThrow(() -> new NotFoundException("Counselor not found"));
            return modelMapper.map(counselor, CounselorGetDTO.class);
        }else{
            throw new NotFoundException("Counselor Not Found");
        }
    }

    @Override
    public List<CounselorGetDTO> getAllCounselors() {
        List<Counselor> counselors = counselorRepo.findAll();
        return modelMapper.map(counselors, new TypeToken<List<CounselorGetDTO>>() {}.getType());
    }

}
