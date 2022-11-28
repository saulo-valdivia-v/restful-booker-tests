package com.api.dataProviders;

import java.util.List;

import org.testng.annotations.DataProvider;
import com.api.repositories.EnumRepository;

/**
 * DataProvider Object used by GetBookingIdsTests file containing all data used by the tests.
 */
public class GetBookingIdsDataProvider {
   private static final String singleFirstname = "James_Test";
   private static final String multipleFirstname = "Harry_Test";
   private static final String multipleLastname = "Mason_Test";

   @DataProvider (name = "oneParameterProvider")
   public Object[][] oneParameterProviderMethod(){
      EnumRepository repo = EnumRepository.INSTANCE.getInstance();
      List<Integer> data = repo.findBookingIdsByFirstname(singleFirstname);
      
      return new Object[][] {{singleFirstname, data}};
   }

   @DataProvider (name = "oneInvalidParameterProvider")
   public Object[][] oneInvalidParameterProviderMethod(){
      EnumRepository repo = EnumRepository.INSTANCE.getInstance();
      List<Integer> data = repo.findBookingIdsByFirstname(singleFirstname);

      return new Object[][] {{singleFirstname, data}};
   }     

   @DataProvider (name = "TwoParametersProvider")
   public Object[][] TwoParametersProviderMethod(){
      EnumRepository repo = EnumRepository.INSTANCE.getInstance();
      List<Integer> data = repo.findBookingIdsByFirstname(multipleFirstname);

      return new Object[][] {{multipleFirstname, multipleLastname, data}};
   }
}