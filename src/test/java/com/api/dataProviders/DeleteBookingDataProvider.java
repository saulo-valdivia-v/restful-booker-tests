package com.api.dataProviders;

import java.util.List;

import org.testng.annotations.DataProvider;
import com.api.repositories.EnumRepository;

/**
 * DataProvider Object used by DeleteBookingTests file containing all data used by the tests.
 */
public class DeleteBookingDataProvider {
   private static final String firstname = "James_Test";

    @DataProvider (name = "DeleteBookingSuccess")
     public Object[][] deleteBookingDataProviderMethod(){
      EnumRepository repo = EnumRepository.INSTANCE.getInstance();
      List<Integer> data = repo.findBookingIdsByFirstname(firstname);

      return new Object[][] {{Integer.toString(data.get(0))}};
     } 

     @DataProvider (name = "InvalidBookingIdDataProvider")
     public Object[][] InvalidBookingIdDataProviderMethod(){
        return new Object[][] {{"abcd"}, {"9999"}};
     } 
}
