/*
      * PetClient class is responsible for handling all API requests related to Pet module.
      * This includes creating a pet, updating a pet, deleting a pet,
      * fetching pet details and searching pets based on status.
      * This class extends BaseClient so that it can reuse common request configurations

 */
package com.veeva.pages;
import com.veeva.models.Category;
import com.veeva.models.Pet;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PetPage extends BasePage {
    private static final Logger log = LogManager.getLogger(PetPage.class);
    /*
     * This method sends a POST request to create a new pet in the system.
     * Instead of manually building JSON string, we create a Pet Java object (POJO).
     * REST Assured automatically converts this object into JSON (Serialization).
     */
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
    /*
     * This method fetches pet details using petId.
     * petId is passed as path parameter which replaces {petId} in endpoint URL.
     */
    public Response getPetById(long petId) {
        log.info("Fetching pet by id: {}", petId);
        return given().spec(requestSpec).when().get("/pet/{petId}", petId);
    }
    /*
     * This method updates an existing pet.
     * PUT request is used for updating resources in REST APIs.
     * Entire pet object is sent again with modified values.
     */

    public Response updatePet(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/photo.jpg"));
        log.info("Updating pet - id: {}, new status: {}", id, status);
        return given().spec(requestSpec).body(pet).when().put("/pet");
    }
    /*
     * This method deletes a pet using petId.
     * DELETE request removes the resource from server.
     */
    public Response deletePet(long petId) {
        log.info("Deleting pet id: {}", petId);
        return given().spec(requestSpec).when().delete("/pet/{petId}", petId);
    }
    /*
     * This method searches pets based on their status using query parameter.
     * Example endpoint:
     */
    public Response findPetsByStatus(String status) {
        log.info("Finding pets by status: {}", status);
        return given().spec(requestSpec)
                .queryParam("status", status)
                .when().get("/pet/findByStatus");
    }
    //This is a helper method used for deserialization.
    //It converts API response JSON array into List<Pet> Java objects.
    public List<Pet> findPetsByStatusAsList(String status) {
        return findPetsByStatus(status)
                .then().extract().jsonPath().getList(".", Pet.class);
    }
    public Response getPetByInvalidId(String id) {
        return given()
                .spec(requestSpec)
                .pathParam("petId", id)
                .when()
                .get("/pet/{petId}");
    }

    public Response deletePetByInvalidId(String id) {
        return given()
                .spec(requestSpec)
                .pathParam("petId", id)
                .when()
                .delete("/pet/{petId}");
    }
}