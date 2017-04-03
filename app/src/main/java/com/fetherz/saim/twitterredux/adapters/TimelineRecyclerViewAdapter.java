package com.fetherz.saim.twitterredux.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.activities.UserProfileActivity;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.utils.PatternEditableBuilder;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;
import java.util.regex.Pattern;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

/**
 * Created by sm032858 on 3/26/17.
 */
public class TimelineRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Tweet> mTweets;
    Context mContext;

    static final int TEXT_TWEET = 0;
    static final int IMAGE_TWEET = 1;
    static final int VIDEO_TWEET = 2;

    static final String IMAGE_MEDIA_TYPE = "photo";
    static final String VIDEO_MEDIA_TYPE = "video";

    static final String RETWEETED_TEXT = "retweedted";

    public TimelineRecyclerViewAdapter(Context context, List<Tweet> tweets){
        this.mContext = context;
        this.mTweets = tweets;
    }

    /**
     * Get the tweet count
     * @return
     */
    @Override
    public int getItemCount(){
        return this.mTweets.size();
    }

    /**
     * Get the view type based on the media type
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position){
        Tweet tweet = mTweets.get(position);

        if(tweet.getMediaType().equalsIgnoreCase(IMAGE_MEDIA_TYPE)){
            return IMAGE_TWEET;
        }
        else if(tweet.getMediaType().equalsIgnoreCase(VIDEO_MEDIA_TYPE)){
            return VIDEO_TWEET;
        }

        return TEXT_TWEET;
    }

    /**
     * Inflate the view holder based on the view type
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case IMAGE_TWEET:
                View imageView = inflater.inflate(R.layout.image_tweet, parent, false);
                viewHolder = new ImageTweetViewHolder(mContext, imageView, mTweets);
            break;

            case TEXT_TWEET:
            default: //TODO: add logic for video tweet
                View textView = inflater.inflate(R.layout.text_tweet, parent, false);
                viewHolder = new TextTweetViewHolder(mContext, textView, mTweets);
            break;
        }

        return viewHolder;
    }

    /**
     * Bind the view holder based on the view type
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case IMAGE_TWEET:
                ImageTweetViewHolder imageTweetViewHolder = (ImageTweetViewHolder) holder;
                bindImageTweetViewHolder(imageTweetViewHolder, position);
            break;

            case TEXT_TWEET:
            default: //TODO: add logic for video tweet
                TextTweetViewHolder textTweetViewHolder = (TextTweetViewHolder) holder;
                bindTextTweetViewHolder(textTweetViewHolder, position);
            break;
        }

    }

    /***
     * Binds the tweet text view
     * @param viewHolder
     * @param position
     */
    private void bindTextTweetViewHolder(TextTweetViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);

        if(tweet != null){
            if(tweet.getHasRetweetStatus()){
                viewHolder.mTvRetweetedUserName.setVisibility(View.VISIBLE);
                viewHolder.mIvRetweeted.setVisibility(View.VISIBLE);

                viewHolder.mTvRetweetedUserName.setText(String.format("%s %s", tweet.getRetweetUser().getScreenName(), RETWEETED_TEXT));
            }else {
                viewHolder.mTvRetweetedUserName.setVisibility(View.GONE);
                viewHolder.mIvRetweeted.setVisibility(View.GONE);
            }

            viewHolder.mTvUserName.setText(tweet.getUser().getName());

            viewHolder.mTvTweetText.setText(tweet.getText());

            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                            text -> {
                                Intent intent = UserProfileActivity.newIntent(mContext);
                                intent.putExtra(UserProfileActivity.EXTRA_TWEET_USER, Parcels.wrap(tweet.getUser()));
                                mContext.startActivity(intent);

                                Log.d(LOG_TAG, "Profile Image Selected, for user: " + tweet.getUser());
                            }).into(viewHolder.mTvTweetText);

            //ImageLoaderImpl imageLoader = new ImageLoaderImpl();
            //imageLoader.loadImage(tweet.getUser().getProfileImageUrl(), viewHolder.mIvProfilePicture);

            Picasso.with(mContext).load(tweet.getUser().getProfileImageUrl())
                    .transform(new RoundedCornersTransformation(2,2))
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_photo)
                    .into(viewHolder.mIvProfilePicture);

            viewHolder.mTvScreenName.setText(tweet.getUser().getScreenName());
            viewHolder.mTvRelativeTime.setText(tweet.getCreatedAt());

            viewHolder.mIvReply.setTag(tweet);

            viewHolder.mTvFavorites.setText(tweet.getFavouriteCount());
            viewHolder.mIvFavorite.setTag(tweet);
            if(tweet.getFavorited()){
                viewHolder.mIvFavorite.setImageResource(R.drawable.ic_heart);
            }else {
                viewHolder.mIvFavorite.setImageResource(R.drawable.ic_heart_outline);
            }

            viewHolder.mTvRetweets.setText(tweet.getRetweetCount());
            viewHolder.mIvRetweet.setTag(tweet);
            if(tweet.getReTweeted()){
                viewHolder.mIvRetweet.setImageResource(R.drawable.ic_retweet_green);
            }else {
                viewHolder.mIvRetweet.setImageResource(R.drawable.ic_retweet);
            }
        }
    }

    /**
     * Binds the tweet image view
     * @param viewHolder
     * @param position
     */
    private void bindImageTweetViewHolder(ImageTweetViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);

        if(tweet != null){
            if(tweet.getHasRetweetStatus()){
                viewHolder.mTvRetweetedUserName.setVisibility(View.VISIBLE);
                viewHolder.mIvRetweeted.setVisibility(View.VISIBLE);

                viewHolder.mTvRetweetedUserName.setText(String.format("%s %s", tweet.getRetweetUser().getScreenName(), RETWEETED_TEXT));
            }else {
                viewHolder.mTvRetweetedUserName.setVisibility(View.GONE);
                viewHolder.mIvRetweeted.setVisibility(View.GONE);
            }

            viewHolder.mTvUserName.setText(tweet.getUser().getName());

            viewHolder.mTvTweetText.setText(tweet.getText());

            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                            text -> {
                                Intent intent = UserProfileActivity.newIntent(mContext);
                                intent.putExtra(UserProfileActivity.EXTRA_TWEET_USER, Parcels.wrap(tweet.getUser()));
                                mContext.startActivity(intent);

                                Log.d(LOG_TAG, "Profile Image Selected, for user: " + tweet.getUser());
                            }).into(viewHolder.mTvTweetText);

            //The following is Glide library usage for image loading
            //ImageLoaderImpl imageLoader = new ImageLoaderImpl();
            //imageLoader.loadImage(tweet.getUser().getProfileImageUrl(), viewHolder.mIvProfilePicture);

            Picasso.with(mContext).load(tweet.getUser().getProfileImageUrl())
                    .transform(new RoundedCornersTransformation(2,2))
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_photo)
                    .into(viewHolder.mIvProfilePicture);

            viewHolder.mTvScreenName.setText(tweet.getUser().getScreenName());
            viewHolder.mTvRelativeTime.setText(tweet.getCreatedAt());

            viewHolder.mIvReply.setTag(tweet);

            viewHolder.mTvFavorites.setText(tweet.getFavouriteCount());
            viewHolder.mIvFavorite.setTag(tweet);
            if(tweet.getFavorited()){
                viewHolder.mIvFavorite.setImageResource(R.drawable.ic_heart);
            }else {
                viewHolder.mIvFavorite.setImageResource(R.drawable.ic_heart_outline);
            }

            viewHolder.mTvRetweets.setText(tweet.getRetweetCount());
            viewHolder.mIvRetweet.setTag(tweet);
            if(tweet.getReTweeted()){
                viewHolder.mIvRetweet.setImageResource(R.drawable.ic_retweet_green);
            }else {
                viewHolder.mIvRetweet.setImageResource(R.drawable.ic_retweet);
            }

            //The following is Glide library usage for image loading
            //imageLoader = new ImageLoaderImpl();
            //imageLoader.loadImage(tweet.getMediaUrl(), viewHolder.mIvTweetImage);

            Picasso.with(mContext).load(tweet.getMediaUrl())
                    .transform(new RoundedCornersTransformation(10,10))
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_camera)
                    .into(viewHolder.mIvTweetImage);
        }
    }

    /**
     * Clear all the tweets
     */
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    /***
     * Adds a list of tweets
     * @param tweets
     */
    public void addAll(List<Tweet> tweets) {
        mTweets.addAll(tweets);
        notifyDataSetChanged();
    }
}
