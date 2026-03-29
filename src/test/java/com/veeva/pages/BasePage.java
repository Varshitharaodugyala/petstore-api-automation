/* BaseClient is a shared setting  file for all the API calls.
*Instead of repeating the same configuration in every client class,define it once here
* and inherited by  PetClient, StoreClient, and UserClient
*/

package com.veeva.pages;
import com.veeva.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BasePage {
    //RequestSpecBuilder is a REST Assured class that lets us build a reusable API configuration step by step
    protected static final RequestSpecification requestSpec =
            new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getBaseUrl())

                    .setContentType(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();

}
