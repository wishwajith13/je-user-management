package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.event.EventGetDto;
import com.jeewaeducation.user_management.dto.event.EventSaveDto;
import com.jeewaeducation.user_management.entity.Event;

import java.util.List;

public interface EventService {
    String saveEvent(EventSaveDto eventSaveDTO);

    String deleteEvent(int eventId);

    EventGetDto getEvent(int eventId);

    List<EventGetDto> getAllEvents();

    String updateEvent(Event event,int id);
}
