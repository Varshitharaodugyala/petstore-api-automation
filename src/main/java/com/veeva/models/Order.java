package com.veeva.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// POJO class representing Order object for Petstore Store APIs
// Used for serialization (Java → JSON) when sending request
// and deserialization (JSON → Java) when reading response
// ignoreUnknown = true prevents failure if API response has extra fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    // Unique order id
    private long id;

    // ID of the pet being ordered
    private long petId;

    // Quantity of pets in the order
    private int quantity;

    // Shipping date of the order
    private String shipDate;

    // Order status (placed, approved, delivered)
    private String status;

    // Indicates if order is fully processed
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

    // Getter and Setter for status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getter and Setter for complete
    public boolean isComplete() { return complete; }
    public void setComplete(boolean complete) { this.complete = complete; }
}