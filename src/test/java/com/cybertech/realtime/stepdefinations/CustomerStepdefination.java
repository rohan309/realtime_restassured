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
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        } else if (data.get("name").equals("Empty")) {
            name = "";
        } else {
            name = data.get("name");
        }
/**
 * Below method will work only for hard core payload
 */
       /* payload = CustomerPayload.builder()
                .name(name)
                        .description("Sample description")
                                .build();*/

        String desc = null;

        switch (data.get("description")){
            case "Random": desc=new Faker().company().catchPhrase();
            break;
            case "Empty": desc="";
            break;
            default:data.get("description");
        }
        
        payload.setName(name);
        payload.setArchived(Boolean.valueOf(data.get("archived")));
        payload.setDescription(desc);
        requestBuilder.postRequest(payload, endPoint);
        requestBuilder.response.prettyPrint();
    }

    @Then("I verify customer created")
    public void iVerifyCustomerCreated(Map<String, String> data) {

        Assert.assertEquals(Integer.parseInt(data.get("statusCode")), requestBuilder.response.getStatusCode());
        customerResponse = requestBuilder.response.as(CustomerResponse.class);
//        Assert.assertEquals(name, customerResponse.getName());
        Assert.assertEquals(payload.getName(), customerResponse.getName());

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

    @Then("I verify error message for customer with status code {int}")
    public void iVerifyErrorMessageForCustomerWithStatusCode(int expectedStatusCode, Map<String, String> data) {
        Assert.assertEquals(expectedStatusCode, requestBuilder.response.getStatusCode());
        Assert.assertEquals(data.get("errorMsg"), requestBuilder.response.jsonPath().get("message"));
    }

    @Then("I verify customer created in all customers")
    public void iCustomerCreatedInAllCustomers(Map<String,String> data) {
        Assert.assertEquals(Integer.parseInt(data.get("statusCode")),requestBuilder.response.getStatusCode());
        customerResponse=requestBuilder.response.as(CustomerResponse.class);

        List<String> customerNames= Collections.singletonList("");
    }

    @And("I get all customer and verify created customer")
    public void iGetAllCustomerAndVerifyCreatedCustomer() {
//        requestBuilder.getRequestWithPathParam();
    }
}
