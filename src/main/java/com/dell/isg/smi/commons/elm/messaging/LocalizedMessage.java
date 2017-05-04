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
 *
 */
public class LocalizedMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<MessageEntity> messageEntityList = new ArrayList<MessageEntity>();
    private String messageGroup = null;

    private final String delimiter = "||";
    private final String escapedDelimiter = "\\|\\|";


    /**
     * @return the localizedMessagesEntityList
     */
    public List<MessageEntity> getMessageEntityList() {
        return this.messageEntityList;
    }


    /**
     * @param localizedMessagesEntityList the localizedMessagesEntityList to set
     */
    public void setLocalizedMessages(List<MessageEntity> messageEntityList) {
        this.messageEntityList = messageEntityList;
    }


    /**
     * @return the messageGroup
     */
    public String getMessageGroup() {
        return this.messageGroup;
    }


    /**
     * @param messageGroup the messageGroup to set
     */
    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }


    /**
     * This default constructor doesn't create any Localized Message.
     */
    public LocalizedMessage() {

    }


    /**
     * This constructor creates a Localized Message with message parameters.
     *
     * @param messageCode
     */
    public LocalizedMessage(IMessageEnum messageEnum) {
        this.createMessage(messageEnum, MessagePartEnum._MSG);
    }


    /**
     * This constructor creates a Localized Message with provided MessagePartEnum
     *
     * @param messageCode
     */
    public LocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePart) {
        this.createMessage(messageEnum, messagePart);
    }


    /**
     * This constructor creates a Localized Message with provided message parameters.
     *
     * @param messageCode
     */
    public LocalizedMessage(IMessageEnum messageEnum, String... params) {
        this.createMessage(messageEnum, MessagePartEnum._MSG, params);
    }


    /**
     * This constructor creates a Localized Message with provided message parameters.
     *
     * @param messageCode
     */
    public LocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePart, String... params) {
        this.createMessage(messageEnum, messagePart, params);
    }


    /**
     * Gets the locale off of request's ThreadLocal using the locale helper and calls getLocalizedMessageString.
     *
     */
    @Override
    public String toString() {
        Locale locale = LocaleContextHolder.getLocale();
        return getLocalizedMessageString(locale);
    }


    /**
     * This method appends the provided LocalizedMessage object into the parent object.
     *
     * @param localizedMessage
     */
    public void appendLocalizedMessage(LocalizedMessage localizedMessage) {
        if (localizedMessage != null) {
            if (localizedMessage.messageEntityList != null && !localizedMessage.messageEntityList.isEmpty()) {
                if (this.getMessageGroup() == null || this.getMessageGroup().trim().isEmpty()) {
                    this.setMessageGroup(localizedMessage.messageEntityList.get(0).getMessageGroup());
                    this.messageEntityList = localizedMessage.messageEntityList;
                } else {
                    if (this.messageEntityList == null || this.messageEntityList.isEmpty()) {
                        this.messageEntityList = new ArrayList<MessageEntity>();
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
     * @param localizedMessage
     */
    public void prependLocalizedMessage(LocalizedMessage localizedMessage) {
        if (localizedMessage != null) {
            if (localizedMessage.messageEntityList != null && !localizedMessage.messageEntityList.isEmpty()) {
                if (this.getMessageGroup() == null || this.getMessageGroup().trim().isEmpty()) {
                    this.setMessageGroup(localizedMessage.messageEntityList.get(0).getMessageGroup());
                    this.messageEntityList = localizedMessage.messageEntityList;
                } else {
                    if (this.messageEntityList == null || this.messageEntityList.isEmpty()) {
                        this.messageEntityList = new ArrayList<MessageEntity>();
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
     * @param log_level
     * @param msgCode
     * @param params
     */
    public void appendLocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        MessageEntity lm = this.getLocalizedMessageEntityWithoutStackDepth(messageEnum, messagePartEnum, params);
        lm.setGroupOrder(this.findMaxStackDepth(this.getMessageEntityList()));
        this.messageEntityList.add(lm);
    }


    /**
     * This method prepends a message and allocates the stack depth.
     *
     * @param log_level
     * @param msgCode
     * @param params
     */
    public void prependLocalizedMessage(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        MessageEntity lm = this.getLocalizedMessageEntityWithoutStackDepth(messageEnum, messagePartEnum, params);
        lm.setGroupOrder(this.findMinStackDepth(this.getMessageEntityList()));
        this.messageEntityList.add(lm);
    }


    /**
     * This method returns the localized message string according to the locale and message group ID.
     *
     * @param locale
     * @param messageGroup
     * @return
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
     * @param locale
     * @param messageGroup
     * @return
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
     * @param date
     * @return
     */
    private String getFormattedDate(Date date) {
        return "|" + DateTimeUtils.getUtcDate(date) + "|";
    }


    /**
     * This method sorts the provided list of localized messages according to the group order.
     *
     * @param messageEntityList
     * @return
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
     * This method assigns the new group orders to the child group according to the parent group and merge into parent
     *
     * @param localizedMessages
     * @param maxGroupOrder
     */
    private void assignMaxGroupOrdersAndMerge(List<MessageEntity> localizedMessages, int maxGroupOrder) {
        if (localizedMessages != null && !localizedMessages.isEmpty()) {
            MessageEntity[] arr = this.sortLocalizedMessages(localizedMessages);

            for (int i = 0; i < arr.length; i++) {
                arr[i].setGroupOrder(maxGroupOrder);
                arr[i].setMessageGroup(this.getMessageGroup());
                this.messageEntityList.add(arr[i]);
                maxGroupOrder++;
            }
        }
    }


    /**
     * This method assigns the new group orders to the child group according to the parent group and merge into parent
     *
     * @param localizedMessages
     * @param minGroupOrder
     */
    private void assignMinGroupOrdersAndMerge(List<MessageEntity> localizedMessages, int minGroupOrder) {
        if (localizedMessages != null && !localizedMessages.isEmpty()) {
            MessageEntity[] arr = this.sortLocalizedMessages(localizedMessages);

            for (int i = arr.length - 1; i >= 0; i--) {
                arr[i].setGroupOrder(minGroupOrder);
                arr[i].setMessageGroup(this.getMessageGroup());
                this.messageEntityList.add(arr[i]);
                minGroupOrder--;
            }
        }
    }


    /**
     * This method creates the localized message entity and assigns the stack depth automatically. This method is for the records which doesn't have any GUID present in DB.
     *
     * @param msgCode
     * @param params
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
     * @param msgCode
     * @param params
     * @return
     */
    private MessageEntity getLocalizedMessageEntityWithoutStackDepth(IMessageEnum messageEnum, MessagePartEnum messagePartEnum, String... params) {
        String messageParams = "";
        if (params != null && params.length > 0) {
            StringBuilder builder = new StringBuilder(messageParams);
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null && !params[i].trim().isEmpty()) {
                    builder.append(params[i].trim()).append(this.delimiter);
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
     * @param msgCode
     * @param messageParams
     * @return
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
     * @param existing_messages
     * @return
     */
    private int findMaxStackDepth(List<MessageEntity> existing_messages) {
        int stack_depth = 1;
        if (existing_messages != null && !existing_messages.isEmpty()) {
            for (MessageEntity lm : existing_messages) {
                if (lm.getGroupOrder() > stack_depth) {
                    stack_depth = lm.getGroupOrder();
                }
            }

            stack_depth += 1;
        }

        return stack_depth;
    }


    /**
     * This method returns the stack depth for the prepend message.
     *
     * @param existing_messages
     * @return
     */
    private int findMinStackDepth(List<MessageEntity> existing_messages) {
        int stack_depth = 1;
        if (existing_messages != null && !existing_messages.isEmpty()) {
            for (MessageEntity lm : existing_messages) {
                if (stack_depth > lm.getGroupOrder()) {
                    stack_depth = lm.getGroupOrder();
                }
            }

            stack_depth -= 1;
        }

        return stack_depth;
    }


    /**
     * This method generates the localized message from the message code and message parameters.
     *
     * @param reader
     * @param locale
     * @param messageCode
     * @param params
     * @return
     */
    private String getMessage(IMessageReader reader, Locale locale, String messageCode, String params) {
        try {
            if (params == null || params.trim().isEmpty()) {
                return reader.getMessage(messageCode);
            } else {
                String[] paramList = params.split(this.escapedDelimiter);
                return reader.getMessage(messageCode, paramList);
            }
        } catch (Exception e) {
            return messageCode;
        }
    }
}
