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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapter around resource bundle to add functionality such using the Same as a StreamReader around Stream. Adapter Pattern
 *
 */
public class ResourceBundleReader {
    private ResourceBundle bundle;
    private static final Logger logger = LoggerFactory.getLogger(ResourceBundleReader.class);


    /**
     * Instantiates a new resource bundle reader.
     *
     * @param bundle the bundle
     */
    public ResourceBundleReader(ResourceBundle bundle) {
        this.bundle = bundle;
    }


    /**
     * Instantiates a new resource bundle reader.
     *
     * @param locale the locale
     * @param bundle the bundle
     */
    public ResourceBundleReader(Locale locale, String bundle) {

        this.bundle = ResourceBundle.getBundle(bundle, locale);

    }


    /**
     * Instantiates a new resource bundle reader.
     *
     * @param bundle the bundle
     */
    public ResourceBundleReader(String bundle) {

        Locale locale = new Locale("en");

        this.bundle = ResourceBundle.getBundle(bundle, locale);

    }


    /**
     * Gets the string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
        String tmpResult = bundle.getString(key);
        if (tmpResult != null) {
            try {
                // added to handle unicode strings
                tmpResult = new String(tmpResult.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("Error converting to UTF8", e);
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
     * @param key the key
     * @param arguments the arguments
     * @return String - formatted String
     */
    public String getString(String key, Object[] arguments) {
        String tmpResult = bundle.getString(key);
        if (tmpResult != null) {
            try {
                // added to handle unicode strings
                tmpResult = new String(tmpResult.getBytes("ISO-8859-1"), "UTF-8");
                tmpResult = StringUtils.replace(tmpResult, "'", "''");
            } catch (UnsupportedEncodingException e) {
                logger.debug("Error converting to UTF8", e);
                return null;
            }
        }
        return MessageFormat.format(tmpResult, arguments);
    }
}
