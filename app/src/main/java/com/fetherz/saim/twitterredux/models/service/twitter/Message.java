package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sm032858 on 4/1/17.
 */

public class Message {
    public static final String ID = "id";
    public static final String ID_STR = "id_str";
    public static final String CREATED_AT = "created_at";
    public static final String TEXT = "text";
    public static final String SENDER = "sender";

    @SerializedName(ID)
    long id;

    @SerializedName(ID_STR)
    String idString;

    @SerializedName(CREATED_AT)
    private String createdAt;

    @SerializedName(TEXT)
    private String text;

    @SerializedName(SENDER)
    private User sender;

    public long getID() {
        return id;
    }

    public String getIdStr() {
        return idString;
    }

    public String getCreatedAt() {
        return createdAt;
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
                ", createdAt='" + createdAt + '\'' +
                ", text='" + text + '\'' +
                ", sender=" + sender +
                '}';
    }
}
