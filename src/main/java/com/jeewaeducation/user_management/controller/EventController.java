package com.jeewaeducation.user_management.controller;


import com.jeewaeducation.user_management.dto.event.EventGetDto;
import com.jeewaeducation.user_management.dto.event.EventSaveDto;
import com.jeewaeducation.user_management.entity.Event;
import com.jeewaeducation.user_management.service.EventService;
import com.jeewaeducation.user_management.utility.StandardResponse;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/event")
public class EventController {

    @Autowired
    private EventService eventService;


    @PostMapping("/save")
    public ResponseEntity<StandardResponse> saveEvent (@RequestBody EventSaveDto eventSaveDto){
        String message = eventService.saveEvent(eventSaveDto);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"success",message), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse> getEvents(@PathVariable int id){
        EventGetDto event = eventService.getEvent(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"success",event), HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<StandardResponse> getAllEvents(){
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"success",eventService.getAllEvents()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteEvent(@PathVariable int id){
        String message = eventService.deleteEvent(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"success",message), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse>  updateEvent(@RequestBody Event event,@PathVariable int id ){
        String message = eventService.updateEvent(event,id);
        return new ResponseEntity<>(new StandardResponse(201,"updated",message ),HttpStatus.ACCEPTED);
    }
}


