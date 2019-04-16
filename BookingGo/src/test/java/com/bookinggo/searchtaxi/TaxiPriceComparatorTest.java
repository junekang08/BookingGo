package com.bookinggo.searchtaxi;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxiPriceComparatorTest {

    @Test
    public void testEqual() {
        JSONObject taxi1 = new JSONObject();
        taxi1.put("price", 1234);
        JSONObject taxi2 = new JSONObject();
        taxi2.put("price", 1234);
        TaxiPriceComparator taxiPriceComparator = new TaxiPriceComparator();

        assertEquals(taxiPriceComparator.compare(taxi1, taxi2), 0);
    }

    @Test
    public void testGreaterThan() {
        JSONObject taxi1 = new JSONObject();
        taxi1.put("price", 12345);
        JSONObject taxi2 = new JSONObject();
        taxi2.put("price", 1234);
        TaxiPriceComparator taxiPriceComparator = new TaxiPriceComparator();

        assertEquals(taxiPriceComparator.compare(taxi1, taxi2), 1);
    }

    @Test
    public void testLessThan() {
        JSONObject taxi1 = new JSONObject();
        taxi1.put("price", 1234);
        JSONObject taxi2 = new JSONObject();
        taxi2.put("price", 12345);
        TaxiPriceComparator taxiPriceComparator = new TaxiPriceComparator();

        assertEquals(taxiPriceComparator.compare(taxi1, taxi2), -1);
    }
}