package com.fetherz.saim.twitterredux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.adapters.TimelineRecyclerViewAdapter;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.eventlisteners.EndlessRecyclerViewScrollListener;
import com.fetherz.saim.twitterredux.fragments.ComposeTweetFragment;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.services.TwitterClient;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends BaseActivity
        implements ComposeTweetFragment.ComposeTweetListener {

    private static final String EXTRA_TWEET = "tweet";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.clTimelineActivity)
    CoordinatorLayout mClTimelineActivity;

    @BindView(R.id.rvTweets)
    RecyclerView mRvArticles;

    @BindView(R.id.srContainer)
    SwipeRefreshLayout mSwipeContainer;

    @BindView(R.id.fabComposeTweet)
    FloatingActionButton mFabComposeTweet;

    static final String TAG = "TimelineActivity";
    static final short START_PAGE = 1;

    TwitterClient mTwitterClient;

    List<Tweet> mTweets;
    TimelineRecyclerViewAdapter mTimelineRecyclerViewAdapter;
    EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    LinearLayoutManager mLinearLayoutManager;
    User mCurrentUser;

    /**
     * On Create event in the android event life cycle
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(TimelineActivity.this);

        mTwitterClient = TwitterApplication.getRestClient();

        initializeViews();

        setUser();
        populateTimeline(START_PAGE);
    }

    /**
     * Sets the current user in the local cache
     */
    private void setUser() {
        mTwitterClient.getUser(new TextHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtil.logE(TAG, "Http request failure with status code: " + statusCode + ". " + throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logI(TAG, responseString);

                if(responseString != null) {
                    mCurrentUser = TweetsUtil.getUserFromJson(responseString);

                    LogUtil.logD(TAG, mCurrentUser.toString());
                }
            }
        });
    }

    /**
     * Initializes all the views for this activity
     */
    private void initializeViews() {

        //set up the toolbar as action bar
        setSupportActionBar(mToolbar);

        setTimelineRecyclerView();

        setSwipeRefreshContainer();
    }

    /**
     * Initializes the timeline recycler view
     */
    private void setTimelineRecyclerView() {
        mTweets = new ArrayList<>();

        // Create adapter passing in the initial tweet data
        mTimelineRecyclerViewAdapter = new TimelineRecyclerViewAdapter(TimelineActivity.this, mTweets);

        // Attach the adapter to the recyclerview to populate items
        mRvArticles.setAdapter(mTimelineRecyclerViewAdapter);

        mLinearLayoutManager =
                new LinearLayoutManager(TimelineActivity.this);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set layout manager to position the items
        mRvArticles.setLayoutManager(mLinearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateTimeline(page); //twitter API page numbers start from 1
            }
        };

        // Adds the scroll listener to RecyclerView
        mRvArticles.addOnScrollListener(mEndlessRecyclerViewScrollListener);
    }

    /**
     * Creates the options menu
     * @param menu
     * @return
     */
    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        return super.onCreateOptionsMenu(menu);
    }*/

    /**
     * Sets up actions for menu items
     * @param item
     * @return
     */
    /**@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.compose_settings) {
            showCompose();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }**/


    /**
     * On float action button click show tweet composer
     */
    @OnClick(R.id.fabComposeTweet)
    public void onComposeClick(){
        showCompose();
    }

    /**
     * Show's the tweet compose overlay dialog fragment
     */
    private void showCompose() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(mCurrentUser);
        composeTweetFragment.show(fragmentManager, "Tweet_Away");
    }

    /**
     * Set's up the swipe refresh layout for the tweets time line
     */
    private void setSwipeRefreshContainer() {
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            refreshTweets();
        });

        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /**
     * Refreshes and populates the latest tweets on the timeline
     */
    private void refreshTweets() {
        resetEndlessScroller();

        populateTimeline(START_PAGE);
    }

    /**
     * Fetches the time line information and updates the recycler view with the time line information
     * @param pageId
     */
    private void populateTimeline(long pageId) {

        mTwitterClient.getHomeTimeLine(pageId, new TextHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtil.logE(TAG, "Http request failure with status code: " + statusCode + ". " + throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logI(TAG, responseString);

                if(responseString != null) {
                    List<Tweet> tweets = TweetsUtil.getTweetsFromJson(responseString);

                    if (tweets != null) {
                        int currSize = mTimelineRecyclerViewAdapter.getItemCount();
                        mTweets.addAll(tweets);
                        mTimelineRecyclerViewAdapter.notifyItemRangeInserted(currSize, mTweets.size() - 1);
                    }

                    // Now we call setRefreshing(false) to signal refresh has finished
                    mSwipeContainer.setRefreshing(false);

                    LogUtil.logD(TAG, mTweets.toString());
                }
            }
        });
    }

    /**
     * Reset Endless Scroller
     */
    private void resetEndlessScroller() {
        mTimelineRecyclerViewAdapter.clear();
        // 3. Reset endless scroll listener when performing a new search
        mEndlessRecyclerViewScrollListener.resetState();
    }

    /**
     * On tweet success add the tweet to the 0th location and notify adapter and scroll into view
     * @param tweet
     */
    @Override
    public void onTweetSuccess(Tweet tweet) {
        mTweets.add(0, tweet);
        mTimelineRecyclerViewAdapter.notifyItemRangeInserted(0, 1);
        mLinearLayoutManager.scrollToPosition(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.logI(TAG, "ON activity result");

        if (resultCode == RESULT_OK && requestCode == 200) {
            // Extract name value from result extras
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra(EXTRA_TWEET));

            onTweetSuccess(tweet);
        }
    }
}
