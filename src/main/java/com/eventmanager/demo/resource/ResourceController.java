package com.eventmanager.demo.resource;

import com.eventmanager.demo.ConsultList.ConsultList;
import com.eventmanager.demo.ConsultList.ConsultListMetadata;
import com.eventmanager.demo.collection.Collection;
import com.eventmanager.demo.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/resources")
public class ResourceController {
    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public @ResponseBody ConsultList<Resource> getAllResources(@RequestParam(value = "author", required=false) Integer author, @RequestParam(value = "collection", required=false) Integer collection, @RequestParam(value = "pagination", required=false) Pagination pagination) {
        List<Resource> resources = new ArrayList<>();
        Pagination paginationObject = new Pagination();
        if (pagination != null) {
            paginationObject = pagination;
        }
        paginationObject.init();

        PageRequest pageRequest;
        if (paginationObject.sortBy != null && !paginationObject.sortBy.isEmpty()) {
            pageRequest = PageRequest.of(paginationObject.page, paginationObject.limit, Sort.by(paginationObject.descending ? Sort.Direction.DESC : Sort.Direction.ASC, paginationObject.sortBy));
        } else {
            pageRequest = PageRequest.of(paginationObject.page, paginationObject.limit);
        }
        long resourceCount = 0;
        if (author != null && collection != null) {
            resources = resourceRepository.findResourceByAuthorsIdAndCollectionsId(author, collection, pageRequest);
            resourceCount = resourceRepository.countByAuthorsIdAndCollectionsId(author, collection);
        }
        else if (author != null) {
            resources = resourceRepository.findResourceByAuthorsId(author, pageRequest);
            resourceCount = resourceRepository.countByAuthorsId(author);
        }
        else if (collection != null) {
            resources = resourceRepository.findResourceByCollectionsId(collection, pageRequest);
            resourceCount = resourceRepository.countByCollectionsId(collection);
        }
        else {
            resources = resourceRepository.findAll(pageRequest).getContent();
            resourceCount = resourceRepository.count();
        }
        ConsultListMetadata consultListMetadata = new ConsultListMetadata(resourceCount);
        return new ConsultList<>(resources, consultListMetadata);
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
