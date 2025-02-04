package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.branchManager.BranchManagerGetDTO;
import com.jeewaeducation.user_management.entity.BranchManager;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchManagerMapper {
    List<BranchManagerGetDTO> entityListToDtoList(List<BranchManager> items);
}
