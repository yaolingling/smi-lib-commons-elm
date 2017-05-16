/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.exception;

import java.util.LinkedList;
import java.util.List;

import com.dell.isg.smi.commons.elm.messaging.IMessageEnum;

/**
 *
 * This class is meant to be delegated by Checked Base Exception or Runtime Base Exception
 *
 */
public class BaseException {

    private List<String> attributes = new LinkedList<>();

    int errorID = 0;
    ExceptionSeverityEnum severity = ExceptionSeverityEnum.CRITICAL;
    IMessageEnum errorCode;


    /**
     * Main Constructor Package Scope.
     */
    BaseException() {

    }


    /**
     * Add Attribute.. package scope
     *
     * @param name
     * @param value
     */
    void addAttribute(String value) {
        attributes.add(value);
    }


    /**
     * get Attributes list
     *
     * @return List<String> - Attributes list.. name,value, name, value
     */
    List<String> getAttributes() {
        return this.attributes;
    }

}
