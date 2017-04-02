package com.fetherz.saim.twitterredux.models.client;

import com.fetherz.saim.twitterredux.utils.GenericUtil;

import org.parceler.Parcel;

/**
 * Created by sm032858 on 3/25/17.
 */

@Parcel(analyze={Tweet.class})
public class Tweet {

    static final String COLUMN_CREATED_AT = "CreatedAt";
    static final String COLUMN_ID_STR = "Id";
    static final String COLUMN_TEXT = "Text";
    static final String COLUMN_FAVOURITE_COUNT = "FavoriteCount";
    static final String COLUMN_RETWEET_COUNT = "RetweetCount";
    static final String COLUMN_MEDIA = "Media";
    static final String COLUMN_TYPE = "Type";
    static final String COLUMN_MEDIA_URL = "MediaUrl";
    static final String COLUMN_EXTENDED_ENTITIES = "ExtendedEntities";
    static final String COLUMN_VIDEO = "Video";
    static final String COLUMN_PHOTO = "photo";
    static final String COLUMN_VIDEO_INFO = "VideoInfo";
    static final String COLUMN_VARIANTS = "Variants";
    static final String COLUMN_CONTENT_TYPE = "ContentType";
    static final String COLUMN_URL = "Url";
    static final String COLUMN_USER = "User";
    static final String COLUMN_ENTITIES = "Entities";
    static final String COLUMN_VIDEO_MP4 = "VideoMp4";

    public static class TweetBuilder {
        private String tweetIdString;
        private long tweetId;
        private String createdAt;
        private String text;
        private String retweetCount;
        private String favouriteCount;
        private Boolean favorited;
        private Boolean reTweeted;
        private User user;
        private User retweetUser;
        private Boolean hasRetweetStatus;
        private String twitterUrl;
        private String mediaType;
        private String mediaUrl;
        private String videoUrl;
        private String mediaContentType;

        public TweetBuilder(long tweetId){
            this.tweetId = tweetId;
        }

        public TweetBuilder setTweetIdString(String tweetIdString) {
            this.tweetIdString = tweetIdString;
            return this;
        }

        public TweetBuilder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TweetBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public TweetBuilder setRetweetCount(String retweetCount) {
            this.retweetCount = retweetCount;
            return this;
        }

        public TweetBuilder setFavouriteCount(String favouriteCount) {
            this.favouriteCount = favouriteCount;
            return this;
        }

        public TweetBuilder setFavorited(Boolean favorited) {
            this.favorited = favorited;
            return this;
        }

        public TweetBuilder setReTweeted(Boolean reTweeted) {
            this.reTweeted = reTweeted;
            return this;
        }

        public TweetBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public TweetBuilder setRetweetUser(User retweetUser) {
            this.retweetUser = retweetUser;
            return this;
        }

        public TweetBuilder setHasRetweetStatus(Boolean hasRetweetStatus) {
            this.hasRetweetStatus = hasRetweetStatus;
            return this;
        }

        public TweetBuilder setMediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public TweetBuilder setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
            return this;
        }

        public TweetBuilder setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public TweetBuilder setMediaContentType(String mediaContentType) {
            this.mediaContentType = mediaContentType;
            return this;
        }

        public Tweet createTweet() {
            return new Tweet(tweetIdString, tweetId, createdAt, text, retweetCount, favouriteCount, favorited, reTweeted, user, retweetUser, hasRetweetStatus, mediaType, mediaUrl, videoUrl, mediaContentType);
        }
    }


    String tweetIdString;

    long tweetId;

    String createdAt;

    String text;

    String retweetCount;

    String favouriteCount;

    Boolean favorited;

    Boolean reTweeted;

    User user;

    User retweetUser;

    Boolean hasRetweetStatus;

    String mediaType;

    String mediaUrl;

    String videoUrl;

    String mediaContentType;

    public Tweet(){ }

    private Tweet(String tweetIdString, long tweetId, String createdAt, String text, String retweetCount,
                 String favouriteCount, Boolean favorited, Boolean reTweeted, User user, User retweetUser,
                 Boolean hasRetweetStatus, String mediaType, String mediaUrl, String videoUrl,
                 String mediaContentType) {
        this.tweetIdString = tweetIdString;
        this.tweetId = tweetId;
        this.createdAt = createdAt;
        this.text = text;
        this.retweetCount = retweetCount;
        this.favouriteCount = favouriteCount;
        this.favorited = favorited;
        this.reTweeted = reTweeted;
        this.user = user;
        this.retweetUser = retweetUser;
        this.hasRetweetStatus = hasRetweetStatus;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.videoUrl = videoUrl;
        this.mediaContentType = mediaContentType;
    }


    public String getTweetIdString() {
        return tweetIdString;
    }

    public long getTweetId() {
        return tweetId;
    }

    public String getCreatedAt() {
        return GenericUtil.getRelativeTimeAgo(createdAt);
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

    public User getUser() {
        return user;
    }

    public User getRetweetUser() {
        return retweetUser;
    }

    public Boolean getHasRetweetStatus() {
        return hasRetweetStatus;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getMediaContentType() {
        return mediaContentType;
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
                ", user=" + user +
                ", retweetUser=" + retweetUser +
                ", hasRetweetStatus=" + hasRetweetStatus +
                ", mediaType='" + mediaType + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", mediaContentType='" + mediaContentType + '\'' +
                '}';
    }
}
