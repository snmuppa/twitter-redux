package com.fetherz.saim.twitterredux.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.images.ImageLoaderImpl;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.services.TwitterClient;
import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class DetailTextActivity extends BaseActivity {
    private static final String EXTRA_TWEET = "tweet";

    private static final String RETWEETED_TEXT = "retweeted";
    private static final String TAG = "DetailTextActivity";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DetailTextActivity.class);
        return intent;
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

    @BindView(R.id.mEtTweet)
    EditText mEtTweet;

    @BindView(R.id.tvTweetLength)
    TextView mTvTweetLength;

    @BindView(R.id.btnTweet)
    Button mBtnTweet;

    Tweet mTweet;

    TwitterClient mTwitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_text);

        ButterKnife.bind(DetailTextActivity.this);

        mTwitterClient = TwitterApplication.getRestClient();

        mTweet = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_TWEET));

        setToolbar();

        updateViews();

        setTweetTextListener();
    }

    private void setToolbar() {
        //set up the toolbar as action bar
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void updateViews() {
        if(mTweet != null){
            if(mTweet.getHasRetweetStatus()){
                mTvRetweetedUserName.setVisibility(View.VISIBLE);
                mIvRetweeted.setVisibility(View.VISIBLE);

                mTvRetweetedUserName.setText(String.format("%s%s %s", "@", mTweet.getRetweetUser().getScreenName(), RETWEETED_TEXT));
            }else {
                mTvRetweetedUserName.setVisibility(View.GONE);
                mIvRetweeted.setVisibility(View.GONE);
            }

            mTvUserName.setText(mTweet.getUser().getName());

            mTvTweetText.setText(mTweet.getText());

            ImageLoaderImpl imageLoader = new ImageLoaderImpl();
            imageLoader.loadImage(mTweet.getUser().getProfileImageUrl(), mIvProfilePicture);

            String screenName = mTweet.getUser().getScreenName();

            mEtTweet.append(screenName + " ");

            mTvTweetLength.setText(Integer.toString(screenName.length() + 1));

            mTvScreenName.setText(screenName);
            mTvRelativeTime.setText(mTweet.getCreatedAt());

            mIvReply.setTag(mTweet);

            mTvFavorites.setText(mTweet.getFavouriteCount());
            mIvFavorite.setTag(mTweet);
            if(mTweet.getFavorited()){
                mIvFavorite.setImageResource(R.drawable.ic_heart);
            }else {
                mIvFavorite.setImageResource(R.drawable.ic_heart_outline);
            }

            mTvRetweets.setText(mTweet.getRetweetCount());
            mIvRetweet.setTag(mTweet);
            if(mTweet.getReTweeted()){
                mIvRetweet.setImageResource(R.drawable.ic_retweet_green);
            }else {
                mIvRetweet.setImageResource(R.drawable.ic_retweet);
            }
        }
    }

    /**
     * Attaches the text change listener while updating the tweet text
     */
    private void setTweetTextListener() {
        mTvTweetLength.setTextColor(Color.BLACK);
        mEtTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long length = 0;
                if (s.length() > 140) {
                    mTvTweetLength.setTextColor(Color.RED);
                    length = 140 - s.length();
                    mBtnTweet.setEnabled(false);
                } else {
                    mTvTweetLength.setTextColor(Color.BLACK);
                    length = s.length();
                    mBtnTweet.setEnabled(true);
                }

                mTvTweetLength.setText(Long.toString(length));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Handler for tweeting.
     */
    @OnClick(R.id.btnTweet)
    public void onTweet(){
        String tweetText = mEtTweet.getText().toString();

        mTwitterClient.composeATweet(tweetText, new TextHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtil.logE(TAG, "Http request failure with status code: " + statusCode + ". " + throwable.getMessage(), throwable);

                Snackbar.make(getCurrentFocus(), "Error occured while composing a new Tweet.", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logI(TAG, responseString);

                Tweet tweet = null;
                if(responseString != null) {
                    tweet = TweetsUtil.getTweetFromJson(responseString);

                    LogUtil.logD(TAG, tweet.toString());
                }

                final Tweet finalTweet = tweet;

                runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TWEET, Parcels.wrap(finalTweet));
                    intent.putExtra("HttpStatusCode", statusCode);
                    // set result status code and bundle data
                    setResult(RESULT_OK, intent);

                    finish(); //closes child activity and passes data to the parent
                });
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
