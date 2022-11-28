package com.api.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.dataProviders.PartialUpdateBookingDataProvider;
import com.api.payloads.Booking;
import com.api.payloads.BookingResponse;
import com.api.repositories.EnumPayloadRepository;
import com.api.repositories.EnumRepository;
import com.api.requests.BookingApi;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.gson.Gson;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Includes API tests for PartialUpdate endpoint
 */
public class PartialUpdateBookingTests 
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
        EnumPayloadRepository.INSTANCE.getInstance();

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/PartialUpdateBookingTestsResults.html");
        htmlReporter.config().setDocumentTitle("API Automation Report");
        htmlReporter.config().setReportName("PartialUpdateBooking endpoint Report");
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
        EnumPayloadRepository.INSTANCE.getInstance().Clean();
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
     * PartialUpdate endpoint updates a booking when using valid parameters
     */
    @Test (dataProvider = "PartialUpdateSuccessProvider", dataProviderClass = PartialUpdateBookingDataProvider.class)
    public void shouldReturnNonEmptyListWhenRequestDoesNotContainParameters(BookingResponse original, BookingResponse updated) {
        test = extent.createTest("shouldReturnNonEmptyListWhenRequestDoesNotContainParameters");

        ValidatableResponse response = BookingApi.partialUpdateBooking(original, updated);

        // Assert that request was performed Successfully
        response.assertThat().statusCode(200);

        // Assert that request returns an updated Booking
        Booking rb = new Gson().fromJson(response.extract().body().asString(), Booking.class);
        assertThat(updated.getBooking(), equalTo(rb));
    }

    /**
     * PartialUpdate endpoint displays Forbidden message when valid parameters are sent and token is empty
     */
    @Test (dataProvider = "PartialUpdateUnauthorizedProvider", dataProviderClass = PartialUpdateBookingDataProvider.class)
    public void shouldReturnForbiddenWhenTokenIsEmpty(BookingResponse original, BookingResponse updated) {
        test = extent.createTest("shouldReturnForbiddenWhenTokenIsEmpty");

        ValidatableResponse response = BookingApi.partialUpdateBooking(original, updated, "");

        // Assert that request returns expected response
        response.assertThat().statusCode(403);
        response.assertThat().body(containsString("Forbidden"));
        
        // Assert that request didn't update Booking
        ValidatableResponse oresponse = BookingApi.getBookingById(Integer.toString(original.getBookingid()));
        Booking rb = new Gson().fromJson(oresponse.extract().body().asString(), Booking.class);
        assertThat(original.getBooking(), equalTo(rb));
    }

    /**
     * PartialUpdate endpoint displays Forbidden message  when valid parameters are sent and token is invalid
     */
    @Test (dataProvider = "PartialUpdateUnauthorizedProvider", dataProviderClass = PartialUpdateBookingDataProvider.class)
    public void shouldReturnForbiddenWhenTokenIsInvalid(BookingResponse original, BookingResponse updated) {
        test = extent.createTest("shouldReturnForbiddenWhenTokenIsInvalid");

        ValidatableResponse response = BookingApi.partialUpdateBooking(original, updated, "33z15z96z1ez36z");

        // Assert that request returns expected response
        response.assertThat().statusCode(403);
        response.assertThat().body(containsString("Forbidden"));

        // Assert that request didn't update Booking
        ValidatableResponse oresponse = BookingApi.getBookingById(Integer.toString(original.getBookingid()));
        Booking rb = new Gson().fromJson(oresponse.extract().body().asString(), Booking.class);
        assertThat(original.getBooking(), equalTo(rb));
    }

    /**
     * PartialUpdate endpoint displays Method not allowed message when valid parameters are sent, token is valid but BookingID doesn't exist
     */
    @Test (dataProvider = "PartialUpdateInvalidBookingIdProvider", dataProviderClass = PartialUpdateBookingDataProvider.class)
    public void shouldReturnForbiddenWhenBookingIdDoesNotExist(BookingResponse original, BookingResponse updated, String invalidBookingId) {
        test = extent.createTest("shouldReturnForbiddenWhenBookingIdDoesNotExist");

        ValidatableResponse response = BookingApi.partialUpdateBooking(updated, invalidBookingId);

        // Assert that request returns expected response
        response.assertThat().statusCode(405);
        response.assertThat().body(containsString("Method Not Allowed"));

        // Assert that request didn't update Booking
        ValidatableResponse oresponse = BookingApi.getBookingById(Integer.toString(original.getBookingid()));
        Booking rb = new Gson().fromJson(oresponse.extract().body().asString(), Booking.class);
        assertThat(original.getBooking(), equalTo(rb));
    }
}
