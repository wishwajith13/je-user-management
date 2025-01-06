package com.jeewaeducation.user_management.utility.mappers;

import com.jeewaeducation.user_management.dto.application.ApplicationStudentBasicDetailsGetDTO;
import com.jeewaeducation.user_management.entity.Application;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    List<ApplicationStudentBasicDetailsGetDTO> entityListToDtoList(List<Application> items);
}
