package com.eventmanager.demo.collection;

import com.eventmanager.demo.ConsultList.ConsultList;
import com.eventmanager.demo.ConsultList.ConsultListMetadata;
import com.eventmanager.demo.author.Author;
import com.eventmanager.demo.pagination.Pagination;
import com.eventmanager.demo.resource.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public @ResponseBody
    ConsultList<EventCollection> getEvents(@RequestParam(value="startDate", required=false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate, @RequestParam(value="endDate", required=false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate, @RequestParam(value = "pagination", required=false) String pagination)  throws JsonProcessingException {
        List<EventCollection> events;
        long eventCount = 0;
        Pagination<EventCollection> pag = new Pagination<>();
        Pagination<EventCollection> paginationObject = new Pagination<>();
        if(pagination != null && !pagination.isEmpty()) {
            pag = new ObjectMapper().readValue(pagination, new TypeReference<>() {});
        }
        if (pag != null) {
            paginationObject = pag;
        }
        paginationObject.init(EventCollection.class);
        PageRequest pageRequest = paginationObject.toPageRequest();
        if(startDate == null && endDate == null) {
            events = eventRepository.findAll(pageRequest).getContent();
            eventCount = eventRepository.count();
        } else {
            events = eventRepository.findBetweenDate(startDate, endDate, pageRequest);
            eventCount = eventRepository.countBetweenDate(startDate, endDate);
        }
        ConsultListMetadata consultListMetadata = new ConsultListMetadata(eventCount);
        return new ConsultList<>(events, consultListMetadata);
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