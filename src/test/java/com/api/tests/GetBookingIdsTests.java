package com.api.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.dataProviders.GetBookingIdsDataProvider;
import com.api.repositories.EnumRepository;
import com.api.requests.BookingApi;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;
import java.util.List;

/**
 * Includes API tests for GetBookingIds endpoint
 */
public class GetBookingIdsTests 
{
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;
    
    /**
     * Test Setup to initialize data repositories
     */
    @BeforeTest
    public void setup() {        
        EnumRepository.INSTANCE.getInstance();

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/GetBookingIdsTestsResults.html");
        htmlReporter.config().setDocumentTitle("API Automation Report");
        htmlReporter.config().setReportName("GetBookingIds endpoint Report");
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester Name", "Saulo Valdivia");        
    }

    /**
     * Runs after tests to clean data repositories and generate Report
     */
    @AfterTest
    public void after() {
        EnumRepository.INSTANCE.getInstance().Clean();
        extent.flush();
    }

    /**
     * Populates the report after each test is run
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "TEST CASE PASSED IS " + result.getName());
        }        
    }

    /**
     * GetBookingIds endpoint returns a list of all BookingIds when invoked with no parameters
     */
    @Test
    public void shouldReturnNonEmptyListWhenRequestDoesNotContainParameters() {
        test = extent.createTest("shouldReturnNonEmptyListWhenRequestDoesNotContainParameters");

        ValidatableResponse response = BookingApi.getBooking();

        response.assertThat().statusCode(200);
        response.assertThat().body("", not(empty()));
    }

    /**
     * GetBookingIds returns a list of BookingIds filtered when invoked with one valid parameter
     */
    @Test (dataProvider = "oneParameterProvider", dataProviderClass = GetBookingIdsDataProvider.class)
    public void shouldReturnFilteredListWhenOneValidParameterIsSent(String owner, List<Integer> testData) {
        test = extent.createTest("shouldReturnFilteredListWhenOneValidParameterIsSent");

        ValidatableResponse response = BookingApi.getBooking("firstname", owner);

        response.assertThat().statusCode(200);
        response.assertThat().body("", hasSize(testData.size()));
        response.assertThat().body("bookingid", hasItems(testData.toArray()));
    }

    /**
     * GetBookingIds returns a list of all BookingIds when invoked with one invalid parameter
     */
    @Test (dataProvider = "oneInvalidParameterProvider", dataProviderClass = GetBookingIdsDataProvider.class)
    public void shouldReturnAllItemsWhenOneInvalidParameterIsSent(String owner, List<Integer> testData) {
        test = extent.createTest("shouldReturnAllItemsWhenOneInvalidParameterIsSent");

        ValidatableResponse response = BookingApi.getBooking("RandomFirstName", owner);

        response.assertThat().statusCode(200);
        response.assertThat().body("", not(empty()));
        response.assertThat().body("", hasSize(greaterThan(testData.size())));
    }

    /**
     * GetBookingIds returns an empty list when invoked with one parameter for non existing value
     */
    @Test
    public void shouldReturnEmptyListWhenOneInvalidParameterValueIsSent() {
        test = extent.createTest("shouldReturnEmptyListWhenOneInvalidParameterValueIsSent");

        ValidatableResponse response = BookingApi.getBooking("firstname", "abcd");

        response.assertThat().statusCode(200);
        response.assertThat().body("", empty());
    }

    /**
     * GetBookingIds returns a list of BookingIds filtered when invoked with two valid parameters
     */
    @Test (dataProvider = "TwoParametersProvider", dataProviderClass = GetBookingIdsDataProvider.class)
    public void shouldReturnFilteredListWhenTwoValidParameterAreSent(String firstname, String lastname, List<Integer> testData) {
        test = extent.createTest("shouldReturnFilteredListWhenTwoValidParameterAreSent");

        ValidatableResponse response = BookingApi.getBooking("firstname", firstname, "lastname", lastname);

        response.assertThat().statusCode(200);
        response.assertThat().body("", hasSize(testData.size()));
        response.assertThat().body("bookingid", hasItems(testData.toArray()));
    }

    /**
     * GetBookingIds returns a list of BookingIds when invoked with one valid parameter and another invalid parameter.
     * It filters by valid parameter and ignores invalid parameter.
     */
    @Test (dataProvider = "TwoParametersProvider", dataProviderClass = GetBookingIdsDataProvider.class)
    public void shouldReturnFilteredListWhenOneValidParameterAndOneInvalidParameterAreSent(String firstname, String lastname, List<Integer> testData) {
        test = extent.createTest("shouldReturnFilteredListWhenOneValidParameterAndOneInvalidParameterAreSent");

        ValidatableResponse response = BookingApi.getBooking("firstname", firstname, "RandomLastName", lastname);

        response.assertThat().statusCode(200);
        response.assertThat().body("", hasSize(testData.size()));
        response.assertThat().body("bookingid", hasItems(testData.toArray()));
    }

    /**
     * GetBookingIds returns a list of all BookingIds when invoked with two invalid parameters
     */
    @Test (dataProvider = "TwoParametersProvider", dataProviderClass = GetBookingIdsDataProvider.class)
    public void shouldReturnAllItemsWhenTwoInvalidParameterIsSent(String firstname, String lastname, List<Integer> testData) {
        test = extent.createTest("shouldReturnAllItemsWhenTwoInvalidParameterIsSent");

        ValidatableResponse response = BookingApi.getBooking("RandomFirstName", firstname, "RandomLastName", lastname);

        response.assertThat().statusCode(200);
        response.assertThat().body("", not(empty()));
        response.assertThat().body("", hasSize(greaterThan(testData.size())));
    }

    /**
     * GetBookingIds returns an empty list of BookingIds when invoked with two non existing parameter values
     */
    @Test
    public void shouldReturnEmptyListWhenTwoInvalidParameterValuesAreSent() {
        test = extent.createTest("shouldReturnEmptyListWhenTwoInvalidParameterValuesAreSent");

        ValidatableResponse response = BookingApi.getBooking("firstname", "RandomFirstName", "lastname", "RandomLastName");

        response.assertThat().statusCode(200);
        response.assertThat().body("", empty());
    }
}
