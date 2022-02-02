package com.eventmanager.demo.ConsultList;

import java.util.ArrayList;
import java.util.List;

public class ConsultList<E> {
    public ConsultList(){

    }
    public ConsultList(List<E> items, ConsultListMetadata metadata){
        this.items = items;
        this.metadata = metadata;
    }
    public List<E> items;
    public ConsultListMetadata metadata;
}
