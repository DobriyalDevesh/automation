package com.company.automation.api.setup;

import io.restassured.internal.support.Prettifier;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class BaseController {

    /**
     * trims response body to 1000 characters to avoid log overload
     */
    public void logResponse(Response response) {
       
        String responseBody;
        if (response.asString().length() > 1000) {
            responseBody = new Prettifier().prettify(response.asString(), Parser.JSON).substring(0, 1000) +
                    "[...RESPONSE BODY CAPPED AT 1000 CHARACTERS FOR LOGGING PURPOSES...]";
        } else {
            responseBody = response.prettyPrint();
        }
    }
}
