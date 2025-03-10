package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.reception.ReceptionDTO;
import com.jeewaeducation.user_management.dto.reception.ReceptionSaveDTO;
import com.jeewaeducation.user_management.entity.Reception;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.reactivestreams.Publisher;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceptionMapper {

    @Mapping(source = "branch.branchId", target = "branch.id")
    @Mapping(source = "branch.branchName", target = "branch.branchName")
    ReceptionDTO entityToDto(Reception reception);

    List<ReceptionDTO> entitListToDtoList(List<Reception> receptions);

    //Iterable<? extends Publisher<?>> toReception(ReceptionSaveDTO receptionSaveDTO);
}