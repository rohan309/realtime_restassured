package com.cybertech.realtime.stepdefinations;

import com.cybertech.realtime.config.ApiRequestBuilder;
import com.cybertech.realtime.config.PropertyHandler;
import com.cybertech.realtime.entities.TaskPayload;
import com.cybertech.realtime.entities.TaskResponse;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.io.IOException;
import java.util.Map;

public class TaskStepdefination {
    PropertyHandler propertyHandler;
    ApiRequestBuilder requestBuilder = ApiRequestBuilder.getInstance();
    String name = null;
    String desc = null;
    TaskPayload payload;

    @Given("I create task")
    public void createTask(Map<String, String> data) throws IOException {
        propertyHandler = new PropertyHandler("endpoints.properties");
        String endPoint = propertyHandler.getProperty(data.get("endPoint"));

        payload = new TaskPayload();

        if (data.get("name").equals("Random")) {
            name = new Faker().name().title();
        }
        payload.setName(name);

        if (data.get("description").equals("Sample description")) {
            desc = new Faker().company().catchPhrase();
        }

        payload.setDescription(desc);
        payload.setStatus(data.get("status"));
        payload.setProjectId(Integer.parseInt(data.get("projectId")));
        payload.setTypeOfWorkId(Integer.parseInt(data.get("typeOfWorkId")));
        payload.setEstimatedTime(Integer.parseInt(data.get("estimatedTime")));

        requestBuilder.postRequest(payload, endPoint);

    }

    @Then("I verify task created with statusCode {int}")
    public void iVerifyTaskCreated(int statusCode) {

        Assert.assertEquals(statusCode, requestBuilder.response.getStatusCode());
        TaskResponse taskResponse = requestBuilder.response.as(TaskResponse.class);
        Assert.assertEquals(name, taskResponse.getName());
        Assert.assertEquals(desc, taskResponse.getDescription());
        Assert.assertEquals(payload.getStatus(), taskResponse.getStatus());
        Assert.assertEquals(payload.getProjectId(), taskResponse.getProjectId());
        Assert.assertEquals(payload.getTypeOfWorkId(), taskResponse.getTypeOfWorkId());
        Assert.assertEquals(payload.getEstimatedTime(), taskResponse.getEstimatedTime());
    }
}
