/* BaseClient is a shared setting  file for all the API calls.
*Instead of repeating the same configuration in every client class,define it once here
* and inherited by  PetClient, StoreClient, and UserClient
*/

package com.veeva.clients;
import com.veeva.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {
    //RequestSpecBuilder is a REST Assured class that lets us build a reusable API configuration step by step
    protected static final RequestSpecification requestSpec =
            new RequestSpecBuilder()
                    // Base URL loaded from config.properties
                    .setBaseUri(ConfigManager.getBaseUrl())
                    //Tells the API — *"I am sending JSON data"*. This automatically adds the header:
                    //Content-Type: application/json
                    .setContentType(ContentType.JSON)
                    // Every time any API call is made, this automatically prints the full request  and response to  console/logs:
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    //Finalises and creates the RequestSpecification object from everything configured above.
                    .build();
}
