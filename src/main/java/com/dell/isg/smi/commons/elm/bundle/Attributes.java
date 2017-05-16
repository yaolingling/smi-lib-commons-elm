/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.bundle;

import java.util.List;

/**
 * Attribute interface for exception.
 */
public interface Attributes {

    
    /**
     * Adds the attribute.
     *
     * @param value the value
     * @return the attributes
     */
    Attributes addAttribute(String value);


    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    Object[] getAttributes();


    /**
     * Gets the attributes list.
     *
     * @return the attributes list
     */
    List<String> getAttributesList();
}
