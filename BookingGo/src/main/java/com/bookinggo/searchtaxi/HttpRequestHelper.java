package com.bookinggo.searchtaxi;

import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HttpRequestHelper {

    public String getJSONResponse(String urlString){
        try{
            final int CONNECT_TIMEOUT = 2000;
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, CONNECT_TIMEOUT);
            WebTarget webTarget = client.target(urlString);
            Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                throw new RuntimeException("API call failed with HTTP error code : " + response.getStatus());
            }
            String output = response.readEntity(String.class);
            return output;
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
