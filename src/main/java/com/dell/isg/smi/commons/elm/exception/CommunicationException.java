/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.exception;

import com.dell.isg.smi.commons.elm.model.EnumErrorCode;

/**
 * @author michael.regert
 *
 */
public class CommunicationException extends RuntimeCoreException {

    private static final long serialVersionUID = -7713024188690225574L;


    /**
     * Instantiates a new communication exception.
     */
    public CommunicationException() {
        super();
        this.setErrorCode(EnumErrorCode.ENUM_COMMUNICATION_ERROR);
    }


    /**
     * Instantiates a new communication exception.
     *
     * @param code the code
     */
    public CommunicationException(long code) {
        super(code);
        this.setErrorCode(EnumErrorCode.ENUM_COMMUNICATION_ERROR);
    }


    /**
     * Instantiates a new communication exception.
     *
     * @param e the e
     */
    public CommunicationException(Throwable e) {
        super(e);
        this.setErrorCode(EnumErrorCode.ENUM_COMMUNICATION_ERROR);
    }


    /**
     * Instantiates a new communication exception.
     *
     * @param msg the msg
     */
    public CommunicationException(String msg) {
        super(msg);
        this.setErrorCode(EnumErrorCode.ENUM_COMMUNICATION_ERROR);
    }


    /**
     * Instantiates a new communication exception.
     *
     * @param msg the msg
     * @param e the e
     */
    public CommunicationException(String msg, Throwable e) {
        super(msg, e);
        this.setErrorCode(EnumErrorCode.ENUM_COMMUNICATION_ERROR);
    }


    /**
     * Instantiates a new communication exception.
     *
     * @param errorCode the error code
     */
    public CommunicationException(EnumErrorCode errorCode) {
        super(errorCode);
        this.setErrorCode(EnumErrorCode.ENUM_COMMUNICATION_ERROR);
    }

}
