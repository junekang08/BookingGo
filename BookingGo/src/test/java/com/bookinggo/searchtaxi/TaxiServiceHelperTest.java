package com.bookinggo.searchtaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Arrays;

import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
class TaxiServiceHelperTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Mock
    ApiHelper apiHelper = new ApiHelper();
    @Mock
    HttpRequestHelper httpRequestHelper = new HttpRequestHelper();

    @Spy
    @InjectMocks
    TaxiServiceHelper taxiServiceHelperInjected = new TaxiServiceHelper(){};

    private String response = "{\"supplier_id\": \"DAVE\"," +
                                  "\"pickup\": \"51.470020,-0.454295\"," +
                                  "\"dropoff\": \"51.00000,1.0000\"," +
                                  "\"options\": [{" +
                                      "\"car_type\": \"STANDARD\",\"price\": 671808}," +
                                      "{\"car_type\": \"EXECUTIVE\",\"price\": 375545}," +
                                      "{\"car_type\": \"LUXURY\",\"price\": 583438}," +
                                      "{\"car_type\": \"MINIBUS\",\"price\": 37456}]}";

    @ParameterizedTest
    @CsvSource({ "STANDARD, 4", "EXECUTIVE, 4", "LUXURY, 4", "PEOPLE_CARRIER, 6", "LUXURY_PEOPLE_CARRIER, 6", "MINIBUS, 16"})
    void getTaxiCapacity(String carType, int capacity) {
        TaxiServiceHelper taxiServiceHelper = new TaxiServiceHelper();
        assertEquals(capacity, taxiServiceHelper.getTaxiCapacity(carType));
    }

    @Test
    void addValidTaxis() {
        TaxiServiceHelper taxiServiceHelper = new TaxiServiceHelper();
        final int noOfPassengers = 5;
        ArrayList<JSONObject> actual = new ArrayList<>();
        actual = taxiServiceHelper.addValidTaxis(new JSONObject(response), noOfPassengers, actual);
        ArrayList<JSONObject> expected = new ArrayList<>();
        expected.add(new JSONObject("{\"price\":37456,\"supplier_id\":\"DAVE\",\"car_type\":\"MINIBUS\"}"));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void printTaxis() {
        TaxiServiceHelper taxiServiceHelper = new TaxiServiceHelper();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        JSONObject taxi1 = new JSONObject();
        taxi1.put("price", 37456);
        taxi1.put("supplier_id", "DAVE");
        taxi1.put("car_type", "MINIBUS");
        JSONObject taxi2 = new JSONObject();
        taxi2.put("price", 583438);
        taxi2.put("supplier_id", "ERIC");
        taxi2.put("car_type", "LUXURY");
        JSONObject taxi3 = new JSONObject();
        taxi3.put("price", 375545);
        taxi3.put("supplier_id", "JEFF");
        taxi3.put("car_type", "STANDARD");
        JSONArray taxisJSONArray = new JSONArray();
        taxisJSONArray.put(taxi1);
        taxisJSONArray.put(taxi2);
        taxisJSONArray.put(taxi3);
        taxiServiceHelper.printTaxis(taxisJSONArray);
        String expected = "{MINIBUS} - {DAVE} - {37456}\n" +
                "{LUXURY} - {ERIC} - {583438}\n" +
                "{STANDARD} - {JEFF} - {375545}\n";
        assertEquals(expected, outContent.toString());
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void getValidTaxis() {
        final int noOfPassengers = 5;
        String url = "www.bookinggo.com";
        final String pickup = "3.410632,-2.157533";
        final String dropoff = "3.410632,-2.157533";
        final String responseDave = null;
        final String responseEric = null;
        final String responseJeff = "{\"supplier_id\": \"Jeff\"," +
                                        "\"options\": [" +
                                        "{\"car_type\": \"MINIBUS\",\"price\": 37456}]}";
        ApiHelper apiHelper = mock(ApiHelper.class);
        HttpRequestHelper httpRequestHelper = mock(HttpRequestHelper.class);
        Mockito.when(apiHelper.apiURLBuilder(anyString(), anyString(), anyString())).thenReturn(url);
        Mockito.when(httpRequestHelper.getJSONResponse(anyString())).thenReturn(responseDave, responseEric, responseJeff);

        TaxiServiceHelper taxiServiceHelper = mock(TaxiServiceHelper.class);

        ArrayList<JSONObject> validTaxis = new ArrayList<>();
        validTaxis.add(new JSONObject("{\"price\":37456,\"supplier_id\":\"JEFF\",\"car_type\":\"MINIBUS\"}"));

        doReturn(validTaxis).when(taxiServiceHelper).addValidTaxis(anyObject(), anyInt(), anyObject());
        ArrayList<JSONObject> actual = taxiServiceHelper.getValidTaxis(noOfPassengers, pickup, dropoff);
        assertEquals(validTaxis.toString(),actual.toString());
    }

    @Test
    void getValidTaxisUsingAPI() {
        final int passengerCount = 5;
        final String domain = "http://localhost:8080/taxiapi_war_exploded/taxis";
        final String pickup = "3.410632,-2.157533";
        final String dropoff = "3.410632,-2.157533";
        final JSONObject expectedTaxi
                = new JSONObject("{\"price\":37456,\"supplier_id\":\"DAVE\",\"car_type\":\"MINIBUS\"}");

        ArrayList<JSONObject> validTaxis = new ArrayList<>();
        validTaxis.add(expectedTaxi);

        String apiResponse = "[{\"price\":37456,\"supplier_id\":\"DAVE\",\"car_type\":\"MINIBUS\"}]";
        String url = domain + "?passengerCount=" + passengerCount + "&pickup=" + pickup + "&dropoff=" + dropoff;

        Mockito.when(apiHelper.myApiURLBuilder(domain, passengerCount, pickup, dropoff)).thenReturn(url);
        Mockito.when(httpRequestHelper.getJSONResponse(url)).thenReturn(apiResponse);

        JSONArray actual = taxiServiceHelperInjected.getValidTaxisUsingAPI(passengerCount, pickup, dropoff);
        assertEquals(validTaxis.toString(),actual.toString());
    }

    @Test
    void jsonify() {
        JSONObject taxi1 = new JSONObject();
        taxi1.put("price", 37456);
        taxi1.put("supplier_id", "DAVE");
        taxi1.put("car_type", "MINIBUS");
        JSONObject taxi2 = new JSONObject();
        taxi2.put("price", 583438);
        taxi2.put("supplier_id", "ERIC");
        taxi2.put("car_type", "LUXURY");
        JSONArray expected = new JSONArray();
        expected.put(taxi1);
        expected.put(taxi2);
        ArrayList<JSONObject> array = new ArrayList<>(Arrays.asList(taxi1, taxi2));
        JSONArray actual = taxiServiceHelperInjected.jsonify(array);
        assertEquals(expected.toString(), actual.toString());
    }
}