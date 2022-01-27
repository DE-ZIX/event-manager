package com.eventmanager.demo.collection;

import com.eventmanager.demo.author.Author;
import com.eventmanager.demo.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public @ResponseBody
    Iterable<EventCollection> getEvents(@RequestParam(value="startDate", required=false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate, @RequestParam(value="endDate", required=false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
        if(startDate == null && endDate == null) {
            return eventRepository.findAll();
        /*} else if(startDate != null && endDate == null) {
            return eventRepository.findByStartDateGreaterThanEqual(startDate);
        } else if(startDate == null && endDate != null) {
            return eventRepository.findByEndDateLessThanEqual(endDate);*/
        } else {
            return eventRepository.findBetweenDate(startDate, endDate);
        }
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EventCollection getEvent(@PathVariable int id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
    }

    @GetMapping(path="/{id}/resources")
    public @ResponseBody Iterable<Resource> getResource(@PathVariable int id) {
        EventCollection event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
        return event.getResources();
    }

    @PostMapping
    public @ResponseBody EventCollection createEvent(@RequestBody EventCollection event) {
        event.zeroHours();
        return eventRepository.save(event);
    }

    @PutMapping
    public @ResponseBody EventCollection updateEvent(@RequestBody EventCollection event) {
        event.zeroHours();
        return eventRepository.findById(event.id).map(e -> {
            e.setTitle(event.getTitle());
            e.setDescription(event.getDescription());
            e.setStartDate(event.getStartDate());
            e.setEndDate(event.getEndDate());
            e.setResources(event.getResources());
            return eventRepository.save(e);
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteEvent(@PathVariable int id) {
        eventRepository.deleteById(id);
        return "Event deleted";
    }
}