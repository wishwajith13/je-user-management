package com.jeewaeducation.user_management.utility.mappers;


import com.jeewaeducation.user_management.dto.branch.BranchDTO;
import com.jeewaeducation.user_management.dto.branch.BranchSaveDTO;
import com.jeewaeducation.user_management.entity.Branch;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    List<BranchDTO> entityListToDtoList(List<Branch> branches);


}
