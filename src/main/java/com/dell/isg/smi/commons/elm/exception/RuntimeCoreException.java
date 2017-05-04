/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.exception;

import java.util.List;

import com.dell.isg.smi.commons.elm.bundle.Attributes;
import com.dell.isg.smi.commons.elm.messaging.IMessageEnum;
import com.dell.isg.smi.commons.elm.messaging.LocalizedMessage;
import com.dell.isg.smi.commons.elm.model.MessagePartEnum;

/**
 *
 * This Runtime Exception Base class is an adapter around the BaseException This class can be subclasses by Core Runtime exception subtypes. Notice it inherits from
 * RuntimeException
 *
 */
public class RuntimeCoreException extends RuntimeException implements CoreException {
    private static final long serialVersionUID = 1L;

    BaseException baseException = new BaseException();


    /**
     * default constructor
     */
    public RuntimeCoreException() {

    }


    public RuntimeCoreException(long code) {
        // Implement ME
    }


    /**
     * Constructor
     *
     * @param e
     */
    public RuntimeCoreException(Throwable e) {
        super(e);
    }


    /**
     * Constructor
     *
     * @param msg
     */
    public RuntimeCoreException(String msg) {
        super(msg);
    }


    /**
     * constructor
     *
     * @param msg
     * @param e
     */
    public RuntimeCoreException(String msg, Throwable e) {
        super(msg, e);
    }


    public RuntimeCoreException(IMessageEnum errorCode) {
        this.setErrorCode(errorCode);
        this.setErrorID(errorCode.getId());
    }


    @Override
    public IMessageEnum getErrorCode() {
        return baseException.errorCode;
    }


    @Override
    public void setErrorCode(IMessageEnum errorCode) {
        baseException.errorCode = errorCode;
        baseException.errorID = errorCode.getId();
    }


    @Override
    public Object[] getAttributes() {
        return baseException.getAttributes().toArray();
    }


    @Override
    public List<String> getAttributesList() {
        return baseException.getAttributes();
    }


    public int getErrorID() {
        return baseException.errorID;
    }


    @Override
    public Attributes addAttribute(String value) {
        baseException.addAttribute(value);
        return this;

    }


    public void setErrorID(int code) {
        baseException.errorID = code;
    }


    @Override
    public String getMessage() {
        LocalizedMessage localizedMessage = generateLocalizedMessage();
        return localizedMessage.toString();
    }

    // @Override
    // public String getMessage() {
    // if (super.getMessage() == null) {
    // return "";
    // } else {
    // return super.getMessage();
    // }
    // }


    public String generateExceptionMessageCode() {
        return (this.getErrorID() + MessagePartEnum._MSG.name());
    }


    public String generateExceptionDetailCode() {
        return (this.getErrorID() + MessagePartEnum._DTL.name());
    }


    public String generateExceptionActionCode() {
        return (this.getErrorID() + MessagePartEnum._ACT.name());
    }


    public String generateExceptionSeverityCode() {
        return (this.getErrorID() + MessagePartEnum._SEV.name());
    }


    public LocalizedMessage generateLocalizedMessage() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        LocalizedMessage msg = new LocalizedMessage(this.getErrorCode(), MessagePartEnum._MSG, params);
        return msg;
    }


    public LocalizedMessage generateLocalizedDetail() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        LocalizedMessage msg = new LocalizedMessage(this.getErrorCode(), MessagePartEnum._DTL, params);
        return msg;
    }


    public LocalizedMessage generateLocalizedAction() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        LocalizedMessage msg = new LocalizedMessage(this.getErrorCode(), MessagePartEnum._ACT, params);
        return msg;
    }


    public LocalizedMessage generateLocalizedSeverity() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        LocalizedMessage msg = new LocalizedMessage(this.getErrorCode(), MessagePartEnum._SEV, params);
        return msg;
    }


    /**
     * Handles RuntimeException - added multiple values as ONE Attribute.
     *
     * @param enumCode
     * @param ex
     * @param valueNames
     * @throws RuntimeCoreException
     */
    public static void handleRuntimeCoreException(IMessageEnum errorCode, Throwable ex, String... valueNames) throws RuntimeCoreException {
        RuntimeCoreException exception = new RuntimeCoreException(ex);
        StringBuilder valArgs = new StringBuilder();
        exception.setErrorCode(errorCode);
        exception.setErrorID(errorCode.getId());
        for (String vName : valueNames) {
            valArgs.append(" ");
            valArgs.append(vName);
        }
        exception.addAttribute(valArgs.toString());
        throw exception;
    }


    /**
     * Handles RuntimeException - added multiple values as ONE Attribute
     *
     * @param enumCode
     * @param valueNames
     * @throws RuntimeCoreException
     */
    public static void handleRuntimeCoreException(IMessageEnum enumCode, String... valueNames) throws RuntimeCoreException {
        RuntimeCoreException exception = new RuntimeCoreException();
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


    public void setLCErrorCode(String errorCode) {
        // Implement this code
    }


    public void setLCErrorStr(String errorStr) {
        // Implement this code
    }
}
