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

    public BusinessValidationException() {
        super();
    }


    public BusinessValidationException(IMessageEnum enumErrorCode) {
        super(enumErrorCode);
    }


    public BusinessValidationException(Throwable e) {
        super(e);
    }


    public BusinessValidationException(String msg) {
        super(msg);
    }


    public BusinessValidationException(String msg, Throwable e) {
        super(msg, e);
    }

    private static final long serialVersionUID = 2467192664312928457L;


    /**
     * Handles BusinessValidationException - added multiple values as ONE Attribute
     *
     * @param enumCode
     * @param valueNames
     * @throws BusinessValidationException
     */
    public static void handleBusinessValidationException(IMessageEnum enumCode, String... valueNames) throws BusinessValidationException {
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
