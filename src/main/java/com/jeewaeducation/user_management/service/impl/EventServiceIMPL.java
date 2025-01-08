package com.jeewaeducation.user_management.service.impl;

import com.jeewaeducation.user_management.dto.event.EventGetDto;
import com.jeewaeducation.user_management.dto.event.EventSaveDto;
import com.jeewaeducation.user_management.entity.Event;
import com.jeewaeducation.user_management.exception.NotFoundException;
import com.jeewaeducation.user_management.repo.EventRepo;
import com.jeewaeducation.user_management.service.EventService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceIMPL implements EventService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepo eventRepo;

    @Override
    public List<EventGetDto> getAllEvents() {
            List<Event> events = eventRepo.findAll();
            return modelMapper.map(events, new TypeToken<List<EventGetDto>>() {}.getType());
    }

    @Override
    public List<EventGetDto> getEventsByDate(String date) {
        return List.of();
    }

    @Override
    public String saveEvent(EventSaveDto eventSaveDTO) {
        Event event = modelMapper.map(eventSaveDTO, Event.class);
        if(!eventRepo.existsById(event.getId())){
            return eventRepo.save(event)+"Event Saved";
        }else{
            throw new NotFoundException("Event Not Found");
        }

    }

    @Override
    public String deleteEvent(int eventId) {
        return null;
    }

    @Override
    public Event getEvent(int eventId) {
        return null;
    }



}
