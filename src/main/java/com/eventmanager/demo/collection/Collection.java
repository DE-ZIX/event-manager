package com.eventmanager.demo.collection;

import com.eventmanager.demo.resource.Resource;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Collection {

    public Collection() {

    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    @Lob
    public byte[] image;

    @ManyToMany
    public List<Resource> resources;

    @Size(max=1024)
    public String title;

    @Size(max=4096)
    public String description;

}
