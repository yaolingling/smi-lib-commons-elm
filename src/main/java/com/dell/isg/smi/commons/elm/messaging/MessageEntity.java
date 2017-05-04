/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.messaging;

import java.io.Serializable;
import java.util.Date;

import com.dell.isg.smi.commons.elm.model.MessagePartEnum;

/**
 *
 */
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private IMessageEnum messageEnum;

    private MessagePartEnum messagePartEnum;

    private String messageParams;

    private Date creationTime;

    private int groupOrder;

    private String messageGroup;


    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * @return the messageCode
     */
    public IMessageEnum getMessageEnum() {
        return messageEnum;
    }


    /**
     * @param messageCode the messageCode to set
     */
    public void setMessageEnum(IMessageEnum messageEnum) {
        this.messageEnum = messageEnum;
    }


    /**
     * @return messagePart
     */
    public MessagePartEnum getMessagePartEnum() {
        return messagePartEnum;
    }


    /**
     * @param messagePartEnum
     */
    public void setMessagePartEnum(MessagePartEnum messagePartEnum) {
        this.messagePartEnum = messagePartEnum;
    }


    /**
     * @return the messageParams
     */
    public String getMessageParams() {
        return messageParams;
    }


    /**
     * @param messageParams the messageParams to set
     */
    public void setMessageParams(String messageParams) {
        this.messageParams = messageParams;
    }


    /**
     * @return the creationTime
     */
    public Date getCreationTime() {
        return creationTime;
    }


    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


    /**
     * @return the groupOrder
     */
    public int getGroupOrder() {
        return groupOrder;
    }


    /**
     * @param groupOrder the groupOrder to set
     */
    public void setGroupOrder(int groupOrder) {
        this.groupOrder = groupOrder;
    }


    /**
     * @return the messageGroup
     */
    public String getMessageGroup() {
        return messageGroup;
    }


    /**
     * @param messageGroup the messageGroup to set
     */
    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }
}
