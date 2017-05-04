/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.utilities;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chandra.Gorantla
 *
 */
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    Client client = ClientBuilder.newClient();


    public void authenticate(String username, String password) {

        logger.trace(" authenticate entered");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(username, password).build();
        client.register(feature);
        logger.trace(" authenticate exited");

    }


    public void supressHttpCompliance() {
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
    }


    public void configureTimeout(String readTimeout, String connectTimeout) {

        logger.trace(" configureTimeout entered");
        client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
        logger.trace(" configureTimeout exited");

    }


    public void addJacksonFeature() {
        logger.trace(" addJacksonFeature entered");
        client.register(JacksonFeature.class);
        logger.trace(" addJacksonFeature exited");
    }


    public Response getRequest(String uri) {
        logger.trace("getRequest() entered {}", uri);

        Response response = null;
        response = client.target(uri).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
        if (null != response) {
            logger.debug(" Response for get Request, is  {} ", response.getStatus());
        }

        logger.trace("getRequest() exited");
        return response;
    }


    public Response putRequest(String uri, Object jsonInput) {
        logger.trace("putRequest() entered, uri = {}", uri);

        Response response = null;
        String jsonInputAsString = null;
        if (jsonInput != null) {
            jsonInputAsString = JsonUtils.writeToString(jsonInput);
        }
        logger.trace("jsonInputAsString:{} ", jsonInputAsString);
        response = client.target(uri).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Entity.json(jsonInputAsString));

        if (null != response) {
            logger.debug(" Response for putRequest ,is  {} ", response.getStatus());
        }

        logger.trace("putRequest() exited ");
        return response;
    }


    public Response putRequest(String uri, Object jsonInput, boolean redirect) {
        logger.trace("putRequest() entered uri = {}", uri);

        Response response = null;
        String jsonInputAsString = null;
        if (jsonInput != null) {
            jsonInputAsString = JsonUtils.writeToString(jsonInput);
        }
        logger.debug("jsonInputAsString:{} ", jsonInputAsString);
        response = client.target(uri).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).property(ClientProperties.FOLLOW_REDIRECTS, redirect).put(Entity.json(jsonInputAsString));

        if (null != response) {
            logger.debug(" Response for putRequest ,is  {} ", response.getStatus());
        }

        logger.trace("putRequest() exited ");
        return response;
    }


    public Response postRequest(String uri, Object jsonInput) {
        logger.trace("postRequest() entered uri {}", uri);

        Response response = null;
        String jsonInputAsString = null;
        if (jsonInput != null) {
            jsonInputAsString = JsonUtils.writeToString(jsonInput);
        }
        logger.trace("jsonInputAsString:{} ", jsonInputAsString);
        response = client.target(uri).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Entity.json(jsonInputAsString));
        if (null != response) {
            logger.debug(" Response code for post Request, is  {} ", response.getStatus());
        }

        logger.trace("postRequest() exited ");
        return response;
    }


    public Response postRequest(String uri, Object jsonInput, boolean redirect) {
        logger.trace("postRequest() entered uri = {}", uri);

        Response response = null;
        String jsonInputAsString = null;
        if (jsonInput != null) {
            jsonInputAsString = JsonUtils.writeToString(jsonInput);
        }
        logger.trace("jsonInputAsString:{} ", jsonInputAsString);
        response = client.target(uri).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).property(ClientProperties.FOLLOW_REDIRECTS, redirect).post(Entity.json(jsonInputAsString));
        if (null != response) {
            logger.debug(" Response code for post Request, is  {} ", response.getStatus());
        }

        logger.trace("postRequest() exited ");
        return response;
    }


    public Response deleteRequest(String uri) {
        logger.trace("deleteRequest() entered, uri {}", uri);

        Response response = null;
        response = client.target(uri).request(MediaType.APPLICATION_JSON).delete();
        if (null != response) {
            logger.debug(" Response for delete Request, is  {} ", response.getStatus());
        }

        logger.trace("deleteRequest() exited");
        return response;
    }

}
