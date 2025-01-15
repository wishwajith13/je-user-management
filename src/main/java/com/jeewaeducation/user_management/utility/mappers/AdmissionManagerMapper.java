package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.admissionManager.AdmissionManagerDTO;
import com.jeewaeducation.user_management.entity.AdmissionManager;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdmissionManagerMapper {
    List<AdmissionManagerDTO> entityListToDtoList(List<AdmissionManager> items);
}
