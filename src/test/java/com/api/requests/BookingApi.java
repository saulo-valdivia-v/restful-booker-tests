package com.api.requests;

import com.api.payloads.BookingResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * Represents Booking API and its related endpoints
 */
public class BookingApi extends BaseApi {
    private static final String apiUrl = baseUrl + "booking/";

    /**
     * Creates a new booking
     */
    public static ValidatableResponse createBooking(String payload) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(apiUrl)
                .then();
    }

    /**
     * Returns a list with all Booking IDs
     */
    public static ValidatableResponse getBooking() {
        return RestAssured.given().when().get(apiUrl).then();
    }

    /**
     * Returns a list of Booking IDs filtered by one parameter
     */
    public static ValidatableResponse getBooking(String paramName, String paramValue) {
        return RestAssured.given()
                .queryParam(paramName, paramValue)
                .when()
                .get(apiUrl)
                .then();
    }

    /**
     * Returns a list of Booking IDs filtered by two parameter
     */
    public static ValidatableResponse getBooking(String paramName1, String paramValue1, String paramName2, String paramValue2) {
        return RestAssured.given()
                .queryParam(paramName1, paramValue1)
                .queryParam(paramName2, paramValue2)
                .when()
                .get(apiUrl)
                .then();
    }

    /**
     * Returns a Booking for the given BookingId
     */
    public static ValidatableResponse getBookingById(String bookingId) {
        return RestAssured.given()
                        .when()
                        .pathParam("bookingId", bookingId)
                        .get(apiUrl + "{bookingId}")
                        .then();
    }

    /**
     * Deletes a Booking with the provided BookingId
     */
    public static ValidatableResponse deleteBooking(String bookingId) {
        Response auth = AuthApi.createToken();
        String token = auth.jsonPath().get("token");

        return deleteBooking(bookingId, token);
    }

    /**
     * Deletes a Booking with the provided BookingId and token
     */
    public static ValidatableResponse deleteBooking(String bookingId, String token) {
        return RestAssured.given()
                .header("Cookie", "token=" + token)
                .when()
                .pathParam("bookingId", bookingId)
                .delete(apiUrl + "{bookingId}")
                .then();
    }

    /**
     * Updates a current booking with a partial payload
     */
    public static ValidatableResponse partialUpdateBooking(BookingResponse original, BookingResponse updated) {
        Response auth = AuthApi.createToken();
        String token = auth.jsonPath().get("token");

        return partialUpdateBooking(original, updated, token);
    }

    /**
     * Updates related BookingId booking with a partial payload
     */
    public static ValidatableResponse partialUpdateBooking(BookingResponse updated, String bookingId) {
        Response auth = AuthApi.createToken();
        String token = auth.jsonPath().get("token");

        return partialUpdateBooking(updated, token, bookingId);
    }

    /**
     * Updates current Booking with a partial payload and using provided token
     */
    public static ValidatableResponse partialUpdateBooking(BookingResponse original, BookingResponse updated, String token) {
        return partialUpdateBooking(updated, token, Integer.toString(original.getBookingid()));
    }

    /**
     * Updates related BookingId Booking with a partial payload and using provided token
     */
    public static ValidatableResponse partialUpdateBooking(BookingResponse updated, String token, String bookingId) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(updated.getPayload())
                .when()
                .pathParam("bookingId", bookingId)
                .patch(apiUrl + "{bookingId}")
                .then();
    }
}
