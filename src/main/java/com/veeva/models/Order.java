package com.veeva.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// POJO class representing Order object for PetStore Store APIs
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;

    // Default constructor required for JSON → Java conversion
    public Order() {}

    // Getter and Setter for id
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    // Getter and Setter for petId
    public long getPetId() { return petId; }
    public void setPetId(long petId) { this.petId = petId; }

    // Getter and Setter for quantity
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Getter and Setter for shipDate
    public String getShipDate() { return shipDate; }
    public void setShipDate(String shipDate) { this.shipDate = shipDate; }

    // Getter and Setter for status,complete
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isComplete() { return complete; }
    public void setComplete(boolean complete) { this.complete = complete; }
}