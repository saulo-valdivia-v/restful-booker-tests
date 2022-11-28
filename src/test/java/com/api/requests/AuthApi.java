package com.api.requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Represents AuthAPI and its related endpoints
 */
public class AuthApi extends BaseApi {
    private static final String apiUrl = baseUrl + "auth";
    private static final String payload = "{\"username\":\"admin\",\"password\":\"password123\"}";

    /**
     * Returns a token required by Booking endpoints such Update and Delete
     */
    public static Response createToken() {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(apiUrl);
    }
}
