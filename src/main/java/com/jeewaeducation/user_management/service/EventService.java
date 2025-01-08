package com.jeewaeducation.user_management.service;

import com.jeewaeducation.user_management.dto.event.EventGetDto;
import com.jeewaeducation.user_management.dto.event.EventSaveDto;
import com.jeewaeducation.user_management.entity.Event;

import java.util.List;

public interface EventService {
    String saveEvent(EventSaveDto eventSaveDTO);

    String deleteEvent(int eventId);

    Event getEvent(int eventId);

    List<EventGetDto> getAllEvents();

    List<EventGetDto> getEventsByDate(String date);
}
