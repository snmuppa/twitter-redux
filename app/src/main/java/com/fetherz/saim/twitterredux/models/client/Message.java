package com.fetherz.saim.twitterredux.models.client;

import com.fetherz.saim.twitterredux.utils.GenericUtil;

import org.parceler.Parcel;

/**
 * Created by sm032858 on 4/1/17.
 */

@Parcel(analyze={Message.class})
public class Message {

    public static class MessageBuilder {
        private long id;

        private String idString;

        private String createdAt;

        private String text;

        private User sender;


        public MessageBuilder(long id){
            this.id = id;
        }

        public MessageBuilder setIdString(String idString) {
            this.idString = idString;
            return this;
        }

        public MessageBuilder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MessageBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public MessageBuilder setSender(User sender) {
            this.sender = sender;
            return this;
        }

        public Message createMessage() {
            return new Message(id, idString, createdAt, text, sender);
        }
    }

    long id;

    String idString;

    String createdAt;

    String text;

    User sender;

    public Message() {}

    public Message(long id, String idString, String createdAt, String text, User sender) {
        this.id = id;
        this.idString = idString;
        this.createdAt = createdAt;
        this.text = text;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public String getIdString() {
        return idString;
    }

    public String getCreatedAt() {
        return GenericUtil.getRelativeTimeAgo(createdAt);
    }

    public String getText() {
        return text;
    }

    public User getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", idString='" + idString + '\'' +
                ", createdAt='" + getCreatedAt() + '\'' +
                ", text='" + text + '\'' +
                ", sender=" + sender +
                '}';
    }
}
