package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sm032858 on 3/25/17.
 */

public class ExtendedEntities {
    static final String MEDIA = "media";

    @SerializedName(MEDIA)
    List<Media> mediaCollection;

    public List<Media> getMediaCollection() {
        return mediaCollection;
    }

    @Override
    public String toString() {
        return "ExtendedEntities{" +
                "mediaCollection=" + mediaCollection +
                '}';
    }
}
