package com.fetherz.saim.twitterredux.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.activities.DetailTextActivity;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

/**
 * Created by sm032858 on 3/26/17.
 */

public class TextTweetViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private static final int REQUEST_CODE = 200;
    @BindView(R.id.ivRetweeted)
    ImageView mIvRetweeted;

    @BindView(R.id.tvRetweetedUserName)
    TextView mTvRetweetedUserName;

    @BindView(R.id.ivProfilePicture)
    DynamicHeightImageView mIvProfilePicture;

    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    @BindView(R.id.tvScreenName)
    TextView mTvScreenName;

    @BindView(R.id.tvTweetText)
    TextView mTvTweetText;

    @BindView(R.id.tvRelativeTime)
    TextView mTvRelativeTime;

    @BindView(R.id.ivBrandIcon)
    ImageView mIvBrandIcon;

    @BindView(R.id.ivReply)
    ImageView mIvReply;

    @BindView(R.id.ivRetweet)
    ImageView mIvRetweet;

    @BindView(R.id.tvRetweets)
    TextView mTvRetweets;

    @BindView(R.id.ivFavorite)
    ImageView mIvFavorite;

    @BindView(R.id.tvFavorites)
    TextView mTvFavorites;


    List<Tweet> mTweets;
    Context mContext;

    public TextTweetViewHolder(Context context, View itemView, List<Tweet> tweets) {
        super(itemView);

        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);

        mTweets = tweets;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = getLayoutPosition();
        Tweet tweet = mTweets.get(position);

        Intent intent = new Intent(mContext, DetailTextActivity.class);
        intent.putExtra("tweet", Parcels.wrap(tweet));
        ((Activity)mContext).startActivityForResult(intent, REQUEST_CODE, new Bundle());

        //Intent intent = DetailTextActivity.newIntent(mContext);
        //intent.putExtra("tweet", Parcels.wrap(tweet));
        //mContext.startActivity(intent);
        Log.d(LOG_TAG, "Tweet selected: " + tweet);
    }
}
