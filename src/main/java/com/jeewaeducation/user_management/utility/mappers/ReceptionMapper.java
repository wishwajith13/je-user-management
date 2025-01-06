package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.entity.Reception;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceptionMapper {
    List<ReceptionDTO> entitListToDtoList(List<Reception> items);
}
