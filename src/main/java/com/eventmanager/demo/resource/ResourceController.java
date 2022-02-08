package com.eventmanager.demo.resource;

import com.eventmanager.demo.ConsultList.ConsultList;
import com.eventmanager.demo.ConsultList.ConsultListMetadata;
import com.eventmanager.demo.collection.ClassCollection;
import com.eventmanager.demo.collection.Collection;
import com.eventmanager.demo.pagination.Pagination;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path = "/resources")
public class ResourceController {
    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public @ResponseBody
    ConsultList<Resource> getAllResources(@RequestParam(value = "author", required = false) Integer author, @RequestParam(value = "collection", required = false) Integer collection, @RequestParam(value = "pagination", required = false) String pagination, @RequestParam(value = "notInCollection", required = false) Integer notInCollection) throws JsonProcessingException {
        List<Resource> resources = new ArrayList<>();
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
        if (author != null && collection != null) {
            resources = resourceRepository.findResourceByAuthorsIdAndCollectionsId(author, collection, pageRequest);
            resourceCount = resourceRepository.countByAuthorsIdAndCollectionsId(author, collection);
        } else if (author != null) {
            resources = resourceRepository.findResourceByAuthorsId(author, pageRequest);
            resourceCount = resourceRepository.countByAuthorsId(author);
        } else if (collection != null) {
            resources = resourceRepository.findResourceByCollectionsId(collection, pageRequest);
            resourceCount = resourceRepository.countByCollectionsId(collection);
        } else if(notInCollection != null){
            resources = resourceRepository.findByCollectionsIdNot(notInCollection, pageRequest);
            resourceCount = resourceRepository.countByCollectionsIdNot(notInCollection);
        } else {
            resources = resourceRepository.findAll(pageRequest).getContent();
            resourceCount = resourceRepository.count();
        }
        ConsultListMetadata consultListMetadata = new ConsultListMetadata(resourceCount);
        return new ConsultList<>(resources, consultListMetadata);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Resource getResource(@PathVariable int id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Resource not found"));
    }

    @PostMapping
    public @ResponseBody
    Resource createResource(@RequestBody Resource resource) {
        return resourceRepository.save(resource);
    }

    @PutMapping
    public @ResponseBody
    Resource updateResource(@RequestBody Resource resource) {
        return resourceRepository.findById(resource.id).map(r -> {
            r.setTitle(resource.getTitle());
            r.setDescription(resource.getDescription());
            r.setLink(resource.getLink());
            r.setImage(resource.getImage());
            r.setImageFileName(resource.getImageFileName());
            r.setImageFileType(resource.getImageFileType());
            r.setKeywords(resource.getKeywords());
            r.setResponsibleAuthor(resource.getResponsibleAuthor());
            return resourceRepository.save(r);
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Collection not found"));
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String deleteResource(@PathVariable int id) {
        resourceRepository.deleteById(id);
        return "Resource deleted";
    }
}
