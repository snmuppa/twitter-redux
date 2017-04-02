package com.fetherz.saim.twitterredux.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * Created by sm032858 on 3/26/17.
 */
public class ImageTweetViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    List<Tweet> mTweets;
    Context mContext;

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

    @BindView(R.id.ivTweetImage)
    DynamicHeightImageView mIvTweetImage;

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


    public ImageTweetViewHolder(Context context, View itemView, List<Tweet> tweets) {
        super(itemView);

        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);

        mTweets = tweets;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        //TODO: Open Tweet Detail
    }
}
