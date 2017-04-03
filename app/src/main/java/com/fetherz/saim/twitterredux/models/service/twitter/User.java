package com.fetherz.saim.twitterredux.models.service.twitter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sm032858 on 3/25/17.
 */

public class User {
    final String USER_ID = "id";
    final String USER_ID_STRING = "id_str";
    final String USER_NAME = "name";
    final String USER_SCREEN_NAME = "screen_name";
    final String PROFILE_IMAGE_URL = "profile_image_url";
    final String USER_LOCATION = "location";
    final String USER_DESCRIPTION = "description";
    final String USER_FOLLOWER_COUNT = "followers_count";
    final String USER_LISTED_COUNT = "listed_count";
    final String USER_FRIENDS_COUNT = "friends_count";
    final String USER_CREATED_AT = "created_at";
    final String USER_FAVORITES_COUNT = "favourites_count";
    final String USER_VERIFIED = "verified";
    final String USER_STATUSES_COUNT = "statuses_count";
    final String USER_PROFILE_BACKGROUND_COLOR = "profile_background_color";
    final String USER_PROFILE_BACKGROUND_IMAGE_URL = "profile_banner_url"; // TODO: change the naming to say banner url all over
    final String USER_FOLLOWING = "following";

    @SerializedName(USER_ID)
    long userId;

    @SerializedName(USER_ID_STRING)
    String userIdString;

    @SerializedName(USER_NAME)
    private String name;

    @SerializedName(USER_SCREEN_NAME)
    private String screenName;

    @SerializedName(PROFILE_IMAGE_URL)
    private String profileImageUrl;

    @SerializedName(USER_LOCATION)
    private String location;

    @SerializedName(USER_DESCRIPTION)
    private String description;

    @SerializedName(USER_FOLLOWER_COUNT)
    private int followersCount;

    @SerializedName(USER_FRIENDS_COUNT)
    private int friendsCount;

    @SerializedName(USER_LISTED_COUNT)
    private int listedCount;

    @SerializedName(USER_CREATED_AT)
    private String createdAt;

    @SerializedName(USER_FAVORITES_COUNT)
    private int favouritesCount;

    @SerializedName(USER_VERIFIED)
    private boolean verified;

    @SerializedName(USER_STATUSES_COUNT)
    private int statusesCount;

    @SerializedName(USER_PROFILE_BACKGROUND_COLOR)
    private String profileBackgroundColor;

    @SerializedName(USER_PROFILE_BACKGROUND_IMAGE_URL)
    private String profileBackgroundImageUrl;

    @SerializedName(USER_FOLLOWING)
    private boolean userFollowing;


    public long getUserId() {
        return userId;
    }

    public String getUserIdString() {
        return userIdString;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getListedCount() {
        return listedCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public boolean getUserFollowing()
    {
        return userFollowing;
    }

    @Override
    public String toString() {
        return "User{" +
                "USER_ID='" + USER_ID + '\'' +
                ", USER_ID_STRING='" + USER_ID_STRING + '\'' +
                ", USER_NAME='" + USER_NAME + '\'' +
                ", USER_SCREEN_NAME='" + USER_SCREEN_NAME + '\'' +
                ", PROFILE_IMAGE_URL='" + PROFILE_IMAGE_URL + '\'' +
                ", USER_LOCATION='" + USER_LOCATION + '\'' +
                ", USER_DESCRIPTION='" + USER_DESCRIPTION + '\'' +
                ", USER_FOLLOWER_COUNT='" + USER_FOLLOWER_COUNT + '\'' +
                ", USER_LISTED_COUNT='" + USER_LISTED_COUNT + '\'' +
                ", USER_FRIENDS_COUNT='" + USER_FRIENDS_COUNT + '\'' +
                ", USER_CREATED_AT='" + USER_CREATED_AT + '\'' +
                ", USER_FAVORITES_COUNT='" + USER_FAVORITES_COUNT + '\'' +
                ", USER_VERIFIED='" + USER_VERIFIED + '\'' +
                ", USER_STATUSES_COUNT='" + USER_STATUSES_COUNT + '\'' +
                ", USER_PROFILE_BACKGROUND_COLOR='" + USER_PROFILE_BACKGROUND_COLOR + '\'' +
                ", USER_PROFILE_BACKGROUND_IMAGE_URL='" + USER_PROFILE_BACKGROUND_IMAGE_URL + '\'' +
                ", USER_FOLLOWING='" + USER_FOLLOWING + '\'' +
                ", userId=" + userId +
                ", userIdString='" + userIdString + '\'' +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", followersCount=" + followersCount +
                ", friendsCount=" + friendsCount +
                ", listedCount=" + listedCount +
                ", createdAt='" + createdAt + '\'' +
                ", favouritesCount=" + favouritesCount +
                ", verified=" + verified +
                ", statusesCount=" + statusesCount +
                ", profileBackgroundColor='" + profileBackgroundColor + '\'' +
                ", profileBackgroundImageUrl='" + profileBackgroundImageUrl + '\'' +
                ", userFollowing=" + userFollowing +
                '}';
    }
}
