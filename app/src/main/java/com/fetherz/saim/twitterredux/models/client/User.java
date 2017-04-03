package com.fetherz.saim.twitterredux.models.client;

import com.fetherz.saim.twitterredux.utils.GenericUtil;

import org.parceler.Parcel;

/**
 * Created by sm032858 on 3/25/17.
 */

@Parcel(analyze={User.class})
public class User {
    public static class UserBuilder {
        private long userId;
        private String userIdString;
        private String name;
        private String screenName;
        private String profileImageUrl;
        private String location;
        private String description;
        private int followersCount;
        private int friendsCount;
        private int listedCount;
        private String createdAt;
        private int favouritesCount;
        private boolean verified;
        private int statusesCount;
        private String profileBackgroundColor;
        private String profileBackgroundImageUrl;
        private boolean userFollowing;

        public UserBuilder(long userId){
            this.userId = userId;
        }

        public UserBuilder setUserIdString(String userIdString) {
            this.userIdString = userIdString;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setScreenName(String screenName) {
            this.screenName = screenName;
            return this;
        }

        public UserBuilder setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public UserBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public UserBuilder setDescription(String description){
            this.description = description;
            return  this;
        }

        public UserBuilder setFollowersCount(int followersCount) {
            this.followersCount = followersCount;
            return this;
        }

        public UserBuilder setFriendsCount(int friendsCount) {
            this.friendsCount = friendsCount;
            return this;
        }

        public UserBuilder setListedCount(int listedCount) {
            this.listedCount = listedCount;
            return this;
        }

        public UserBuilder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder setFavouritesCount(int favouritesCount) {
            this.favouritesCount = favouritesCount;
            return this;
        }

        public UserBuilder setVerified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public UserBuilder setStatusesCount(int statusesCount) {
            this.statusesCount = statusesCount;
            return this;
        }

        public UserBuilder setProfileBackgroundColor(String profileBackgroundColor) {
            this.profileBackgroundColor = profileBackgroundColor;
            return this;
        }

        public UserBuilder setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
            this.profileBackgroundImageUrl = profileBackgroundImageUrl;
            return this;
        }

        public UserBuilder setUserFollowing(boolean userFollowing){
            this.userFollowing = userFollowing;
            return this;
        }

        public User createUser() {
            return new User(userId, userIdString, name, screenName, profileImageUrl, location, description, followersCount,
                    friendsCount, listedCount, createdAt, favouritesCount, verified, statusesCount, profileBackgroundColor,
                    profileBackgroundImageUrl, userFollowing);
        }
    }

    long userId;

    String userIdString;

    String name;

    String screenName;

    String profileImageUrl;

    String location;

    String description;

    int followersCount;

    int friendsCount;

    int listedCount;

    String createdAt;

    int favouritesCount;

    boolean verified;

    int statusesCount;

    String profileBackgroundColor;

    String profileBackgroundImageUrl;

    boolean userFollowing;

    public User() { }

    private User(long userId, String userIdString, String name, String screenName, String profileImageUrl,
                 String location, String description, int followersCount, int friendsCount, int listedCount, String createdAt,
                 int favouritesCount, boolean verified, int statusesCount, String profileBackgroundColor, String profileBackgroundImageUrl, boolean userFollowing) {
        this.userId = userId;
        this.userIdString = userIdString;
        this.name = name;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
        this.description = description;
        this.followersCount = followersCount;
        this.friendsCount = friendsCount;
        this.listedCount = listedCount;
        this.createdAt = createdAt;
        this.favouritesCount = favouritesCount;
        this.verified = verified;
        this.statusesCount = statusesCount;
        this.profileBackgroundColor = profileBackgroundColor;
        this.profileBackgroundImageUrl =profileBackgroundImageUrl;
        this.userFollowing = userFollowing;
    }

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
        return String.format("%s%s", "@", screenName);
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
        return GenericUtil.getRelativeTimeAgo(createdAt);
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

    public boolean isUserFollowing() {
        return userFollowing;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
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
