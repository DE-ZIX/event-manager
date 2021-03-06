package com.eventmanager.demo.collection;

import com.eventmanager.demo.ConsultList.ConsultList;
import com.eventmanager.demo.ConsultList.ConsultListMetadata;
import com.eventmanager.demo.author.Author;
import com.eventmanager.demo.pagination.Pagination;
import com.eventmanager.demo.resource.Resource;
import com.eventmanager.demo.resource.ResourceRepository;
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
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public @ResponseBody
    ConsultList<EventCollection> getEvents(@RequestParam(value="startDate", required=false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate, @RequestParam(value="endDate", required=false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate, @RequestParam(value = "resource", required = false) Integer resource, @RequestParam(value = "pagination", required=false) String pagination, @RequestParam(value = "notInResource", required = false) Integer notInResource)  throws JsonProcessingException {
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
        if(startDate == null && endDate == null && resource == null) {
            events = eventRepository.findAll(pageRequest).getContent();
            eventCount = eventRepository.count();
        } else {
            events = eventRepository.findBetweenDate(startDate, endDate, pageRequest);
            eventCount = eventRepository.countBetweenDate(startDate, endDate);
        }
        if (resource != null) {
            events = eventRepository.findByResourcesId(resource, pageRequest);
            eventCount = eventRepository.countByResourcesId(resource);
        }  else if (notInResource != null) {

            List<Integer> notThese = eventRepository.findIdByResourcesId(notInResource).stream().map(EventCollection::getId).collect(Collectors.toList());
            if (notThese.size() > 0) {
                events = eventRepository.findByIdNotIn(notThese, pageRequest);
                eventCount = eventRepository.countByIdNotIn(notThese);

            } else {
                events = eventRepository.findAll(pageRequest).getContent();
                eventCount = eventRepository.count();
            }
        }
        ConsultListMetadata consultListMetadata = new ConsultListMetadata(eventCount);
        return new ConsultList<>(events, consultListMetadata);
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EventCollection getEvent(@PathVariable int id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
    }

    @GetMapping(path="/{id}/resources")
    public @ResponseBody ConsultList<Resource> getResource(@PathVariable int id, @RequestParam(value = "pagination", required = false) String pagination, @RequestParam(value = "notInResource", required = false) Integer notInResource) throws JsonProcessingException {
        EventCollection event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
        long resourceCount = 0;
        Pagination<Resource> pag = new Pagination<>();
        Pagination<Resource> paginationObject = new Pagination<>();
        if (pagination != null && !pagination.isEmpty()) {
            pag = new ObjectMapper().readValue(pagination, new TypeReference<>() {
            });
        }
        if (pag != null) {
            paginationObject = pag;
        }
        paginationObject.init(Resource.class);

        PageRequest pageRequest = paginationObject.toPageRequest();
        resourceCount = resourceRepository.countByCollectionsId(event.getId());
        ConsultListMetadata consultListMetadata = new ConsultListMetadata(resourceCount);
        List<Resource> resources = resourceRepository.findByCollectionsId(event.getId(), pageRequest);
        return new ConsultList<>(resources, consultListMetadata);
    }

    @GetMapping(path="/{id}/{resourceId}")
    public @ResponseBody EventCollection addResource(@PathVariable int id, @PathVariable int resourceId) {
        EventCollection event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        event.resources.add(resource);
        eventRepository.save(event);
        resource.collections.add(event);
        resourceRepository.save(resource);
        return event;
    }

    @DeleteMapping(path="/{id}/{resourceId}/remove")
    public @ResponseBody String removeResource(@PathVariable int id, @PathVariable int resourceId) {
        EventCollection event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Event not found"));
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
        event.resources.remove(resource);
        eventRepository.save(event);
        resource.collections.remove(event);
        resourceRepository.save(resource);
        return "Resource removed";
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
            e.setImage(event.getImage());
            e.setImageFileName(event.getImageFileName());
            e.setImageFileType(event.getImageFileType());
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