package com.api.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.dataProviders.DeleteBookingDataProvider;
import com.api.repositories.EnumRepository;
import com.api.requests.BookingApi;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;

/**
 * Includes API tests for DeleteBooking endpoint
 */
public class DeleteBookingTests
{
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    /**
     * Test Setup to initialize data repositories and report parameters
     */
    @BeforeTest
    public void setup() {
        EnumRepository.INSTANCE.getInstance();

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/DeleteBookingTestsResults.html");
        htmlReporter.config().setDocumentTitle("API Automation Report");
        htmlReporter.config().setReportName("DeleteBooking endpoint Report");
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
     * DeleteBooking endpoint removes an existing Booking when using a valid ID
     */
    @Test (dataProvider = "DeleteBookingSuccess", dataProviderClass = DeleteBookingDataProvider.class)
    public void shouldRemoveBookingWhenUsingValidBookingId(String bookingId) {
        test = extent.createTest("shouldRemoveBookingWhenUsingValidBookingId");

        ValidatableResponse response = BookingApi.deleteBooking(bookingId);

        response.assertThat().statusCode(201);
        response.assertThat().body(containsString("Created"));
    }

    /**
     * DeleteBooking endpoint returns NotFound when no ID parameter is sent
     */
    @Test
    public void shouldReturnNotFoundWhenBookingIdNotSent() {
        test = extent.createTest("shouldReturnNotFoundWhenBookingIdNotSent");

        ValidatableResponse response = BookingApi.deleteBooking("");

        response.assertThat().statusCode(404);
        response.assertThat().body(containsString("Not Found"));
    }

    /**
     * DeleteBooking endpoint returns Method Not Allowed when ID parameter is invalid or doesn't exist
     */
    @Test (dataProvider = "InvalidBookingIdDataProvider", dataProviderClass = DeleteBookingDataProvider.class)
    public void shouldReturnMethodNotAllowedWhenBookingIdValueIsInvalidOrNotExists(String invalidBookingId) {
        test = extent.createTest("shouldReturnMethodNotAllowedWhenBookingIdValueIsInvalidOrNotExists");
        
        ValidatableResponse response = BookingApi.deleteBooking(invalidBookingId);

        response.assertThat().statusCode(405);
        response.assertThat().body(containsString("Method Not Allowed"));
    }

    /**
     * DeleteBooking endpoint returns Forbidden when a valid Booking ID but no Token is sent
     */
    @Test (dataProvider = "DeleteBookingSuccess", dataProviderClass = DeleteBookingDataProvider.class)
    public void shouldReturnForbiddenWhenTokenIsNotSent(String bookingId) {
        test = extent.createTest("shouldReturnForbiddenWhenTokenIsNotSent");
        
        ValidatableResponse response = BookingApi.deleteBooking(bookingId, "");

        response.assertThat().statusCode(403);
        response.assertThat().body(containsString("Forbidden"));
    }

    /**
     * DeleteBooking endpoint returns Forbidden when a valid Booking ID but invalid Token is sent
     */
    @Test (dataProvider = "DeleteBookingSuccess", dataProviderClass = DeleteBookingDataProvider.class)
    public void shouldReturnForbiddenWhenInvalidTokenIsSent(String bookingId) {
        test = extent.createTest("shouldReturnForbiddenWhenInvalidTokenIsSent");
        
        ValidatableResponse response = BookingApi.deleteBooking(bookingId, "72xxx83131270xx");

        response.assertThat().statusCode(403);
        response.assertThat().body(containsString("Forbidden"));
    }
}