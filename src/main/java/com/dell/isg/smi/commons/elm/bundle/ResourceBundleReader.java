/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.bundle;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * Adapter around resource bundle to add functionality such using the Same as a StreamReader around Stream. Adapter Pattern
 *
 */
public class ResourceBundleReader {
    private ResourceBundle bundle;


    /**
     *
     * @param bundle
     */
    public ResourceBundleReader(ResourceBundle bundle) {
        this.bundle = bundle;
    }


    /**
     *
     * @param locale
     * @param bundle
     */
    public ResourceBundleReader(Locale locale, String bundle) {

        this.bundle = ResourceBundle.getBundle(bundle, locale);

    }


    public ResourceBundleReader(String bundle) {

        Locale locale = new Locale("en");

        this.bundle = ResourceBundle.getBundle(bundle, locale);

    }


    /**
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        String tmpResult = bundle.getString(key);
        if (tmpResult != null) {
            try {
                // added to handle unicode strings
                tmpResult = new String(tmpResult.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return tmpResult;
    }


    /**
     * Used to get string with arguments passed in .
     *
     * Example: Input: Object[] arguments = {new Integer(7), new Date(System.currentTimeMillis()), "a disturbance in the Force";
     *
     * ResourceBundle's key value is "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
     *
     * OutPut: At 12:30 PM on Jul 3, 2053, there was a disturbance in the Force on planet 7.
     *
     * @param key
     * @param arguments
     * @return String - formatted String
     *
     */
    public String getString(String key, Object[] arguments) {
        String tmpResult = bundle.getString(key);
        if (tmpResult != null) {
            try {
                // added to handle unicode strings
                tmpResult = new String(tmpResult.getBytes("ISO-8859-1"), "UTF-8");
                tmpResult = StringUtils.replace(tmpResult, "'", "''");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return MessageFormat.format(tmpResult, arguments);
    }
}
