package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.counselor.CounselorGetDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CounselorMapper {
    List<CounselorGetDTO> entityListToDtoList(List<CounselorGetDTO> items);
}
