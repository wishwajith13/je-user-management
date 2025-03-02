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
import com.jeewaeducation.user_management.service.CounselorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CounselorServiceIMPL implements CounselorService {

    private  CounselorRepo counselorRepo;

    private  ModelMapper modelMapper;

    private  BranchRepo branchRepo;

    private  StudentRepo studentRepo;

    @Override
    public String saveCounselor(CounselorSaveDTO counselorSaveDTO) {
        Branch branch = branchRepo.findById(counselorSaveDTO.getBranch()).orElseThrow(() -> new NotFoundException("Branch not found"));
        Counselor counselor = modelMapper.map(counselorSaveDTO, Counselor.class);
        counselor.setBranch(branch);
        counselor.setCounselorId(0);
        counselorRepo.save(counselor);
        return counselor.getCounselorId() + " Saved";
    }

    @Override
    public String deleteCounselor(int counselorId) {
        Counselor counselor = counselorRepo.findById(counselorId).orElseThrow(() -> new NotFoundException("Counselor not found"));
        boolean isReferenced = studentRepo.existsByCounselorId(counselor);
        if (isReferenced) {
            throw  new ForeignKeyConstraintViolationException("Cannot delete counselor as it is referenced by other records");
        }
        counselorRepo.deleteById(counselorId);
        return "Counselor with ID "+counselorId+" has been Deleted";

    }

    @Override
    public String updateCounselor(CounselorSaveDTO counselorSaveDTO, int counselorId) {
        Branch branch = branchRepo.findById(counselorSaveDTO.getBranch())
                .orElseThrow(() -> new NotFoundException("Branch not found"));
        counselorRepo.findById(counselorId).orElseThrow(() -> new NotFoundException("Counselor not found"));
        Counselor counselor = modelMapper.map(counselorSaveDTO, Counselor.class);

        counselor.setCounselorId(counselorId);
        counselor.setBranch(branch);
        counselorRepo.save(counselor);
        return "Counselor Updated with ID: " + counselorId;

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
