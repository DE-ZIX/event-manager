package com.eventmanager.demo.resource;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EventResource extends Resource {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date startDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date endDate;

}
