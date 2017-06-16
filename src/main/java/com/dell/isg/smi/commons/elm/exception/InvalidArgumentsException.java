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
 * This exception should be used when required input is not provided or is incorrect
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentsException extends RuntimeCoreException {

    private static final long serialVersionUID = 1L;


    // Default Constructor
    public InvalidArgumentsException() {
    	super();
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
    }

    public InvalidArgumentsException(Throwable e) {
    	super(e);
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
    }

    public InvalidArgumentsException(IMessageEnum errorCode) {
    	super();
    	this.setErrorCode(errorCode);
    }
    
    public InvalidArgumentsException(IMessageEnum errorCode, Throwable e) {
        super(e);
    	this.setErrorCode(errorCode);
    }

    public InvalidArgumentsException(IMessageEnum errorCode, String attributeName) {
    	super();
    	this.setErrorCode(errorCode);
        this.addAttribute(attributeName);
    }

    public InvalidArgumentsException(IMessageEnum errorCode, String attributeName, Throwable e) {
    	super(e);
    	this.setErrorCode(errorCode);
    	this.addAttribute(attributeName);
    }

    public InvalidArgumentsException(String attributeName) {
        super();
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
        this.addAttribute(attributeName);
    }

    public InvalidArgumentsException(String attributeName, Throwable e) {
        super(e);
        this.setErrorCode(EnumErrorCode.ENUM_INVALID_DATA);
        this.addAttribute(attributeName);
    }
}
