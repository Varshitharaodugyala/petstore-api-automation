package com.veeva.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// POJO class representing Pet object used in PetStore APIs

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private String status;

    // Default constructor required for deserialization
    public Pet() {}

    // Getter and Setter for id
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    // Getter and Setter for category (nested object mapping)
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    // Getter and Setter for name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Getter and Setter for photoUrls (maps JSON array → Java List)
    public List<String> getPhotoUrls() { return photoUrls; }
    public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }

    // Getter and Setter for status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}