package com.eventmanager.demo.collection;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EventCollection extends Collection {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date startDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date endDate;

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
