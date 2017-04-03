package com.fetherz.saim.twitterredux.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.adapters.FollowsRecyclerViewAdapter;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.eventlisteners.EndlessRecyclerViewScrollListener;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.services.TwitterClient;
import com.fetherz.saim.twitterredux.utils.GenericUtil;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FollowActivity extends AppCompatActivity {

    public static final String FOLLOWERS_TYPE = "followers";
    public static final String FOLLOWING_TYPE = "following";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvFollows)
    RecyclerView mRvFollows;

    @BindView(R.id.srContainer)
    SwipeRefreshLayout mSwipeContainer;

    private String mScreenName;
    private String mFollowType;

    public static final String EXTRA_SCREEN_NAME = "screenName";
    public static final String EXTRA_FOLLOW_TYPE ="followType" ;

    static final String TAG = "FollowActivity";
    static final short START_PAGE = -1;

    long mPageId;

    TwitterClient mTwitterClient;

    List<User> mUsers;
    FollowsRecyclerViewAdapter mFollowsRecyclerViewAdapter;
    EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    LinearLayoutManager mLinearLayoutManager;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FollowActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        ButterKnife.bind(FollowActivity.this);

        mScreenName = getIntent().getStringExtra(EXTRA_SCREEN_NAME);

        mFollowType = getIntent().getStringExtra(EXTRA_FOLLOW_TYPE);

        mTwitterClient = TwitterApplication.getRestClient();

        initializeViews();

        populateFollows(START_PAGE);
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
        mUsers = new ArrayList<>();

        // Create adapter passing in the initial tweet data
        mFollowsRecyclerViewAdapter = new FollowsRecyclerViewAdapter(FollowActivity.this, mUsers, mFollowType);

        // Attach the adapter to the recyclerview to populate items
        mRvFollows.setAdapter(mFollowsRecyclerViewAdapter);

        mLinearLayoutManager =
                new LinearLayoutManager(FollowActivity.this);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set layout manager to position the items
        mRvFollows.setLayoutManager(mLinearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateFollows(mPageId); //twitter API page numbers start from 1
            }
        };

        // Adds the scroll listener to RecyclerView
        mRvFollows.addOnScrollListener(mEndlessRecyclerViewScrollListener);
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
            refreshUsers();
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
    private void refreshUsers() {
        resetEndlessScroller();

        populateFollows(START_PAGE);
    }

    /**
     * Fetches the time line information and updates the recycler view with the time line information
     * @param pageId
     */
    private void populateFollows(long pageId) {
        if(GenericUtil.isMatch(mFollowType, FOLLOWERS_TYPE)) {
            mTwitterClient.getFollowers(mScreenName, pageId, textHttpResponseHandler);
        }else{
            mTwitterClient.getFollowing(mScreenName, pageId, textHttpResponseHandler);
        }
    }

    JsonHttpResponseHandler textHttpResponseHandler = new JsonHttpResponseHandler(){

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            LogUtil.logE(TAG, "Http request failure with status code: " + statusCode + ". " + throwable.getMessage(), throwable);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject responseObject) {
            LogUtil.logI(TAG, String.valueOf(responseObject));

            if(responseObject != null) {

                JSONArray jsonArray = null;
                try {
                    jsonArray = responseObject.getJSONArray("users");
                    mPageId = responseObject.getLong("next_cursor");
                } catch (JSONException e) {
                    LogUtil.logE(TAG, e.getMessage(), e);
                }

                if(jsonArray != null) {
                    List<User> users = TweetsUtil.getUsersFromJson(String.valueOf(jsonArray));

                    if (users != null) {
                        int currSize = mFollowsRecyclerViewAdapter.getItemCount();
                        mUsers.addAll(users);
                        mFollowsRecyclerViewAdapter.notifyItemRangeInserted(currSize, mUsers.size() - 1);
                    }

                    // Now we call setRefreshing(false) to signal refresh has finished
                    mSwipeContainer.setRefreshing(false);
                }

                LogUtil.logD(TAG, mUsers.toString());
            }
        }
    };

    /**
     * Reset Endless Scroller
     */
    private void resetEndlessScroller() {
        mFollowsRecyclerViewAdapter.clear();
        // 3. Reset endless scroll listener when performing a new search
        mEndlessRecyclerViewScrollListener.resetState();
    }
}
