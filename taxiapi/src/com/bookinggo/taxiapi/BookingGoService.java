package com.bookinggo.taxiapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.bookinggo.searchtaxi.TaxiService;

@Path("/taxis")
public class BookingGoService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTaxis(@QueryParam("passengerCount") int passengerCount
                            , @QueryParam("pickup") String pickup
                            , @QueryParam("dropoff") String dropoff) {
        TaxiService taxiService = new TaxiService();
        return taxiService.getTaxi(passengerCount, pickup, dropoff).toString();
    }
}

