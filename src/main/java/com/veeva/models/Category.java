package com.veeva.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// This is a POJO (Model) class representing Category object in Pet API.
// It is used for serialization (Java → JSON) and deserialization (JSON → Java).
// ignoreUnknown = true prevents errors if API sends extra fields.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    // Category id field
    private long id;

    // Category name field
    private String name;

    // Default constructor required for deserialization
    public Category() {}

    // Parameterized constructor to easily create Category object
    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for id
    public long getId() { return id; }

    // Setter for id
    public void setId(long id) { this.id = id; }

    // Getter for name
    public String getName() { return name; }

    // Setter for name
    public void setName(String name) { this.name = name; }
}