/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dell.isg.smi.commons.elm.messaging.IMessageEnum;

/**
 * @author lakshmi.lakkireddy
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessValidationException extends RuntimeCoreException {

    /**
     * Instantiates a new business validation exception.
     */
    public BusinessValidationException() {
        super();
    }


    /**
     * Instantiates a new business validation exception.
     *
     * @param enumErrorCode the enum error code
     */
    public BusinessValidationException(IMessageEnum enumErrorCode) {
        super(enumErrorCode);
    }


    /**
     * Instantiates a new business validation exception.
     *
     * @param e the e
     */
    public BusinessValidationException(Throwable e) {
        super(e);
    }


    /**
     * Instantiates a new business validation exception.
     *
     * @param msg the msg
     */
    public BusinessValidationException(String msg) {
        super(msg);
    }


    /**
     * Instantiates a new business validation exception.
     *
     * @param msg the msg
     * @param e the e
     */
    public BusinessValidationException(String msg, Throwable e) {
        super(msg, e);
    }

    private static final long serialVersionUID = 2467192664312928457L;


    /**
     * Handles BusinessValidationException - added multiple values as ONE Attribute
     *
     * @param enumCode   The enumerated error code
     * @param valueNames The values to append
     * @throws BusinessValidationException  A business validation exception
     */
    public static void handleBusinessValidationException(IMessageEnum enumCode, String... valueNames) {
        BusinessValidationException exception = new BusinessValidationException();
        StringBuilder valArgs = new StringBuilder();
        exception.setErrorCode(enumCode);
        exception.setErrorID(enumCode.getId());
        for (String vName : valueNames) {
            valArgs.append(" ");
            valArgs.append(vName);
        }
        exception.addAttribute(valArgs.toString());
        throw exception;
    }

}
