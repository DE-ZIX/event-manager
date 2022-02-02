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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path = "/classes")
public class ClassController {
    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public @ResponseBody
    ConsultList<ClassCollection> getClasses(@RequestParam(value = "pagination", required = false) String pagination) throws JsonProcessingException {
        List<ClassCollection> classes;
        Pagination<ClassCollection> pag = new Pagination<>();
        Pagination<ClassCollection> paginationObject = new Pagination<>();
        if (pagination != null && !pagination.isEmpty()) {
            pag = new ObjectMapper().readValue(pagination, new TypeReference<>() {
            });
        }
        if (pag != null) {
            paginationObject = pag;
        }
        paginationObject.init(ClassCollection.class);
        PageRequest pageRequest = paginationObject.toPageRequest();
        classes = classRepository.findAll(pageRequest).getContent();
        long count = classRepository.count();
        ConsultListMetadata consultListMetadata = new ConsultListMetadata(count);
        return new ConsultList<>(classes, consultListMetadata);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    ClassCollection getClass(@PathVariable int id) {
        return classRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Class not found"));
    }

    @GetMapping(path = "/{id}/resources")
    public @ResponseBody
    Iterable<Resource> getResource(@PathVariable int id) {
        ClassCollection classAux = classRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Class not found"));
        return classAux.getResources();
    }

    @PostMapping
    public @ResponseBody
    ClassCollection createClass(@RequestBody ClassCollection classIn) {
        return classRepository.save(classIn);
    }

    @PutMapping
    public @ResponseBody
    ClassCollection updateClass(@RequestBody ClassCollection classIn) {
        return classRepository.findById(classIn.id).map(c -> {
            c.setTitle(classIn.getTitle());
            c.setDescription(classIn.getDescription());
            c.setResources(classIn.getResources());
            classRepository.save(c);
            return c;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Class not found"));
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String deleteClass(@PathVariable int id) {
        classRepository.deleteById(id);
        return "Class deleted";
    }
}
