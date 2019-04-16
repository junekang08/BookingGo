package com.bookinggo.searchtaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class TaxiService {
    private TaxiServiceHelper taxiServiceHelper;

    public TaxiService(){
        taxiServiceHelper = new TaxiServiceHelper();
    }

    public JSONArray getTaxi(int noOfPassengers, String pickup, String dropoff){
        try{
            ArrayList<JSONObject> validTaxis = taxiServiceHelper.getValidTaxis(noOfPassengers, pickup, dropoff);
            taxiServiceHelper.sortTaxisByPrice(validTaxis);
            return taxiServiceHelper.jsonify(validTaxis);
        }
        catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    public JSONArray getTaxisUsingAPI(int noOfPassengers, String pickup, String dropoff){
        try{
            return taxiServiceHelper.getValidTaxisUsingAPI(noOfPassengers, pickup, dropoff);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
