package com.cybertech.realtime.stepdefinations;

import com.cybertech.realtime.config.ApiRequestBuilder;
import com.cybertech.realtime.config.PropertyHandler;
import com.cybertech.realtime.entities.CustomerPayload;
import com.cybertech.realtime.entities.CustomerResponse;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class CustomerStepdefination {
    ApiRequestBuilder requestBuilder = ApiRequestBuilder.getInstance();
    String name = " ";
    CustomerPayload payload;

    @Given("I create customer")
    public void iCreateCustomer(Map<String, String> data) throws IOException {
        PropertyHandler propertyHandler = new PropertyHandler("endpoints.properties");
        String endPoint = propertyHandler.getProperty(data.get("endPoint"));

        payload = new CustomerPayload();

      /* Optional<String> optional=Optional.of(data.get("name"));
       optional.equals()*/


        if (data.get("name").equals("Random")) {
            name = new Faker().company().name();
        } else {
            name = data.get("name");
        }


        payload.setName(name);
        payload.setArchived(Boolean.valueOf(data.get("archived")));
        payload.setDescription(data.get("description"));
        requestBuilder.postRequest(payload, endPoint);
        requestBuilder.response.prettyPrint();
    }

    @Then("I verify customer created")
    public void iVerifyCustomerCreated(Map<String, String> data) {

        Assert.assertEquals(Integer.parseInt(data.get("statusCode")), requestBuilder.response.getStatusCode());
        CustomerResponse customerResponse=requestBuilder.response.as(CustomerResponse.class);
        Assert.assertEquals(name,customerResponse.getName());
        Assert.assertFalse(customerResponse.isArchived());
        Assert.assertEquals(payload.getDescription(),customerResponse.getDescription());
    }
}
