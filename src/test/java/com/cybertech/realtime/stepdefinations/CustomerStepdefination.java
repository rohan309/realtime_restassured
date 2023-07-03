package com.cybertech.realtime.stepdefinations;

import com.cybertech.realtime.config.ApiRequestBuilder;
import com.cybertech.realtime.config.PropertyHandler;
import com.cybertech.realtime.entities.CustomerPayload;
import com.cybertech.realtime.entities.CustomerResponse;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
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
    PropertyHandler propertyHandler;
    CustomerResponse customerResponse;

    @Given("I create customer")
    public void iCreateCustomer(Map<String, String> data) throws IOException {
        propertyHandler = new PropertyHandler("endpoints.properties");
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
        customerResponse = requestBuilder.response.as(CustomerResponse.class);
        Assert.assertEquals(name, customerResponse.getName());
        Assert.assertFalse(customerResponse.isArchived());
        Assert.assertEquals(payload.getDescription(), customerResponse.getDescription());
    }

    @Then("I verify error message for duplicate customer")
    public void iVerifyErrorMessageForDuplicateCustomer(Map<String, String> data) {

    }

    @Then("I verify error message for duplicate customer with status code {int}")
    public void iVerifyErrorMessageForDuplicateCustomerWithStatusCode(int statusCodeDuplicate, Map<String, String> data) {
        Assert.assertEquals(statusCodeDuplicate, requestBuilder.response.getStatusCode());
        Assert.assertEquals(data.get("errorMsg"), requestBuilder.response.jsonPath().get("message"));
    }

    @And("I create duplicate customer")
    public void iCreateDuplicateCustomer(Map<String, String> data) throws IOException {
        propertyHandler = new PropertyHandler("endpoints.properties");
        String endPoint = propertyHandler.getProperty(data.get("endPoint"));

        payload = new CustomerPayload();


        payload.setName(customerResponse.getName());
        payload.setArchived(customerResponse.isArchived());
        payload.setDescription(customerResponse.getDescription());
        requestBuilder.postRequest(payload, endPoint);
        requestBuilder.response.prettyPrint();
    }
}
