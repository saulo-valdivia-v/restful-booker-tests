package com.api.dataProviders;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.payloads.BookingResponse;
import com.api.repositories.EnumPayloadRepository;
import com.api.repositories.EnumRepository;

/**
 * DataProvider Object used by PartialUpdateBookingTests file containing all data used by the tests.
 */
public class PartialUpdateBookingDataProvider {
   private static final String originalFirstname = "James_Test";
   private static final String updatedFirstname = "Jodelle_Test";

     @DataProvider (name = "PartialUpdateSuccessProvider")
     public Object[][] PartialUpdateSuccessProviderMethod(){
        EnumRepository repo = EnumRepository.INSTANCE.getInstance();
        EnumPayloadRepository payloadRepo = EnumPayloadRepository.INSTANCE.getInstance();

        List<BookingResponse> originalData = repo.findByFirstname(originalFirstname);
        List<BookingResponse> updatedData = payloadRepo.findByFirstname(updatedFirstname);

        return new Object[][] {{originalData.get(0), updatedData.get(0)}};
     }

     @DataProvider (name = "PartialUpdateUnauthorizedProvider")
     public Object[][] PartialUpdateUnauthorizedProviderMethod(){
        EnumRepository repo = EnumRepository.INSTANCE.getInstance();
        EnumPayloadRepository payloadRepo = EnumPayloadRepository.INSTANCE.getInstance();

        List<BookingResponse> originalData = repo.findByFirstname(originalFirstname);
        List<BookingResponse> updatedData = payloadRepo.findByFirstname(updatedFirstname);

        return new Object[][] {{originalData.get(0), updatedData.get(0)}};
     }

     @DataProvider (name = "PartialUpdateInvalidBookingIdProvider")
     public Object[][] PartialUpdateInvalidBookingIdProviderMethod(){
        EnumRepository repo = EnumRepository.INSTANCE.getInstance();
        EnumPayloadRepository payloadRepo = EnumPayloadRepository.INSTANCE.getInstance();

        List<BookingResponse> originalData = repo.findByFirstname(originalFirstname);
        List<BookingResponse> updatedData = payloadRepo.findByFirstname(updatedFirstname);        

        return new Object[][] {{originalData.get(0), updatedData.get(0), "abcd"},
                               {originalData.get(0), updatedData.get(0), "9999"}};
     }
}
