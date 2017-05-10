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

// TODO: Auto-generated Javadoc
/**
 *
 * This Runtime Exception Base class is an adapter around the BaseException This class can be subclasses by Core Runtime exception subtypes. Notice it inherits from
 * RuntimeException
 *
 */
public class RuntimeCoreException extends RuntimeException implements CoreException {
    private static final long serialVersionUID = 1L;

    protected final BaseException baseException = new BaseException();


    /**
     * default constructor
     */
    public RuntimeCoreException() {
        // allows creating an empty exception
    }


    /**
     * Instantiates a new runtime core exception.
     *
     * @param code the code
     */
    public RuntimeCoreException(long code) {
        // Implement ME
    }


    /**
     * Instantiates a new runtime core exception.
     *
     * @param e the e
     */
    public RuntimeCoreException(Throwable e) {
        super(e);
    }


    /**
     * Instantiates a new runtime core exception.
     *
     * @param msg the msg
     */
    public RuntimeCoreException(String msg) {
        super(msg);
    }


    /**
     * Instantiates a new runtime core exception.
     *
     * @param msg the msg
     * @param e the e
     */
    public RuntimeCoreException(String msg, Throwable e) {
        super(msg, e);
    }


    /**
     * Instantiates a new runtime core exception.
     *
     * @param errorCode the error code
     */
    public RuntimeCoreException(IMessageEnum errorCode) {
        this.setErrorCode(errorCode);
        this.setErrorID(errorCode.getId());
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.commons.elm.exception.CoreException#getErrorCode()
     */
    @Override
    public IMessageEnum getErrorCode() {
        return baseException.errorCode;
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.commons.elm.exception.CoreException#setErrorCode(com.dell.isg.smi.commons.elm.messaging.IMessageEnum)
     */
    @Override
    public void setErrorCode(IMessageEnum errorCode) {
        baseException.errorCode = errorCode;
        baseException.errorID = errorCode.getId();
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.commons.elm.bundle.Attributes#getAttributes()
     */
    @Override
    public Object[] getAttributes() {
        return baseException.getAttributes().toArray();
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.commons.elm.bundle.Attributes#getAttributesList()
     */
    @Override
    public List<String> getAttributesList() {
        return baseException.getAttributes();
    }


    /**
     * Gets the error ID.
     *
     * @return the error ID
     */
    public int getErrorID() {
        return baseException.errorID;
    }


    @Override
    public Attributes addAttribute(String value) {
        baseException.addAttribute(value);
        return this;

    }


    /**
     * Sets the error ID.
     *
     * @param code the new error ID
     */
    public void setErrorID(int code) {
        baseException.errorID = code;
    }


    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
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


    /**
     * Generate exception message code.
     *
     * @return the string
     */
    public String generateExceptionMessageCode() {
        return (this.getErrorID() + MessagePartEnum._MSG.name());
    }


    /**
     * Generate exception detail code.
     *
     * @return the string
     */
    public String generateExceptionDetailCode() {
        return (this.getErrorID() + MessagePartEnum._DTL.name());
    }


    /**
     * Generate exception action code.
     *
     * @return the string
     */
    public String generateExceptionActionCode() {
        return (this.getErrorID() + MessagePartEnum._ACT.name());
    }


    /**
     * Generate exception severity code.
     *
     * @return the string
     */
    public String generateExceptionSeverityCode() {
        return (this.getErrorID() + MessagePartEnum._SEV.name());
    }


    /**
     * Generate localized message.
     *
     * @return the localized message
     */
    public LocalizedMessage generateLocalizedMessage() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        return new LocalizedMessage(this.getErrorCode(), MessagePartEnum._MSG, params);
    }


    /**
     * Generate localized detail.
     *
     * @return the localized message
     */
    public LocalizedMessage generateLocalizedDetail() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        return new LocalizedMessage(this.getErrorCode(), MessagePartEnum._DTL, params);
    }


    /**
     * Generate localized action.
     *
     * @return the localized message
     */
    public LocalizedMessage generateLocalizedAction() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        return new LocalizedMessage(this.getErrorCode(), MessagePartEnum._ACT, params);
    }


    /**
     * Generate localized severity.
     *
     * @return the localized message
     */
    public LocalizedMessage generateLocalizedSeverity() {
        String[] params = new String[] {};
        if (this.getAttributesList() != null) {
            params = this.getAttributesList().toArray(new String[this.getAttributesList().size()]);
        }
        return new LocalizedMessage(this.getErrorCode(), MessagePartEnum._SEV, params);
    }


    /**
     * Handle runtime core exception.
     *
     * @param errorCode the error code
     * @param ex the ex
     * @param valueNames the value names
     * @throws RuntimeCoreException the runtime core exception
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
     * Handle runtime core exception.
     *
     * @param enumCode the enum code
     * @param valueNames the value names
     * @throws RuntimeCoreException the runtime core exception
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


    /**
     * Sets the LC error code.
     *
     * @param errorCode the new LC error code
     */
    public void setLCErrorCode(String errorCode) {
        // Implement this code
    }


    /**
     * Sets the LC error str.
     *
     * @param errorStr the new LC error str
     */
    public void setLCErrorStr(String errorStr) {
        // Implement this code
    }
}
