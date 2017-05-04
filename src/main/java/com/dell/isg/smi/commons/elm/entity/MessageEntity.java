/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.dell.isg.smi.commons.elm.TableConstants;

/**
 *
 */
@Entity
@Table(name = TableConstants.T_LOCALIZED_MESSAGES)
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = TableConstants.SEQ_LOCALIZED_MESSAGES, sequenceName = TableConstants.SEQ_LOCALIZED_MESSAGES)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConstants.SEQ_LOCALIZED_MESSAGES)
    @Column(name = TableConstants.LOCALIZED_MESSAGES_ID, columnDefinition = TableConstants.LOCALIZED_MESSAGES_ID, insertable = true, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = TableConstants.LOCALIZED_MESSAGES_CODE, length = 256)
    private String messageCode;

    @Column(name = TableConstants.LOCALIZED_MESSAGES_PARAMS, length = 5120)
    private String messageParams;

    @Column(name = TableConstants.LOCALIZED_MESSAGES_CREATION_TIME)
    private Date creationTime;

    @Column(name = TableConstants.LOCALIZED_MESSAGES_GROUP_ORDER)
    private int groupOrder;

    @Column(name = TableConstants.LOCALIZED_MESSAGES_GROUP, length = 256)
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
    public String getMessageCode() {
        return messageCode;
    }


    /**
     * @param messageCode the messageCode to set
     */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
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
