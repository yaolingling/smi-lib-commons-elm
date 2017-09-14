/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dell.isg.smi.commons.elm.messaging.IMessageEnum;
import com.dell.isg.smi.commons.elm.model.EnumErrorCode;

/**
 * This exception should be used when required input is not provided or is incorrect.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentsException extends RuntimeCoreException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new invalid arguments exception.
     */
    // Default Constructor
    public InvalidArgumentsException() {
    	super();
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
    }

    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param e the e
     */
    public InvalidArgumentsException(Throwable e) {
    	super(e);
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
    }

    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param errorCode the error code
     */
    public InvalidArgumentsException(IMessageEnum errorCode) {
    	super();
    	this.setErrorCode(errorCode);
    }
    
    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param errorCode the error code
     * @param e the e
     */
    public InvalidArgumentsException(IMessageEnum errorCode, Throwable e) {
        super(e);
    	this.setErrorCode(errorCode);
    }

    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param errorCode the error code
     * @param attributeName the attribute name
     */
    public InvalidArgumentsException(IMessageEnum errorCode, String attributeName) {
    	super();
    	this.setErrorCode(errorCode);
        this.addAttribute(attributeName);
    }

    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param errorCode the error code
     * @param attributeName the attribute name
     * @param e the e
     */
    public InvalidArgumentsException(IMessageEnum errorCode, String attributeName, Throwable e) {
    	super(e);
    	this.setErrorCode(errorCode);
    	this.addAttribute(attributeName);
    }

    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param attributeName the attribute name
     */
    public InvalidArgumentsException(String attributeName) {
        super();
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
        this.addAttribute(attributeName);
    }

    /**
     * Instantiates a new invalid arguments exception.
     *
     * @param attributeName the attribute name
     * @param e the e
     */
    public InvalidArgumentsException(String attributeName, Throwable e) {
        super(e);
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
        this.addAttribute(attributeName);
    }
}
