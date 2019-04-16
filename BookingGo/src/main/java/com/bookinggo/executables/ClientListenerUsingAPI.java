package com.bookinggo.executables;

import com.bookinggo.searchtaxi.TaxiService;
import com.bookinggo.searchtaxi.TaxiServiceHelper;

import java.util.Scanner;

public class ClientListenerUsingAPI {
    public static void main(String[] args) {
        try{
            int passengerCount = 0;
            String pickup;
            String dropoff;

            Scanner sc = new Scanner(System.in);
            System.out.println("Please specify [Pick-up] [Drop-off] [Passenger Count]");
            pickup = sc.next();
            dropoff = sc.next();
            if (!sc.nextLine().isEmpty()) passengerCount = sc.nextInt();
            System.out.println("Please wait until we get the available taxis for you!");

            TaxiService taxiService = new TaxiService();
            TaxiServiceHelper taxiServiceHelper = new TaxiServiceHelper();
            taxiServiceHelper.printTaxis(taxiService.getTaxisUsingAPI(passengerCount, pickup, dropoff));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
