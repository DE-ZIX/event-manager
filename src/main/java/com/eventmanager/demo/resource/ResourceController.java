package com.eventmanager.demo.resource;

import com.eventmanager.demo.collection.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/resources")
public class ResourceController {
    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public @ResponseBody Iterable<Resource> getAllResources(@RequestParam(value = "author", required=false) Integer author, @RequestParam(value = "collection", required=false) Integer collection) {
        if(author != null && collection != null) {
            return resourceRepository.findResourceByAuthorsIdAndCollectionsId(author, collection);
        }
        if (author != null) {
            return resourceRepository.findResourceByAuthorsId(author);
        }
        if (collection != null) {
            return resourceRepository.findResourceByCollectionsId(collection);
        }
        return resourceRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Resource getResource(@PathVariable int id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }

    @PostMapping
    public @ResponseBody Resource createResource(@RequestBody Resource resource) {
        return resourceRepository.save(resource);
    }

    @PutMapping
    public @ResponseBody Resource updateResource(@RequestBody Resource resource) {
        return resourceRepository.findById(resource.id).map(r -> {
            r.setTitle(resource.getTitle());
            r.setDescription(resource.getDescription());
            r.setLink(resource.getLink());
            r.setKeywords(resource.getKeywords());
            r.setResponsibleAuthor(resource.getResponsibleAuthor());
            return resourceRepository.save(r);
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Collection not found"));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteResource(@PathVariable int id) {
        resourceRepository.deleteById(id);
        return "Resource deleted";
    }
}
