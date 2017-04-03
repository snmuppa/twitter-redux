package com.fetherz.saim.twitterredux.services;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TwitterClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "YtEc21QWU0try76qAvHq9Gayb";       // Change this
    public static final String REST_CONSUMER_SECRET = "REdnhUm1QvqaKAgdAYomyF93s4M3DbOzu7Xy7ykOW5JwrLkcNU"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://droidgeddontweets"; // Change this (here and in manifest)

    public static final String GET_HOME_TIMELINE = "statuses/home_timeline.json";
    public static final String QUERY_PARAM_SINCE_ID = "since_id";
    public static final String QUERY_PARAM_MAX_ID = "max_id";

    public static final String GET_USER_TIMELINE = "statuses/user_timeline.json";
    public static final String QUERY_PARAM_COUNT = "count";

    public static final String UPDATE_STATUS = "statuses/update.json";
    public static final String QUERY_PARAM_STATUS = "status";
    public static final String QUERY_PARAM_REPLY_TO_STATUS_ID = "in_reply_to_status_id";

    public static final String GET_FAVORITES = "favorites/list.json";
    public static final String ADD_FAVORITE = "favorites/create.json";
    public static final String REMOVE_FAVORITE = "favorites/destroy.json";
    public static final String QUERY_PARAM_ID = "id";

    public static final String ADD_RETWEET  = "statuses/retweet/%s/.json"; //format string to update the tweet id in the path
    public static final String GET_CURRENT_USER = "account/verify_credentials.json";
    public static final short TWEET_COUNT = 15;

    public static final String GET_MENTIONS_TIMELINE = "statuses/mentions_timeline.json";

    public static final String QUERY_PARAM_SCREEN_NAME = "screen_name";

    public static final String GET_DIRECT_MESSAGES_RECEIVED = "direct_messages.json";
    public static final String GET_DIRECT_MESSAGES_SENT = "direct_messages/sent.json";
    public static final String ADD_DIRECT_MESSAGE = "direct_messages/new.json";
    private static final String QUERY_PARAM_TEXT = "text";

    private static final String QUERY_PARAM_EXCLUDE_REPLIES = "exclude_replies";
    private static final String QUERY_PARAM_INCLUDE_RETWEETS = "include_rts";
    private static final String QUERY_PARAM_SEARCH = "q";
    private static final String GET_SEARCH_URL = "search/tweets.json";

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeLine(long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(GET_HOME_TIMELINE);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);

        if(pageId == 1){
            params.put(QUERY_PARAM_SINCE_ID, pageId);
        }
        else {
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        }

        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void getMentionsTimeLine(long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        String apiUrl = getApiUrl(GET_MENTIONS_TIMELINE);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);
        if (pageId == 1) {
            params.put(QUERY_PARAM_SINCE_ID, pageId);
        }
        else {
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        }

        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void getUserTimeLine(String screenName, long pageId, boolean excludeReplies, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(GET_USER_TIMELINE);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);
        params.put(QUERY_PARAM_SCREEN_NAME, screenName);
        params.put(QUERY_PARAM_EXCLUDE_REPLIES, excludeReplies);

        if(pageId == 1){
            params.put(QUERY_PARAM_SINCE_ID, pageId);
        }
        else {
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        }

        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void composeATweet(String text, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl  = getApiUrl(UPDATE_STATUS);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_STATUS, text);

        getClient().post(apiUrl, params, asyncHttpResponseHandler);
    }

    public void searchTweets(String searchQuery, long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        String api_url = getApiUrl(GET_SEARCH_URL);
        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_SEARCH, searchQuery);
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);
        if (pageId == 1)
            params.put(QUERY_PARAM_SINCE_ID, 1);
        else
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        getClient().get(api_url, params, asyncHttpResponseHandler);
    }

    public void replyToStatus(String text, String replyToStatusId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(UPDATE_STATUS);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_REPLY_TO_STATUS_ID, replyToStatusId);
        params.put(QUERY_PARAM_STATUS, text);

        getClient().put(apiUrl, params, asyncHttpResponseHandler);
    }

    public void reTweet(String tweetId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(String.format(ADD_RETWEET, tweetId));

        getClient().post(apiUrl, null, asyncHttpResponseHandler);
    }

    public void setGetFavoriteTweets(long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(GET_FAVORITES);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);
        if (pageId == 1) {
            params.put(QUERY_PARAM_SINCE_ID, pageId);
        }
        else {
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        }

        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void favoriteATweet(String tweetId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(ADD_FAVORITE);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_ID, tweetId);

        getClient().put(apiUrl, params, asyncHttpResponseHandler);
    }

    public void unFavoriteATweet(String tweetId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(REMOVE_FAVORITE);

        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_ID, tweetId);

        getClient().put(apiUrl, params, asyncHttpResponseHandler);
    }

    public void getUser(AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(GET_CURRENT_USER);
        getClient().get(apiUrl, null, asyncHttpResponseHandler);
    }

    public void getReceivedDirectMessages(long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(GET_DIRECT_MESSAGES_RECEIVED);
        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);

        if(pageId == 1){
            params.put(QUERY_PARAM_SINCE_ID, pageId);
        }
        else {
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        }

        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void getSentDirectMessages(long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(GET_DIRECT_MESSAGES_SENT);
        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);

        if(pageId == 1){
            params.put(QUERY_PARAM_SINCE_ID, pageId);
        }
        else {
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        }

        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void getUserMedia(String screenName, long pageId, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        String apiUrl = getApiUrl(GET_USER_TIMELINE);
        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_COUNT, TWEET_COUNT);
        params.put(QUERY_PARAM_SCREEN_NAME, screenName);
        params.put(QUERY_PARAM_EXCLUDE_REPLIES, "true");
        params.put(QUERY_PARAM_INCLUDE_RETWEETS, "false");
        if (pageId == 1)
            params.put(QUERY_PARAM_SINCE_ID, 1);
        else
            params.put(QUERY_PARAM_MAX_ID, pageId - 1);
        getClient().get(apiUrl, params, asyncHttpResponseHandler);
    }

    public void addDirectMessage(String message, String screenName, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl(ADD_DIRECT_MESSAGE);
        RequestParams params = new RequestParams();
        params.put(QUERY_PARAM_SCREEN_NAME, screenName);
        params.put(QUERY_PARAM_TEXT, message);
        getClient().post(apiUrl, params, asyncHttpResponseHandler);
    }

}
