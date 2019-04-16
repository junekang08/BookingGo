package com.bookinggo.executables;

import com.bookinggo.searchtaxi.TaxiService;
import com.bookinggo.searchtaxi.TaxiServiceHelper;

public class ClientListener {

    public static void main(String[] args){
        try{
            TaxiService taxiService = new TaxiService();
            TaxiServiceHelper taxiServiceHelper = new TaxiServiceHelper();
            taxiServiceHelper.printTaxis(taxiService.getTaxiFromCommandLine());
        }
        catch(Exception e){
            System.out.println("Please provide a valid argument!");
        }
    }
}
