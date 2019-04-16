package com.bookinggo.executables;

import com.bookinggo.searchtaxi.TaxiService;
import com.bookinggo.searchtaxi.TaxiServiceHelper;

import java.util.Scanner;

public class ClientListener {

    public static void main(String[] args){
        int noOfPassengers = 0;
        String pickup;
        String dropoff;

        Scanner sc = new Scanner(System.in);

        System.out.println("Please specify [Pick-up] [Drop-off] [Passenger Count]");
        pickup = sc.next();
        dropoff = sc.next();
        if (!sc.nextLine().isEmpty()) noOfPassengers = sc.nextInt();
        System.out.println("Please wait until we get the available taxis for you!");

        TaxiService taxiService = new TaxiService();
        TaxiServiceHelper taxiServiceHelper = new TaxiServiceHelper();
        taxiServiceHelper.printTaxis(taxiService.getTaxi(noOfPassengers, pickup, dropoff));
    }
}
