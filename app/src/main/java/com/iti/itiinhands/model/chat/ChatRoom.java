package com.iti.itiinhands.model.chat;

import java.io.Serializable;

/**
 * Created by home on 5/22/2017.
 */

public class ChatRoom implements Serializable{

    private String senderId;
    private String receiverId;

    private String receiverName;
    private String senderName;

    private String receiverImagePath;
    private String receiverType;

    private String roomKey;
    private boolean hasPendingMessages;


    public ChatRoom() {
    }

    public ChatRoom(String senderId,  String receiverName, String receiverId, String roomKey) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.roomKey = roomKey;
        this.receiverName = receiverName;
    }

    public String getReceiverImagePath() {
        return receiverImagePath;
    }

    public void setReceiverImagePath(String receiverImagePath) {
        this.receiverImagePath = receiverImagePath;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public boolean isHasPendingMessages() {
        return hasPendingMessages;
    }

    public void setHasPendingMessages(boolean hasPendingMessages) {
        this.hasPendingMessages = hasPendingMessages;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }


}
