package com.eventmanager.demo.collection;

import com.eventmanager.demo.author.Author;
import com.eventmanager.demo.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/classes")
public class ClassController {
    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public @ResponseBody
    Iterable<ClassCollection> getClasses() {
        return classRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody ClassCollection getClass(@PathVariable int id) {
        return classRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Class not found"));
    }

    @GetMapping(path="/{id}/resources")
    public @ResponseBody Iterable<Resource> getResource(@PathVariable int id) {
        ClassCollection classAux = classRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Class not found"));
        return classAux.getResources();
    }

    @PostMapping
    public @ResponseBody ClassCollection createClass(@RequestBody ClassCollection classIn) {
        return classRepository.save(classIn);
    }

    @PutMapping
    public @ResponseBody ClassCollection updateClass(@RequestBody ClassCollection classIn) {
        return classRepository.findById(classIn.id).map(c -> {
            c.setTitle(classIn.getTitle());
            c.setDescription(classIn.getDescription());
            c.setResources(classIn.getResources());
            classRepository.save(c);
            return c;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Class not found"));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteClass(@PathVariable int id) {
        classRepository.deleteById(id);
        return "Class deleted";
    }
}
