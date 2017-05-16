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
 * The Class MessageEntity.
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
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }


    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Gets the message enum.
     *
     * @return the messageCode
     */
    public IMessageEnum getMessageEnum() {
        return messageEnum;
    }


    /**
     * Sets the message enum.
     *
     * @param messageEnum the new message enum
     */
    public void setMessageEnum(IMessageEnum messageEnum) {
        this.messageEnum = messageEnum;
    }


    /**
     * Gets the message part enum.
     *
     * @return messagePart
     */
    public MessagePartEnum getMessagePartEnum() {
        return messagePartEnum;
    }


    /**
     * Sets the message part enum.
     *
     * @param messagePartEnum the new message part enum
     */
    public void setMessagePartEnum(MessagePartEnum messagePartEnum) {
        this.messagePartEnum = messagePartEnum;
    }


    /**
     * Gets the message params.
     *
     * @return the messageParams
     */
    public String getMessageParams() {
        return messageParams;
    }


    /**
     * Sets the message params.
     *
     * @param messageParams the messageParams to set
     */
    public void setMessageParams(String messageParams) {
        this.messageParams = messageParams;
    }


    /**
     * Gets the creation time.
     *
     * @return the creationTime
     */
    public Date getCreationTime() {
        return creationTime;
    }


    /**
     * Sets the creation time.
     *
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


    /**
     * Gets the group order.
     *
     * @return the groupOrder
     */
    public int getGroupOrder() {
        return groupOrder;
    }


    /**
     * Sets the group order.
     *
     * @param groupOrder the groupOrder to set
     */
    public void setGroupOrder(int groupOrder) {
        this.groupOrder = groupOrder;
    }


    /**
     * Gets the message group.
     *
     * @return the messageGroup
     */
    public String getMessageGroup() {
        return messageGroup;
    }


    /**
     * Sets the message group.
     *
     * @param messageGroup the messageGroup to set
     */
    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }
}
