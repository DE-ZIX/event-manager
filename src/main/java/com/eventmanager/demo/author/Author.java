package com.eventmanager.demo.author;

import com.eventmanager.demo.resource.Resource;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Author {

    public Author() {

    }
    public Author(Author a) {
        this.id = a.id;
        this.name = a.name;
        this.surname = a.surname;
        this.email = a.email;
        this.affiliation = a.affiliation;
        this.orcid = a.orcid;
        this.resources = a.resources;
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    public String id;

    public String email;

    @Column(length = 64)
    public String name;

    @Column(length = 64)
    public String surname;

    @Column(length = 256)
    public String affiliation;

    @Pattern(regexp = "XXXX-XXXX-XXXX-XXXX")
    public String orcid;

    @ManyToMany
    public List<Resource> resources;
}
