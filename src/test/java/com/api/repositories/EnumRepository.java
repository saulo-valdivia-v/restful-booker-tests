package com.api.repositories;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.api.payloads.BookingResponse;
import com.api.requests.BookingApi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.restassured.response.ValidatableResponse;

/**
 * Singleton object to represent available Bookings for testing used by DataProviders.
 * Bookings are loaded from json file and sinchronized/released using the Booking API.
 */
public enum EnumRepository {
    INSTANCE();
    private List<BookingResponse> storage = new ArrayList<>();
    
    /**
     * Constructor that loads Bookings from JSON file and creates Bookings using Booking API
     */
    private EnumRepository() { 
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("bookings.json"));
            JsonArray array = jsonElement.getAsJsonArray();

            for (JsonElement element: array) {
                if (element.isJsonObject()) {
                    ValidatableResponse response = BookingApi.createBooking(element.toString());
                    String body = response.extract().body().asString();

                    BookingResponse rb = new Gson().fromJson(body, BookingResponse.class);
                    storage.add(rb);
                }
            }           
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Returns a unique Repository instance
     */
    public EnumRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Returns all Bookings available for Testing
     */
    public List<BookingResponse> getAll() {
        return storage;
    }

    /**
     * Filters Testing Bookings by Firstname
     */
    public List<BookingResponse> findByFirstname(String firstname) {
        List<BookingResponse> result = new ArrayList<>();

        for(BookingResponse b : storage) {
            if(b.getBooking().getFirstname().equals(firstname)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Filters Testing Bookings by Firstname and returns a list with the IDs
     */
    public List<Integer> findBookingIdsByFirstname(String firstname) {
        List<Integer> result = new ArrayList<>();

        for(BookingResponse b : storage) {
            if(b.getBooking().getFirstname().equals(firstname)) {
                result.add(b.getBookingid());
            }
        }

        return result;
    }

    /**
     * Filters Testing Bookings by Lastname
     */
    public List<BookingResponse> findByLastname(String lastname) {
        List<BookingResponse> result = new ArrayList<>();

        for(BookingResponse b : storage) {
            if(b.getBooking().getLastname().equals(lastname)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Cleans repository and Deletes Testing Booking objects
     */
    public void Clean() {
        for(BookingResponse b : storage) {
            ValidatableResponse response = BookingApi.getBookingById(String.valueOf(b.getBookingid()));

            if(response.extract().statusCode() == 200) {
                BookingApi.deleteBooking(String.valueOf(b.getBookingid()));
            }
        }

        storage.clear();
    }
}
