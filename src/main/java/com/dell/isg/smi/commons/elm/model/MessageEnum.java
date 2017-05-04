/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.model;

import java.util.HashMap;
import java.util.Map;

import com.dell.isg.smi.commons.elm.messaging.IMessageEnum;

public enum MessageEnum implements IMessageEnum {
    TEST("TEST", 400001);
    private static final Map<Integer, MessageEnum> intEnumMap = new HashMap<Integer, MessageEnum>();
    static {
        for (MessageEnum enumItem : MessageEnum.values()) {
            intEnumMap.put(enumItem.getId(), enumItem);
        }
    }
    private Integer id;
    private String value;


    private MessageEnum(String value, Integer id) {
        this.value = value;
        this.id = id;
    }


    public static MessageEnum getById(int id) {
        MessageEnum enumItem = intEnumMap.get(Integer.valueOf(id));
        return enumItem;
    }


    public String getValue() {
        return this.value;
    }


    public Integer getId() {
        return this.id;
    }


    public String toString() {
        return this.value;
    }
}
