package com.iti.itiinhands.model.chat;

import java.util.Date;

/**
 * Created by home on 5/22/2017.
 */

public class ChatMessage {

    private String message;
    private String senderId;
    private String receiverId;
    private String receiverName;
    private long date;
    private String senderName;
    private String offline;


    public ChatMessage() {
    }

    public ChatMessage(String message, String senderId, String receiverId, String senderName) {
        this.message = message;
        this.senderId = senderId;
        this.date = new Date().getTime();
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.offline = offline;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public long getDate() {
        return date;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

}
