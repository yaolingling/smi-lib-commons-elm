/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.messaging;

import java.util.Locale;

/**
 * A factory for creating MessageReader objects.
 */
public class MessageReaderFactory {
    
    /**
     * Creates reader with the given locale.
     *
     * @param msgCode the msg code
     * @param locale - Locale to in initialize the reader with.
     * @return the i message reader
     */
    public static IMessageReader createReader(String msgCode, Locale locale) {
        MessageReaderType type = getReaderType(msgCode);
        switch (type) {
        case FriendlyLogBundleReader:
            return new FriendlyMessageBundleReader(locale);
        default:
            return null;
        }
    }


    // /**
    // * Creates reader with default locale.
    // * @param type - Reader type.
    // * @return
    // */
    // public static IMessageReader createReader(String msgCode){
    // MessageReaderType type = getReaderType(msgCode);
    // switch (type){
    // case FriendlyLogBundleReader:
    // return new FriendlyMessageBundleReader();
    // default:
    // return null;
    // }
    // }

    /**
     * Gets the reader type.
     *
     * @param msgCode the msg code
     * @return the reader type
     */
    private static MessageReaderType getReaderType(String msgCode) {
        return MessageReaderType.FriendlyLogBundleReader;
    }

}
