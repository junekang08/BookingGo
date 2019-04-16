package com.bookinggo.searchtaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Scanner;

public class TaxiService {
    private TaxiServiceHelper taxiServiceHelper;
    private int passengerCount;
    private String pickup;
    private String dropoff;

    public TaxiService()
    {
        taxiServiceHelper = new TaxiServiceHelper();
        this.passengerCount = 0;
    }
    public TaxiService(int passengerCount, String pickup, String dropoff)
    {
        taxiServiceHelper = new TaxiServiceHelper();
        this.passengerCount = passengerCount;
        this.pickup = pickup;
        this.dropoff = dropoff;
    }

    public void inputListener(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Please specify [Pick-up] [Drop-off] [Passenger Count]");
        pickup = sc.next();
        dropoff = sc.next();
        String noOfPassengers = sc.nextLine();
        if (!noOfPassengers.isEmpty()) passengerCount = Integer.parseInt(noOfPassengers.trim());
        System.out.println("Please wait until we get the available taxis for you!");
    }

    public JSONArray getTaxiFromCommandLine(){
        try{
            inputListener();
            ArrayList<JSONObject> validTaxis = taxiServiceHelper.getValidTaxis(passengerCount, pickup, dropoff);
            taxiServiceHelper.sortTaxisByPrice(validTaxis);
            return taxiServiceHelper.jsonify(validTaxis);
        }
        catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }
    public String getTaxi(){
        try{
            ArrayList<JSONObject> validTaxis = taxiServiceHelper.getValidTaxis(passengerCount, pickup, dropoff);
            taxiServiceHelper.sortTaxisByPrice(validTaxis);
            return taxiServiceHelper.jsonify(validTaxis).toString();
        }
        catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    public JSONArray getTaxisUsingAPI(){
        try{
            inputListener();
            String validTaxis = taxiServiceHelper.getValidTaxisUsingAPI(passengerCount, pickup, dropoff);
            System.out.println(validTaxis);
            return new JSONArray(validTaxis);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
