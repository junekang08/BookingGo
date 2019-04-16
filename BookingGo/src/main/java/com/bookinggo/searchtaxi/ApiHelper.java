package com.bookinggo.searchtaxi;

public class ApiHelper {
    public String apiURLBuilder(String domain, String pickup, String dropoff){
        StringBuilder query =  new StringBuilder();
        query.append(domain);
        query.append("?");
        query.append("pickup=" + pickup);
        query.append("&");
        query.append("dropoff=" + dropoff);
        return query.toString();
    }
    public String myApiURLBuilder(String domain, int passsengerCount, String pickup, String dropoff){
        StringBuilder query =  new StringBuilder();
        query.append(domain);
        query.append("?");
        query.append("passengerCount=" + passsengerCount);
        query.append("&");
        query.append("pickup=" + pickup);
        query.append("&");
        query.append("dropoff=" + dropoff);
        return query.toString();
    }
}
