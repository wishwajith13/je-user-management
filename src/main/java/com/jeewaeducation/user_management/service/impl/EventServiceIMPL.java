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


    private final ModelMapper modelMapper;
    private final EventRepo eventRepo;


    @Autowired
    public EventServiceIMPL(ModelMapper modelMapper, EventRepo eventRepo) {
        this.modelMapper = modelMapper;
        this.eventRepo = eventRepo;

    }

    @Override
    public List<EventGetDto> getAllEvents() {
            List<Event> events = eventRepo.findAll();
            return modelMapper.map(events, new TypeToken<List<EventGetDto>>() {}.getType());
    }



    @Override
    public String saveEvent(EventSaveDto eventSaveDTO) {
        Event event = modelMapper.map(eventSaveDTO, Event.class);
        if(eventRepo.findById(event.getId()).isEmpty()){
            return eventRepo.save(event)+"Event Saved";
        }else{
            throw new NotFoundException("Event Not Found");
        }

    }

    @Override
    public String deleteEvent(int eventId) {
        if(eventRepo.existsById(eventId)){
            eventRepo.deleteById(eventId);
            return "Event with ID"+eventId+"has been Deleted";
        }else{
            throw new NotFoundException("Event Not Found");
        }

    }

    @Override
    public EventGetDto getEvent(int eventId) {
        if(eventRepo.existsById(eventId)){
            Event event = eventRepo.findById(eventId).orElseThrow(() -> new NotFoundException("Event Not Found"));
            return modelMapper.map(event,EventGetDto.class);
        }else {
            throw new NotFoundException("Event Not Found");
        }
    }
    
    @Override
    public String updateEvent(Event event, int id) {
        if(eventRepo.existsById(id)){
            Event existingEvent = eventRepo.findById(id).orElseThrow(() -> new NotFoundException("Event Not Found"));
            existingEvent.setId(id);
            existingEvent.setTime(event.getTime());
            existingEvent.setDate(event.getDate());
            existingEvent.setTitle(event.getTitle());
            eventRepo.save(existingEvent);
            return "Successfully Updated " + id;
        } else {
            throw new NotFoundException("Event Not Found");
        }
    }
}
