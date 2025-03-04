package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.application.ApplicationStudentBasicDetailsGetDTO;
import com.jeewaeducation.user_management.dto.branch.BranchGetDTO;
import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "student", target = "student")
    @Mapping(source = "student.counselorId", target = "counselorId")
    @Mapping(source = "student.branchId", target = "branchId")
    ApplicationStudentBasicDetailsGetDTO toDto(Application application);

    List<ApplicationStudentBasicDetailsGetDTO> entityListToDtoList(List<Application> applications);

    default BranchGetDTO map(Branch branch) {
        if (branch == null) {
            return null;
        }
        BranchGetDTO branchGetDTO = new BranchGetDTO();
        branchGetDTO.setId(branch.getBranchId());
        branchGetDTO.setBranchName(branch.getBranchName());
        return branchGetDTO;
    }

    default int map(Counselor counselor) {
        return counselor != null ? counselor.getCounselorId() : 0;
    }
}