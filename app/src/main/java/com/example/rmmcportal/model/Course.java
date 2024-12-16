package com.example.rmmcportal.model;

import java.util.ArrayList;
import java.util.List;



public class Course {

    public static final String COLLECTION_NAME = "courses";

    private String name;


    private List<Subject> subjects;

    public Course() {
        subjects = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return name;
    }
}
