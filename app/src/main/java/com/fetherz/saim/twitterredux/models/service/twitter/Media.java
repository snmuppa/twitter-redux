package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sm032858 on 3/25/17.
 */

public class Media {
    static final String MEDIA_ID = "id";
    static final String MEDIA_ID_STRING = "id_str";
    static final String TYPE = "type";
    static final String MEDIA_URL = "media_url";
    static final String VIDEO_INFO = "video_info";

    @SerializedName(MEDIA_ID)
    long mediaId;

    @SerializedName(MEDIA_ID_STRING)
    String mediaIdString;

    @SerializedName(TYPE)
    String type;

    @SerializedName(MEDIA_URL)
    String mediaURl;

    @SerializedName(VIDEO_INFO)
    VideoInfo videoInfo;

    public long getMediaId() {
        return mediaId;
    }

    public String getMediaIdString() {
        return mediaIdString;
    }

    public String getType() {
        return type;
    }

    public String getMediaURl() {
        return mediaURl;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }


    @Override
    public String toString() {
        return "Media{" +
                "mediaId=" + mediaId +
                ", mediaIdString='" + mediaIdString + '\'' +
                ", type='" + type + '\'' +
                ", mediaURl='" + mediaURl + '\'' +
                ", videoInfo=" + videoInfo +
                '}';
    }
}
