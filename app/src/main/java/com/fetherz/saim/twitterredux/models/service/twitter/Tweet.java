package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sm032858 on 3/21/17.
 */

public class Tweet {
    static final String CREATED_AT = "created_at";
    static final String TWEET_ID_STR = "id_str";
    static final String TWEET_ID = "id";
    static final String TEXT = "text";
    static final String FAVOURITE_COUNT = "favorite_count";
    static final String RETWEET_COUNT = "retweet_count";
    static final String FAVORITED = "favorited";
    static final String RETWEETED = "retweeted";
    static final String ENTITIES = "entities";
    static final String EXTENDED_ENTITIES = "extended_entities";
    static final String RETWEETED_STATUS = "retweeted_status";
    static final String USER = "user";

    static final String VIDEO_MP4 = "video/mp4";

    @SerializedName(TWEET_ID_STR)
    String tweetIdString;

    @SerializedName(TWEET_ID)
    long tweetId;

    @SerializedName(CREATED_AT)
    String createdAt;

    @SerializedName(TEXT)
    String text;

    @SerializedName(RETWEET_COUNT)
    String retweetCount;

    @SerializedName(FAVOURITE_COUNT)
    String favouriteCount;

    @SerializedName(FAVORITED)
    Boolean favorited;

    @SerializedName(RETWEETED)
    Boolean reTweeted;

    @SerializedName(ENTITIES)
    Entities entities;

    @SerializedName(EXTENDED_ENTITIES)
    ExtendedEntities extendedEntities;

    @SerializedName(RETWEETED_STATUS)
    RetweetedStatus retweetedStatus;

    @SerializedName(USER)
    User user;

    public String getTweetIdString() {
        return tweetIdString;
    }

    public long getTweetId() {
        return tweetId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public String getRetweetCount() {
        return retweetCount;
    }

    public String getFavouriteCount() {
        return favouriteCount;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public Boolean getReTweeted() {
        return reTweeted;
    }

    public Entities getEntities() {
        return entities;
    }

    public ExtendedEntities getExtendedEntities() {
        return extendedEntities;
    }

    public RetweetedStatus getRetweetedStatus() {
        return retweetedStatus;
    }

    public User getUser() {
        if(retweetedStatus != null){
            return retweetedStatus.getUser();
        }
        return user;
    }

    public User getRetweetUser(){
        if(retweetedStatus != null){
            return user;
        }

        return null;
    }

    public Boolean getHasRetweetStatus() {
        if(retweetedStatus != null){
            return true;
        }
        return false;
    }

    public String getMediaType() {
        if(extendedEntities != null && extendedEntities.getMediaCollection() != null && extendedEntities.getMediaCollection().size() > 0){
            VideoInfo videoInfo = extendedEntities.getMediaCollection().get(0).getVideoInfo();

            if(videoInfo != null){
                List<Variant> variants = videoInfo.getVariants();

                if(variants != null && variants.size() > 0){

                    Variant localVariant = null;
                    for (Variant variant: variants) {
                        if(variant != null && variant.getContentType() == VIDEO_MP4) {
                            localVariant = variant;
                            break;
                        }
                    }

                    if(localVariant != null){
                        return extendedEntities.getMediaCollection().get(0).getType();
                    }
                }
            }
        }

        if(entities != null && entities.getMediaCollection() != null && entities.getMediaCollection().size() > 0){
            return entities.getMediaCollection().get(0).getType(); // returns first media item's type found
        }
        return "";
    }

    public String getMediaUrl() {
        if(entities != null && entities.getMediaCollection() != null && entities.getMediaCollection().size() > 0){
            return entities.getMediaCollection().get(0).getMediaURl(); // returns first media item's type found
        }
        return "";
    }

    public String getVideoUrl() {
        if(extendedEntities  != null && extendedEntities.getMediaCollection() != null && extendedEntities.getMediaCollection().size() > 0){
            VideoInfo videoInfo = extendedEntities.getMediaCollection().get(0).getVideoInfo();

            if(videoInfo != null){
                List<Variant> variants = videoInfo.getVariants();

                if(variants != null && variants.size() > 0){
                    Variant localVariant = null;
                    for (Variant variant: variants) {
                        if(variant != null && variant.getContentType() == VIDEO_MP4) {
                            localVariant = variant;
                            break;
                        }
                    }

                    if(localVariant!=null){
                        return localVariant.getUrl();
                    }
                }
            }
        }
        return "";
    }

    public String getMediaContentType() {
        if(extendedEntities  != null && extendedEntities.getMediaCollection() != null && extendedEntities.getMediaCollection().size() > 0){
            VideoInfo videoInfo = extendedEntities.getMediaCollection().get(0).getVideoInfo();

            if(videoInfo != null){
                List<Variant> variants = videoInfo.getVariants();

                if(variants != null && variants.size() > 0){
                    Variant localVariant = null;
                    for (Variant variant: variants) {
                        if(variant != null && variant.getContentType() == VIDEO_MP4) {
                            localVariant = variant;
                            break;
                        }
                    }

                    if(localVariant!=null){
                        return localVariant.getContentType();
                    }
                }
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetIdString='" + tweetIdString + '\'' +
                ", tweetId=" + tweetId +
                ", createdAt='" + createdAt + '\'' +
                ", text='" + text + '\'' +
                ", retweetCount='" + retweetCount + '\'' +
                ", favouriteCount='" + favouriteCount + '\'' +
                ", favorited=" + favorited +
                ", reTweeted=" + reTweeted +
                ", entities=" + entities +
                ", extendedEntities=" + extendedEntities +
                ", retweetedStatus=" + retweetedStatus +
                ", user=" + user +
                ", hasRetweetStatus=" + getHasRetweetStatus() +
                ", mediaType='" + getMediaType() + '\'' +
                ", mediaUrl='" + getMediaUrl() + '\'' +
                ", videoUrl='" + getVideoUrl() + '\'' +
                ", mediaContentType='" + getMediaContentType() + '\'' +
                '}';
    }
}
