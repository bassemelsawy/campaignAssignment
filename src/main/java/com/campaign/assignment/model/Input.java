package com.campaign.assignment.model;

import lombok.Data;

import java.util.*;
import java.io.Serializable;

@Data
public class Input implements Serializable {
    private String id;
    private List<String> segments;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<String> getSegments() {
        return this.segments;
    }
    public void setSegments(List<String> segments) {
        this.segments = segments;
    }
}
