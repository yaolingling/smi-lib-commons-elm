/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.exception;

import com.dell.isg.smi.commons.elm.bundle.Attributes;
import com.dell.isg.smi.commons.elm.messaging.IMessageEnum;

/**
 * Interface used by Core Exceptions to allow for passing of attributes to Error Bundle Utility.
 *
 */
public interface CoreException extends Attributes {
    /**
     * ErrorID for given error.
     *
     * @return int
     */
    IMessageEnum getErrorCode();


    void setErrorCode(IMessageEnum errorCode);
}
