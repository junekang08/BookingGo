package com.bookinggo.searchtaxi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiHelperTest {

    @Test
    void apiURLBuilder() {
        final String domain = "www.bookinggo.com";
        final String pickup = "3.410632,-2.157533";
        final String dropoff = "3.410632,-2.157533";
        final String expected = "www.bookinggo.com?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533";
        ApiHelper apiHelper = new ApiHelper();
        String actual = apiHelper.apiURLBuilder(domain, pickup, dropoff);
        assertEquals(expected, actual);
    }

    @Test
    void myApiURLBuilder() {
        final String domain = "www.bookinggo.com";
        final int passengerCount = 5;
        final String pickup = "3.410632,-2.157533";
        final String dropoff = "3.410632,-2.157533";
        final String expected = "www.bookinggo.com?passengerCount=" + passengerCount
                                + "&pickup=" + pickup + "&dropoff=" + dropoff;
        ApiHelper apiHelper = new ApiHelper();
        String actual = apiHelper.myApiURLBuilder(domain, passengerCount, pickup, dropoff);
        assertEquals(expected, actual);
    }
}