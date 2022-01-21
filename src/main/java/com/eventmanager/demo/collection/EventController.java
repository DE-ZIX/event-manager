package com.eventmanager.demo.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public @ResponseBody
    Iterable<EventCollection> getEvents() {
        return eventRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EventCollection getEvent(@PathVariable int id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
    }

    @PostMapping
    public @ResponseBody EventCollection createEvent(@RequestBody EventCollection event) {
        return eventRepository.save(event);
    }

    @PutMapping
    public @ResponseBody EventCollection updateEvent(@RequestBody EventCollection event) {
        return eventRepository.findById(event.id).map(e -> {
            e.setTitle(event.getTitle());
            e.setDescription(event.getDescription());
            e.setStartDate(event.getStartDate());
            e.setEndDate(event.getEndDate());
            return eventRepository.save(e);
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteEvent(@PathVariable int id) {
        eventRepository.deleteById(id);
        return "Event deleted";
    }
}