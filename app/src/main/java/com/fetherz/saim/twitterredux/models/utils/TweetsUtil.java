package com.fetherz.saim.twitterredux.models.utils;

import com.fetherz.saim.twitterredux.database.utils.DBFlowExclusionStrategy;
import com.fetherz.saim.twitterredux.models.service.twitter.Message;
import com.fetherz.saim.twitterredux.utils.JsonHelper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Twitter models
 * @author Sai Muppa
 */
public class TweetsUtil {

    /**
     * Deserializes json response into a collection of client understandable objects of type {@link com.fetherz.saim.twitterredux.models.client.Tweet}
     * @param jsonResponseString
     * @return Collection of {@link com.fetherz.saim.twitterredux.models.client.Tweet} objects
     */
    public static List<com.fetherz.saim.twitterredux.models.client.Tweet> getTweetsFromJson(String jsonResponseString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy[]{new DBFlowExclusionStrategy()});

        Gson gson = gsonBuilder.create();
        List<com.fetherz.saim.twitterredux.models.service.twitter.Tweet> serviceTweets;
        serviceTweets = gson.fromJson(jsonResponseString,
                new TypeToken<List<com.fetherz.saim.twitterredux.models.service.twitter.Tweet>>(){}.getType());

        return transposeServiceTweetsToClientTweets(serviceTweets);
    }

    /**
     * Deserializes json reponse into an object if type {@link com.fetherz.saim.twitterredux.models.client.Tweet}
     * @param jsonReponseString
     * @return An object of type {@link com.fetherz.saim.twitterredux.models.client.Tweet}
     */
    public static com.fetherz.saim.twitterredux.models.client.Tweet getTweetFromJson(String jsonReponseString){
        com.fetherz.saim.twitterredux.models.service.twitter.Tweet serviceTweet = JsonHelper.GetResponseObject(jsonReponseString, com.fetherz.saim.twitterredux.models.service.twitter.Tweet.class);

        return transposeServiceTweetToClientTweet(serviceTweet);
    }

    /**
     * Deserializes json reponse into an object if type {@link com.fetherz.saim.twitterredux.models.client.User}
     * @param jsonResponseString
     * @return An object of type {@link com.fetherz.saim.twitterredux.models.client.User}
     */
    public static com.fetherz.saim.twitterredux.models.client.User getUserFromJson(String jsonResponseString){
        com.fetherz.saim.twitterredux.models.service.twitter.User serviceUser = JsonHelper.GetResponseObject(jsonResponseString, com.fetherz.saim.twitterredux.models.service.twitter.User.class);

        return transposeServiceUserToClientUser(serviceUser);
    }

    /**
     * Transposes a collection service Tweet model to a collection of client Tweet models
     * @param serviceTweets
     * @return Collection of {@link com.fetherz.saim.twitterredux.models.client.Tweet} objects
     */
    public static List<com.fetherz.saim.twitterredux.models.client.Tweet> transposeServiceTweetsToClientTweets(List<com.fetherz.saim.twitterredux.models.service.twitter.Tweet> serviceTweets){
        List<com.fetherz.saim.twitterredux.models.client.Tweet> clientTweets = new ArrayList<>();

        if(serviceTweets != null) {
            for (com.fetherz.saim.twitterredux.models.service.twitter.Tweet serviceTweet: serviceTweets) {
                clientTweets.add(transposeServiceTweetToClientTweet(serviceTweet));
            }
        }

        return clientTweets;
    }

    /**
     * Transposes the service Tweet model to client Tweet model
     * @param serviceTweet
     * @return Object of type {@link com.fetherz.saim.twitterredux.models.client.Tweet}
     */
    public static com.fetherz.saim.twitterredux.models.client.Tweet transposeServiceTweetToClientTweet(com.fetherz.saim.twitterredux.models.service.twitter.Tweet serviceTweet) {
        if(serviceTweet != null) {
            return new com.fetherz.saim.twitterredux.models.client.Tweet.TweetBuilder(serviceTweet.getTweetId())
                    .setTweetIdString(serviceTweet.getTweetIdString())
                    .setCreatedAt(serviceTweet.getCreatedAt())
                    .setText(serviceTweet.getText())
                    .setRetweetCount(serviceTweet.getRetweetCount())
                    .setFavouriteCount(serviceTweet.getFavouriteCount())
                    .setFavorited(serviceTweet.getFavorited())
                    .setReTweeted(serviceTweet.getReTweeted())
                    .setRetweetUser(transposeServiceUserToClientUser(serviceTweet.getRetweetUser()))
                    .setUser(transposeServiceUserToClientUser(serviceTweet.getUser()))
                    .setHasRetweetStatus(serviceTweet.getHasRetweetStatus())
                    .setMediaType(serviceTweet.getMediaType())
                    .setMediaUrl(serviceTweet.getMediaUrl())
                    .setVideoUrl(serviceTweet.getVideoUrl())
                    .setMediaContentType(serviceTweet.getMediaContentType())
                    .createTweet();
        }

        return  null;
    }

    /**
     * Transposes the service User model to client User model
     * @param serviceUser
     * @return Object of type {@link com.fetherz.saim.twitterredux.models.client.User}
     */
    public static com.fetherz.saim.twitterredux.models.client.User transposeServiceUserToClientUser(com.fetherz.saim.twitterredux.models.service.twitter.User serviceUser){
        if(serviceUser != null) {
            return new com.fetherz.saim.twitterredux.models.client.User.UserBuilder(serviceUser.getUserId())
                    .setUserIdString(serviceUser.getUserIdString())
                    .setProfileImageUrl(serviceUser.getProfileImageUrl())
                    .setName(serviceUser.getName())
                    .setScreenName(serviceUser.getScreenName())
                    .createUser();
        }

        return null;
    }

    public static List<com.fetherz.saim.twitterredux.models.client.Message> getMessagesFromJson(String jsonResponseString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy[]{new DBFlowExclusionStrategy()});

        Gson gson = gsonBuilder.create();
        List<com.fetherz.saim.twitterredux.models.service.twitter.Message> messages;
        messages = gson.fromJson(jsonResponseString,
                new TypeToken<List<com.fetherz.saim.twitterredux.models.service.twitter.Message>>(){}.getType());

        return transposeServiceMessagesToClientMessages(messages);

    }

    public static List<com.fetherz.saim.twitterredux.models.client.Message> transposeServiceMessagesToClientMessages(List<com.fetherz.saim.twitterredux.models.service.twitter.Message> serviceMessages) {
        List<com.fetherz.saim.twitterredux.models.client.Message> clientMessages = new ArrayList<>();

        if(serviceMessages != null) {
            for (com.fetherz.saim.twitterredux.models.service.twitter.Message serviceMessage: serviceMessages) {
                clientMessages.add(transposeServiceMessageToClientMessage(serviceMessage));
            }
        }

        return clientMessages;
    }

    public static com.fetherz.saim.twitterredux.models.client.Message transposeServiceMessageToClientMessage(Message serviceMessage) {
        if(serviceMessage != null) {
            return new com.fetherz.saim.twitterredux.models.client.Message.MessageBuilder(serviceMessage.getID())
                    .setIdString(serviceMessage.getIdStr())
                    .setCreatedAt(serviceMessage.getCreatedAt())
                    .setSender(transposeServiceUserToClientUser(serviceMessage.getSender()))
                    .setText(serviceMessage.getText())
                    .createMessage();
        }

        return null;
    }
}
