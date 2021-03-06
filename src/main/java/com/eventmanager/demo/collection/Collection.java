package com.eventmanager.demo.collection;

import com.eventmanager.demo.resource.Resource;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Collection {

    public Collection() {

    }

    public Collection(Collection c) {
        this.id = c.id;
        this.title = c.title;
        this.image = c.image;
        this.description = c.description;
        this.resources = c.resources;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    @Lob
    public byte[] image;

    public String imageFileName;

    public String imageFileType;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    public List<Resource> resources;

    @Size(max=1024)
    public String title;

    @Size(max=4096)
    public String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFileName() { return imageFileName; }

    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }

    public String getImageFileType() { return imageFileType; }

    public void setImageFileType(String imageFileType) { this.imageFileType = imageFileType; }

}
