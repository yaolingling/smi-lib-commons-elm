/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.bundle;

import java.util.List;

/**
 *
 * Attribute interface for exception
 *
 *
 */
public interface Attributes {
    /**
     * Add Attribute.. package scope
     *
     * @param name
     * @param value
     */
    Attributes addAttribute(String value);


    /**
     * Used for passing use case specific details.
     */
    Object[] getAttributes();


    List<String> getAttributesList();
}
