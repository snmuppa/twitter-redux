package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sm032858 on 3/25/17.
 */

public class VideoInfo {
    static final String ASPECT_RATIO = "aspect_ratio";
    static final String VARIANTS = "variants";
    static final String DURATION = "duration_millis";

    @SerializedName(ASPECT_RATIO)
    public List<Integer> aspectRatio = null;

    @SerializedName(DURATION)
    public int durationMillis;

    @SerializedName(VARIANTS)
    public List<Variant> variants = null;

    public static String getAspectRatio() {
        return ASPECT_RATIO;
    }

    public int getDurationMillis() {
        return durationMillis;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "aspectRatio=" + aspectRatio +
                ", durationMillis=" + durationMillis +
                ", variants=" + variants +
                '}';
    }
}
