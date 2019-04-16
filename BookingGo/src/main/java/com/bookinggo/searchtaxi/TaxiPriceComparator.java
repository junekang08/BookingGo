package com.bookinggo.searchtaxi;

import org.json.JSONObject;
import java.util.Comparator;

public class TaxiPriceComparator implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject taxi1, JSONObject taxi2) {
        return Integer.compare(taxi1.getInt("price"), taxi2.getInt("price"));
    }
}