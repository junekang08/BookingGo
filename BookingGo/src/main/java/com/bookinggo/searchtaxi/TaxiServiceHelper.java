package com.bookinggo.searchtaxi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TaxiServiceHelper {

    private ApiHelper apiHelper;
    private HttpRequestHelper httpRequestHelper;

    public TaxiServiceHelper(){
        apiHelper = new ApiHelper();
        httpRequestHelper = new HttpRequestHelper();
    }

    private Stream<Object> arrayToStream(JSONArray array) {
        return StreamSupport.stream(array.spliterator(), false);
    }

    int getTaxiCapacity(String carType){
        switch(carType){
            case "STANDARD":
                return 4;
            case "EXECUTIVE":
                return 4;
            case "LUXURY":
                return 4;
            case "PEOPLE_CARRIER":
                return 6;
            case "LUXURY_PEOPLE_CARRIER":
                return 6;
            case "MINIBUS":
                return 16;
            default:
                throw new IllegalArgumentException("The car type \'" + carType + "\' is not supported");
        }
    }

    ArrayList<JSONObject> addValidTaxis(JSONObject response, int noOfPassengers, ArrayList<JSONObject> validTaxis){
        arrayToStream(response.getJSONArray("options"))
                .map(JSONObject.class::cast)
                .filter(car -> getTaxiCapacity(car.getString("car_type")) >= noOfPassengers)
                .forEach(car -> {
                    car.put("supplier_id", response.getString("supplier_id"));
                    validTaxis.add(car);
                });
        return validTaxis;
    }

    public void printTaxis(JSONArray taxis){
        arrayToStream(taxis)
                .map(JSONObject.class::cast)
                .forEach(taxi -> {
                    System.out.printf("{%s} - {%s} - {%d}\n", taxi.getString("car_type")
                            , taxi.get("supplier_id")
                            , taxi.getInt("price"));
                });
    }

    ArrayList<String> getApiServiceDomains(){
        final String domainDave = "https://techtest.rideways.com/dave";
        final String domainEric = "https://techtest.rideways.com/eric";
        final String domainJeff = "https://techtest.rideways.com/jeff";

        return new ArrayList<>(Arrays.asList(domainDave, domainEric, domainJeff));
    }

    ArrayList<JSONObject> getValidTaxis(int passengerCount, String pickup, String dropoff){
        try{
            ArrayList<String> domains = getApiServiceDomains();
            ArrayList<JSONObject> validTaxis = new ArrayList<>();
            for (String domain : domains){
                String response = httpRequestHelper
                                    .getJSONResponse(apiHelper.apiURLBuilder(domain, pickup, dropoff));
                if (response != null){
                    validTaxis = addValidTaxis(new JSONObject(response), passengerCount, validTaxis);
                }
            }
            return validTaxis;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    JSONArray getValidTaxisUsingAPI(int passengerCount, String pickup, String dropoff){
        try{
            final String domain = "http://localhost:8080/taxiapi_war_exploded/taxis";
            String response = httpRequestHelper
                                .getJSONResponse(apiHelper.myApiURLBuilder(domain, passengerCount, pickup, dropoff));
            return new JSONArray(response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    void sortTaxisByPrice(ArrayList<JSONObject> validTaxis){
        Collections.sort(validTaxis, Collections.reverseOrder(new TaxiPriceComparator()));
    }

    JSONArray jsonify(ArrayList<JSONObject> array){
        JSONArray jsonArray = new JSONArray();
        for (JSONObject obj : array){
            jsonArray.put(obj);
        }
        return jsonArray;
    }
}
