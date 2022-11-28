package com.api.repositories;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.api.payloads.BookingResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Singleton object to represent Payloads and Expected Booking updates used by DataProviders.
 */
public enum EnumPayloadRepository {
    INSTANCE();
    private List<BookingResponse> storage = new ArrayList<>();
    
    /**
     * Constructor that loads Payload and Expected Bookings from JSON file
     */
    private EnumPayloadRepository() { 
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("updateBookings.json"));
            JsonArray array = jsonElement.getAsJsonArray();

            for (JsonElement element: array) {
                if (element.isJsonObject()) {
                    BookingResponse rb = new Gson().fromJson(element.toString(), BookingResponse.class);
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
    public EnumPayloadRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Returns all Bookings and Payloads available for Testing
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
     * Cleans repository
     */
    public void Clean() {
        storage.clear();
    }
}
