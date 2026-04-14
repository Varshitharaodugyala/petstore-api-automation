package com.veeva.pages;

import com.veeva.models.Category;
import com.veeva.models.Pet;
import io.restassured.response.Response;
import java.util.Collections;
import static io.restassured.RestAssured.given;

public class PetPage extends BasePage {

    public Response createPet(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));

        return given().spec(requestSpec)
                .body(pet)
                .post("/pet");
    }

    public Response createPetWithCategory(long id, String name, String status, String catName) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setCategory(new Category(1L, catName));
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));

        return given().spec(requestSpec)
                .body(pet)
                .post("/pet");
    }

    public Response updatePet(String id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(Long.parseLong(id));
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));

        return given().spec(requestSpec)
                .body(pet)
                .put("/pet");
    }

    public Response getPetById(String petId) {
        return given().spec(requestSpec)
                .pathParam("petId", petId)
                .get("/pet/{petId}");
    }

    public Response deletePet(String petId) {
        return given().spec(requestSpec)
                .pathParam("petId", petId)
                .delete("/pet/{petId}");
    }

    public Response findPetsByStatus(String status) {
        return given().spec(requestSpec)
                .queryParam("status", status)
                .get("/pet/findByStatus");
    }
}