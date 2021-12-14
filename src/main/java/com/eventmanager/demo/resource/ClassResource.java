package com.eventmanager.demo.resource;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ClassResource extends Resource {
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date updatedDate;
}
