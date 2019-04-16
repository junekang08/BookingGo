package com.bookinggo.searchtaxi;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@PrepareForTest(ClientBuilder.class)
@RunWith(PowerMockRunner.class)
class HttpRequestHelperTest {

    @Test
    void getJSONResponse() {
        HttpRequestHelper httpRequestHelper = new HttpRequestHelper();
        String expected = null;
        final String domain = "http://localhost:8080/taxiapi_war_exploded/taxis";
        final int passengerCount = 5;
        final String pickup = "3.410632,-2.157533";
        final String dropoff = "3.410632,-2.157533";
        String url = domain + "?passengerCount=" + passengerCount + "&pickup=" + pickup + "&dropoff=" + dropoff;

        Client client = mock( Client.class );
        WebTarget webTarget = mock (WebTarget.class);
        Invocation.Builder builder = mock(Invocation.Builder.class);
        Response response = mock(Response.class);

        PowerMockito.mockStatic(ClientBuilder.class);
        PowerMockito.when(ClientBuilder.newClient()).thenReturn(client);

        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(anyString())).thenReturn(builder);
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(404);
        when(response.readEntity(String.class)).thenReturn(expected);

        assertEquals(expected, httpRequestHelper.getJSONResponse(url));

    }
}