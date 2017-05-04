/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.exception;

/**
 *
 */
public enum ExceptionSeverityEnum {

    CRITICAL("CRITICAL", 2), WARNING("WARNING", 1), INFORMATIONAL("INFORMATIONAL", 0);

    private Integer id;
    private String value;


    private ExceptionSeverityEnum(String value, Integer id) {
        this.value = value;
        this.id = id;
    }


    public String getValue() {
        return this.value;
    }


    public Integer getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
