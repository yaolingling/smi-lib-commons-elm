/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.messaging;

public interface IMessageReader {
    String getMessage(String msgCode);


    String getMessage(String msgCode, Object[] attributes);


    void setTag(Object tag);
}
