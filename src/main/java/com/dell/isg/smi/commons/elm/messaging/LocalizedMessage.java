/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.context.i18n.LocaleContextHolder;

import com.dell.isg.smi.commons.elm.model.MessagePartEnum;
import com.dell.isg.smi.commons.elm.utilities.DateTimeUtils;

/**
 * The Class LocalizedMessage.
 */
public class LocalizedMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<MessageEntity> messageEntityList = new ArrayList<>();
    private String messageGroup = null;
    private final static String delimiter = "||";
    private final static String escapedDelimiter = "\\|\\|";


    /**
     * Gets the message entity list.
     *
     * @return the localizedMessagesEntityList
     */
    public List<MessageEntity> getMessageEntityList() {
        return this.messageEntityList;
    }


    /**
     * Sets the localized messages.
     *
     * @param messageEntityList the new localized messages
     */
    public void setLocalizedMessages(List<MessageEntity> messageEntityList) {
        this.messageEntityList = messageEntityList;
    }


    /**
     * Gets the message group.
     *
     * @return the messageGroup
     */
    public String getMessageGroup() {
        return this.messageGroup;
    }


    /**
     * Sets the message group.
     *
     * @param messageGroup the messageGroup to set
     */
    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }


    /**
     * This default constructor doesn't create any Localized Message.
     */
    public LocalizedMessage() {
        // just used to new up an empty object
    }


    /**
     * This constructor creates a Localized Message with message parameters.
     *
     * @param messageEnum the message enum
     */
    public LocalizedMessage(IMessageEnum messageEnum) {
        this.createMessage(messageEnum, MessagePartEnum._MSG);
    }


    /**
     * This constructor creates a Localized Message with provided MessagePartEnum.
     *
     * @param messageEnum the message enum
     * @param messagePart the message part
     */
    public LocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePart) {
        this.createMessage(messageEnum, messagePart);
    }


    /**
     * This constructor creates a Localized Message with provided message parameters.
     *
     * @param messageEnum the message enum
     * @param params the params
     */
    public LocalizedMessage(IMessageEnum messageEnum, String... params) {
        this.createMessage(messageEnum, MessagePartEnum._MSG, params);
    }


    /**
     * This constructor creates a Localized Message with provided message parameters.
     *
     * @param messageEnum the message enum
     * @param messagePart the message part
     * @param params the params
     */
    public LocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePart, String... params) {
        this.createMessage(messageEnum, messagePart, params);
    }


    /**
     * Gets the locale off of request's ThreadLocal using the locale helper and calls getLocalizedMessageString.
     *
     * @return the string
     */
    @Override
    public String toString() {
        Locale locale = LocaleContextHolder.getLocale();
        return getLocalizedMessageString(locale);
    }


    /**
     * This method appends the provided LocalizedMessage object into the parent object.
     *
     * @param localizedMessage the localized message
     */
    public void appendLocalizedMessage(LocalizedMessage localizedMessage) {
        if (localizedMessage != null) {
            if (localizedMessage.messageEntityList != null && !localizedMessage.messageEntityList.isEmpty()) {
                if (this.getMessageGroup() == null || this.getMessageGroup().trim().isEmpty()) {
                    this.setMessageGroup(localizedMessage.messageEntityList.get(0).getMessageGroup());
                    this.messageEntityList = localizedMessage.messageEntityList;
                } else {
                    if (this.messageEntityList == null || this.messageEntityList.isEmpty()) {
                        this.messageEntityList = new ArrayList<>();
                        for (MessageEntity messageDAOEntity : localizedMessage.messageEntityList) {
                            messageDAOEntity.setMessageGroup(this.getMessageGroup());
                            this.messageEntityList.add(messageDAOEntity);
                        }
                    } else {
                        this.assignMaxGroupOrdersAndMerge(localizedMessage.getMessageEntityList(), this.findMaxStackDepth(this.getMessageEntityList()));
                    }
                }
            }
        }
    }


    /**
     * This method prepends the provided LocalizedMessage object into the parent object.
     *
     * @param localizedMessage the localized message
     */
    public void prependLocalizedMessage(LocalizedMessage localizedMessage) {
        if (localizedMessage != null) {
            if (localizedMessage.messageEntityList != null && !localizedMessage.messageEntityList.isEmpty()) {
                if (this.getMessageGroup() == null || this.getMessageGroup().trim().isEmpty()) {
                    this.setMessageGroup(localizedMessage.messageEntityList.get(0).getMessageGroup());
                    this.messageEntityList = localizedMessage.messageEntityList;
                } else {
                    if (this.messageEntityList == null || this.messageEntityList.isEmpty()) {
                        this.messageEntityList = new ArrayList<>();
                        for (MessageEntity messageDAOEntity : localizedMessage.messageEntityList) {
                            messageDAOEntity.setMessageGroup(this.getMessageGroup());
                            this.messageEntityList.add(messageDAOEntity);
                        }
                    } else {
                        this.assignMinGroupOrdersAndMerge(localizedMessage.getMessageEntityList(), this.findMinStackDepth(this.getMessageEntityList()));
                    }
                }
            }
        }
    }


    /**
     * This method appends a message and allocates the stack depth.
     *
     * @param messageEnum the message enum
     * @param messagePartEnum the message part enum
     * @param params the params
     */
    public void appendLocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        MessageEntity lm = this.getLocalizedMessageEntityWithoutStackDepth(messageEnum, messagePartEnum, params);
        lm.setGroupOrder(this.findMaxStackDepth(this.getMessageEntityList()));
        this.messageEntityList.add(lm);
    }


    /**
     * This method prepends a message and allocates the stack depth.
     *
     * @param messageEnum the message enum
     * @param messagePartEnum the message part enum
     * @param params the params
     */
    public void prependLocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        MessageEntity lm = this.getLocalizedMessageEntityWithoutStackDepth(messageEnum, messagePartEnum, params);
        lm.setGroupOrder(this.findMinStackDepth(this.getMessageEntityList()));
        this.messageEntityList.add(lm);
    }


    /**
     * This method returns the localized message string according to the locale and message group ID.
     *
     * @param locale the locale
     * @return the localized message string
     */
    public String getLocalizedMessageString(Locale locale) {
        String localizedMessage = "";
        if (this.getMessageEntityList() == null || this.getMessageEntityList().isEmpty()) {
            return localizedMessage;
        }

        MessageEntity[] messages = this.sortLocalizedMessages(this.getMessageEntityList());
        if (messages != null) {
            for (int i = 0; i < messages.length; i++) {
                IMessageEnum messageEnum = messages[i].getMessageEnum();
                if (messageEnum == null) {
                	return localizedMessage;
                }
                String code = messageEnum.getId() + messages[i].getMessagePartEnum().name();
                IMessageReader reader = MessageReaderFactory.createReader(code.trim(), locale);
                localizedMessage += this.getMessage(reader, locale, code.trim(), messages[i].getMessageParams()) + " ";
            }

            if (localizedMessage.charAt(localizedMessage.length() - 1) == ' ') {
                localizedMessage = localizedMessage.substring(0, localizedMessage.length() - 1);
            }
        }

        return localizedMessage;
    }


    /**
     * This method returns the localized message string according to the locale and message group ID.
     *
     * @param locale the locale
     * @return the localized message string with date
     */
    public String getLocalizedMessageStringWithDate(Locale locale) {
        String localizedMessage = "";
        if (this.getMessageEntityList() == null || this.getMessageEntityList().isEmpty()) {
            return localizedMessage;
        }

        MessageEntity[] messages = this.sortLocalizedMessages(this.getMessageEntityList());
        if (messages != null) {
            for (int i = 0; i < messages.length; i++) {
                IMessageEnum messageEnum = messages[i].getMessageEnum();
                String code = messageEnum.getValue();
                if (code == null) {
                    code = "";
                }
                IMessageReader reader = MessageReaderFactory.createReader(code.trim(), locale);
                if ("ARGUMENT_MSG_ONE".equalsIgnoreCase(code)) {
                    // this is needed for data prior to 1.5.1
                    localizedMessage += this.getMessage(reader, locale, code.trim(), messages[i].getMessageParams()) + "\n";
                } else {
                    localizedMessage += getFormattedDate(messages[i].getCreationTime()) + ": " + this.getMessage(reader, locale, code.trim(), messages[i].getMessageParams()) + "\n";
                }
            }
        }

        return localizedMessage;
    }


    /**
     * This method returns the formatted date string.
     *
     * @param date the date
     * @return the formatted date
     */
    private String getFormattedDate(Date date) {
        return "|" + DateTimeUtils.getUtcDate(date) + "|";
    }


    /**
     * This method sorts the provided list of localized messages according to the group order.
     *
     * @param messageEntityList the message entity list
     * @return the message entity[]
     */
    private MessageEntity[] sortLocalizedMessages(List<MessageEntity> messageEntityList) {
        MessageEntity[] lcList = null;
        if (messageEntityList != null && !messageEntityList.isEmpty()) {
            lcList = messageEntityList.toArray(new MessageEntity[messageEntityList.size()]);
            for (int i = 0; i < lcList.length; i++) {
                for (int j = i + 1; j < lcList.length; j++) {
                    if (lcList[i].getGroupOrder() > lcList[j].getGroupOrder()) {
                        MessageEntity tmp = lcList[i];
                        lcList[i] = lcList[j];
                        lcList[j] = tmp;
                    }
                }
            }
        }

        return lcList;
    }


    /**
     * This method assigns the new group orders to the child group according to the parent group and merge into parent.
     *
     * @param localizedMessages the localized messages
     * @param maxGroupOrder the max group order
     */
    private void assignMaxGroupOrdersAndMerge(List<MessageEntity> localizedMessages, int maxGroupOrder) {
        if (localizedMessages != null && !localizedMessages.isEmpty()) {
            int groupOrder = maxGroupOrder;
            MessageEntity[] arr = this.sortLocalizedMessages(localizedMessages);
            if(null != arr){
                for (int i = 0; i < arr.length; i++) {
                    arr[i].setGroupOrder(groupOrder);
                    arr[i].setMessageGroup(this.getMessageGroup());
                    this.messageEntityList.add(arr[i]);
                    groupOrder++;
                }
            }
        }
    }


    /**
     * This method assigns the new group orders to the child group according to the parent group and merge into parent.
     *
     * @param localizedMessages the localized messages
     * @param minGroupOrder the min group order
     */
    private void assignMinGroupOrdersAndMerge(List<MessageEntity> localizedMessages, int minGroupOrder) {
        if (localizedMessages != null && !localizedMessages.isEmpty()) {
            int groupOrder = minGroupOrder;
            MessageEntity[] arr = this.sortLocalizedMessages(localizedMessages);
            if(null != arr){
                for (int i = arr.length - 1; i >= 0; i--) {
                    arr[i].setGroupOrder(groupOrder);
                    arr[i].setMessageGroup(this.getMessageGroup());
                    this.messageEntityList.add(arr[i]);
                    groupOrder--;
                }
            }
        }
    }


    /**
     * This method creates the localized message entity and assigns the stack depth automatically. This method is for the records which doesn't have any GUID present in DB.
     *
     * @param messageEnum the message enum
     * @param messagePartEnum the message part enum
     * @param params the params
     * @return message GUID
     */
    private void createMessage(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        if (this.getMessageGroup() == null || this.getMessageGroup().trim().isEmpty()) {
            this.setMessageGroup(UUID.randomUUID().toString());
        }

        MessageEntity lm = this.getLocalizedMessageEntityWithoutStackDepth(messageEnum, messagePartEnum, params);
        lm.setGroupOrder(this.getMessageEntityList().size() + 1);
        this.getMessageEntityList().add(lm);
    }


    /**
     * This method builds the localized message object without stack depth.
     *
     * @param messageEnum the message enum
     * @param messagePartEnum the message part enum
     * @param params the params
     * @return the localized message entity without stack depth
     */
    private MessageEntity getLocalizedMessageEntityWithoutStackDepth(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        String messageParams = "";
        if (params != null && params.length > 0) {
            StringBuilder builder = new StringBuilder(messageParams);
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null && !params[i].trim().isEmpty()) {
                    builder.append(params[i].trim()).append(delimiter);
                }
            }
            if (builder.length() >= 2) {
                builder.setLength(builder.length() - 2);
            }
            messageParams = builder.toString();
        }
        return createMessageEntity(messageEnum, messagePartEnum, messageParams);
    }


    /**
     * This method creates the messageEntity with provided parameters.
     *
     * @param messageEnum the message enum
     * @param messagePartEnum the message part enum
     * @param messageParams the message params
     * @return the message entity
     */
    private MessageEntity createMessageEntity(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String messageParams) {
        MessageEntity lm = new MessageEntity();
        lm.setMessageGroup(this.getMessageGroup());
        lm.setCreationTime(new Date());
        lm.setMessageEnum(messageEnum);
        lm.setMessagePartEnum(messagePartEnum);
        lm.setMessageParams(messageParams);
        return lm;
    }


    /**
     * This method returns the stack depth for the append message.
     *
     * @param existingMessage the existing messages
     * @return the int
     */
    private int findMaxStackDepth(List<MessageEntity> existingMessage) {
        int stackDepth = 1;
        if (existingMessage != null && !existingMessage.isEmpty()) {
            for (MessageEntity lm : existingMessage) {
                if (lm.getGroupOrder() > stackDepth) {
                    stackDepth = lm.getGroupOrder();
                }
            }
            stackDepth += 1;
        }
        return stackDepth;
    }


    /**
     * This method returns the stack depth for the prepend message.
     *
     * @param existingMessage the existing messages
     * @return the int
     */
    private int findMinStackDepth(List<MessageEntity> existingMessage) {
        int stackDepth = 1;
        if (existingMessage != null && !existingMessage.isEmpty()) {
            for (MessageEntity lm : existingMessage) {
                if (stackDepth > lm.getGroupOrder()) {
                    stackDepth = lm.getGroupOrder();
                }
            }
            stackDepth -= 1;
        }
        return stackDepth;
    }


    /**
     * This method generates the localized message from the message code and message parameters.
     *
     * @param reader the reader
     * @param locale the locale
     * @param messageCode the message code
     * @param params the params
     * @return the message
     */
    private String getMessage(IMessageReader reader, Locale locale, String messageCode, String params) {
        try {
            if (params == null || params.trim().isEmpty()) {
                return reader.getMessage(messageCode);
            } else {
                String[] paramList = params.split(escapedDelimiter);
                return reader.getMessage(messageCode, paramList);
            }
        } catch (Exception e) {
            return messageCode;
        }
    }
}
