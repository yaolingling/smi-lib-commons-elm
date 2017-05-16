/**
 * Copyright � 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.03.04 at 01:18:28 PM CST
//

package com.dell.isg.smi.commons.elm.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class LocalizedMessage.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocalizedMessage", propOrder = { "messageEntityList", "messageGroup" })
public class LocalizedMessage {

    @XmlElement(name = "MessageEntityList")
    protected List<MessageEntity> messageEntityList;
    @XmlElement(name = "MessageGroup")
    protected String messageGroup;


    /**
     * Gets the value of the messageEntityList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageEntityList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMessageEntityList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link MessageEntity }
     *
     * @return the message entity list
     */
    public List<MessageEntity> getMessageEntityList() {
        if (messageEntityList == null) {
            messageEntityList = new ArrayList<MessageEntity>();
        }
        return this.messageEntityList;
    }


    /**
     * Gets the value of the messageGroup property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMessageGroup() {
        return messageGroup;
    }


    /**
     * Sets the value of the messageGroup property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMessageGroup(String value) {
        this.messageGroup = value;
    }

}