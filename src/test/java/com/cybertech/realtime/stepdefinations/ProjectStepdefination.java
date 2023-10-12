package com.cybertech.realtime.stepdefinations;

import com.cybertech.realtime.config.ApiRequestBuilder;
import com.cybertech.realtime.config.PropertyHandler;
import com.cybertech.realtime.entities.ProjectPayload;
import com.cybertech.realtime.entities.ProjectResponse;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProjectStepdefination {
    ApiRequestBuilder requestBuilder = ApiRequestBuilder.getInstance();
    ProjectPayload projectPayload;
    ProjectResponse projectResponse;

    @Given("I create project with valid credentials")
    public void iCreateCustomer(Map<String, String> data) throws IOException {

        PropertyHandler propertyHandler = new PropertyHandler("endpoints.properties");
        String endPoint = propertyHandler.getProperty("projects");

        projectPayload = new ProjectPayload();

        String name = null;
        switch (data.get("name")) {
            case "Random":
                name = new Faker().company().name();
                break;
            case "Empty":
                name = "";
                break;
            default:
                name = data.get("name");
        }

        String desc = null;

        if (data.get("description").equals("Empty")) {
            desc = "";
        } else {
            desc = new Faker().company().catchPhrase();
        }


        projectPayload.setCustomerId(Integer.parseInt(data.get("customerId")));
//        projectPayload.setCustomerId(CustomerStepdefination.customerID);
        projectPayload.setName(name);
        projectPayload.setArchived(Boolean.parseBoolean(data.get("archived")));
        projectPayload.setDescription(desc);

        requestBuilder.postRequest(projectPayload, endPoint);
        requestBuilder.response.prettyPrint();
    }


    @Then("I verify project created with status code")
    public void iVerifyProjectCreatedWithStatusCode(Map<String, String> data) {
        projectResponse = requestBuilder.response.as(ProjectResponse.class);
        Assert.assertEquals(Integer.parseInt(data.get("statusCode")), requestBuilder.response.getStatusCode());
    }

    @Then("I verify project created in all projects with status code {int}")
    public void iVerifyProjectCreatedInAllProjects(int statusCode) {
        Assert.assertEquals(requestBuilder.response.getStatusCode(), statusCode);

    }

    @Given("I create one project with valid credentials")
    public void iCreateOneProjectWithValidCredentials(Map<String, String> data) throws IOException {

        projectPayload.setCustomerId(Integer.parseInt(data.get("customerId")));
        projectPayload.setName(data.get("name"));
        projectPayload.setArchived(Boolean.parseBoolean(data.get("archived")));
        String desc = null;
        if (data.get("description").equals("SampleDescription")) {
            desc = new Faker().company().catchPhrase();
        } else {
            desc = data.get("description");
        }
        projectPayload.setDescription(desc);

        requestBuilder.postRequest(projectPayload, data.get("endPoint"));
        requestBuilder.response.prettyPrint();
    }

    @Given("I get all projects")
    public void iGetAllProjects(Map<String,String> data) throws IOException{
        requestBuilder.getRequestWithoutPathParam(data.get("endPoint"));
    }

    @Then("I verify all projects with status code {int}")
    public void iVerifyAllProjects(int statusCode) {
        Assert.assertEquals(requestBuilder.response.getStatusCode(),statusCode);
        List<String> projectList=requestBuilder.response.jsonPath().get("items.name");
        System.out.println(projectList);

        Collections.sort(projectList);
        List<String> sortedList=projectList;

        System.out.println(sortedList);
        Assert.assertEquals(sortedList,projectList);

    }
}
