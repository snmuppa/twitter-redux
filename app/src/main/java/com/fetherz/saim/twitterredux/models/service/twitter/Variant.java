package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sm032858 on 3/25/17.
 */

class Variant {
    static final String BITRATE = "bitrate";
    static final String CONTENT_TYPE = "content_type";
    static final String URL = "url";

    @SerializedName(BITRATE)
    public int bitrate;

    @SerializedName(CONTENT_TYPE)
    public String contentType;

    @SerializedName(URL)
    public String url;

    public int getBitrate() {
        return bitrate;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "bitrate=" + bitrate +
                ", contentType='" + contentType + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
