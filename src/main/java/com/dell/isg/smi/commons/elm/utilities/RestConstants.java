/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.utilities;

public final class RestConstants {

    private RestConstants() {
    }

    public static final String REST_API_ENDPOINT = "/api";

    // Request Headers
    public static final String DELL_AUTH_TIMESTAMP = "x-dell-auth-timestamp";
    public static final String DELL_AUTH_KEY = "x-dell-auth-key";
    public static final String DELL_AUTH_SIGNATURE = "x-dell-auth-signature";
    public static final String DELL_API_VERSION = "x-dell-api-version";
    public static final String DELL_JOB_URI = "x-dell-job-uri";
    public static final String DELL_PROXY_URI = "x-dell-proxy-uri";
    public static final String DELL_ORIG_PATH = "x-dell-orig-path";
    public static final String DELL_SERVICE_CONTEXT_HEADER = "x-dell-service-context";
    public static final String DELL_TOTAL_COUNT_HEADER = "x-dell-collection-total-count";

    // Standard headers not defined in HttpHeaders in JAX-RS.
    public static final String LINK = "Link";

    // Query parameters

    /**
     * Name of the query parameter that should be used to specify columns to sort on for queries.
     */
    public static final String QUERY_PARAM_SORT = "sort";

    /**
     * Name of the query parameter that should be used to specify columns to filter on for queries.
     */
    public static final String QUERY_PARAM_FILTER = "filter";

    /**
     * Name of the query parameter that should be used to specify limit on for queries.
     */
    public static final String QUERY_PARAM_LIMIT = "limit";

    /**
     * Name of the query parameter that should be used to specify offset on for queries.
     */
    public static final String QUERY_PARAM_OFFSET = "offset";

    // ASM user roles
    public static final String USERROLE_ADMINISTRATOR = "Administrator";
    public static final String USERROLE_OPERATOR = "standard";
    public static final String USERROLE_READONLY = "ReadOnly";
}
