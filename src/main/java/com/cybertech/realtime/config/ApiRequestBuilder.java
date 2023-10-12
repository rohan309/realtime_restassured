package com.cybertech.realtime.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class ApiRequestBuilder {

    public RequestSpecification requestSpecification = given();

    public Response response;
    private static ApiRequestBuilder apiRequestBuilder;
    private String pathParam;


    public static ApiRequestBuilder getInstance() {
        if (Objects.isNull(apiRequestBuilder)) {
            apiRequestBuilder = new ApiRequestBuilder();
        }
        return apiRequestBuilder;
    }

    public void setRequestConfig() throws IOException {
        PropertyHandler property = new PropertyHandler("config.properties");
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecification = requestSpecBuilder.setBaseUri(property.getProperty("baseUri"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", property.getProperty("token"))
                .setBasePath(property.getProperty("basePath"))
                .build();
    }

    public void execute(Method method, String endPoint) {
        RequestSpecification rspec = given().log().all();
        switch (method) {
            case GET:
                response = Objects.isNull(pathParam)
                        ? rspec.spec(requestSpecification).get(endPoint)
                        : rspec.spec(requestSpecification).get(endPoint + "/" + "{pathParam}");
                break;
            case POST:
                response = Objects.isNull(pathParam)
                        ? rspec.spec(requestSpecification).post(endPoint)
                        : rspec.spec(requestSpecification).post(endPoint + "/" + "{pathParam}");
                break;
            case PUT:
                response = Objects.isNull(pathParam)
                        ? rspec.spec(requestSpecification).put(endPoint)
                        : rspec.spec(requestSpecification).put(endPoint + "/" + "{pathParam}");
                break;
            case PATCH:
                response = Objects.isNull(pathParam)
                        ? rspec.spec(requestSpecification).patch(endPoint)
                        : rspec.spec(requestSpecification).patch(endPoint + "/" + "{pathParam}");
                break;
            case DELETE:
                response = Objects.isNull(pathParam)
                        ? rspec.spec(requestSpecification).delete(endPoint)
                        : rspec.spec(requestSpecification).delete(endPoint + "/" + "{pathParam}");
                break;
        }
    }


    public void setQueryParams(Map<String, Object> queryParams) {
        Optional.ofNullable(queryParams).ifPresent(params -> requestSpecification.queryParams(params));
    }

    public void setPathParam(String param) {
        Optional.ofNullable(param).ifPresent(p -> {
            pathParam = p;
            requestSpecification.pathParam("pathParam", p);
        });

    }

    /*    public void setRequestBody(String body) {
            Optional.ofNullable(body).ifPresent(obj -> requestSpecification.body(obj));
        }
     public void setRequestBody(Map<String, Object> body) {
            Optional.ofNullable(body).ifPresent(obj -> requestSpecification.body(obj));
        }
     */
    public <T> void setRequestBody(T classObject) {
        Optional.ofNullable(classObject).ifPresent(obj -> requestSpecification.body(obj));

    }


    public JSONObject setRequestBodyWithFile(String filePath) {
        JSONObject jsonObject = null;
        if (Objects.nonNull(filePath) && !filePath.isEmpty()) {
            JSONParser jsonParser = new JSONParser();
            FileReader reader;
            byte[] payload;
            try {
                reader = new FileReader(filePath);
                Object object = jsonParser.parse(reader);
                jsonObject = (JSONObject) object;
                payload = Files.readAllBytes(Path.of(filePath)); // access the content from json file convert into byte array
                requestSpecification.body(payload);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonObject;
    }

    public <T> void postRequest(T clazz, String endPoint) throws IOException {
        setRequestConfig();
        setRequestBody(clazz);
        execute(Method.POST, endPoint);
    }

    public void getRequestWithQueryParam(Map<String, Object> queryParams, String endPoint) throws IOException {
        setRequestConfig();
        setQueryParams(queryParams);
        execute(Method.GET, endPoint);
    }

    public void getRequestWithPathParam(String param, String endPoint) throws IOException {
        setRequestConfig();
        setPathParam(param);
        execute(Method.GET, endPoint);
    }
    public void getRequestWithoutPathParam(String endPoint) throws IOException {
        setRequestConfig();
//        setPathParam(param);
        execute(Method.GET, endPoint);
    }
}