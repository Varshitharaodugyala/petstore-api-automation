package com.veeva.clients;

import com.veeva.models.Category;
import com.veeva.models.Pet;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PetClient extends BaseClient {

    private static final Logger log = LogManager.getLogger(PetClient.class);

    public Response createPet(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));

        log.info("Creating pet - id: {}, name: {}, status: {}", id, name, status);
        return given().spec(requestSpec).body(pet).when().post("/pet");
    }

    public Response createPetWithCategory(long id, String name, String status, String categoryName) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));
        pet.setCategory(new Category(1L, categoryName));

        log.info("Creating pet with category - name: {}, category: {}", name, categoryName);
        return given().spec(requestSpec).body(pet).when().post("/pet");
    }

    public Response getPetById(long petId) {
        log.info("Fetching pet by id: {}", petId);
        return given().spec(requestSpec).when().get("/pet/{petId}", petId);
    }

    public Response updatePet(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));

        log.info("Updating pet - id: {}, new status: {}", id, status);
        return given().spec(requestSpec).body(pet).when().put("/pet");
    }

    public Response deletePet(long petId) {
        log.info("Deleting pet id: {}", petId);
        return given().spec(requestSpec).when().delete("/pet/{petId}", petId);
    }

    public Response findPetsByStatus(String status) {
        log.info("Finding pets by status: {}", status);
        return given().spec(requestSpec)
                .queryParam("status", status)
                .when().get("/pet/findByStatus");
    }

    public List<Pet> findPetsByStatusAsList(String status) {
        return findPetsByStatus(status)
                .then().extract().jsonPath().getList(".", Pet.class);
    }
}