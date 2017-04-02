package com.fetherz.saim.twitterredux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.adapters.TimelineRecyclerViewAdapter;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.eventlisteners.EndlessRecyclerViewScrollListener;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.services.TwitterClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by sm032858 on 4/1/17.
 */

public abstract class TweetsListFragment extends Fragment {

    @BindView(R.id.rvTweets)
    RecyclerView mRvArticles;

    @BindView(R.id.srContainer)
    SwipeRefreshLayout mSwipeContainer;

    TimelineRecyclerViewAdapter mTimelineRecyclerViewAdapter;
    EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    LinearLayoutManager mLinearLayoutManager;
    List<Tweet> mTweets;

    TwitterClient mTwitterClient;

    static final String TAG = "TweetsListFragment";
    static final short START_PAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTwitterClient = TwitterApplication.getRestClient();
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
     * Refreshes and populates the latest tweets on the timeline
     */
    private void refreshTweets() {
        resetEndlessScroller();

        populateTimeline(START_PAGE);
    }

    /**
     * Set's up the swipe refresh layout for the tweets time line
     */
    void setSwipeRefreshContainer() {
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
     * Initializes the timeline recycler view
     */
    void setTimelineRecyclerView() {
        mTweets = new ArrayList<>();

        // Create adapter passing in the initial tweet data
        mTimelineRecyclerViewAdapter = new TimelineRecyclerViewAdapter(getActivity(), mTweets);

        // Attach the adapter to the recyclerview to populate items
        mRvArticles.setAdapter(mTimelineRecyclerViewAdapter);

        mLinearLayoutManager =
                new LinearLayoutManager(getActivity());

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set layout manager to position the items
        mRvArticles.setLayoutManager(mLinearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateTimeline(page + 1); //twitter API page numbers start from 1
            }
        };

        // Adds the scroll listener to RecyclerView
        mRvArticles.addOnScrollListener(mEndlessRecyclerViewScrollListener);
    }

    public void appendTweets(List<Tweet> tweets) {
        mTweets.addAll(0, tweets);
        mTimelineRecyclerViewAdapter.notifyItemRangeInserted(0, 1);
        mLinearLayoutManager.scrollToPosition(0);
    }

    long getTwitterPageId(long pageId) {
        if(pageId >= 1){
            int currSize = mTimelineRecyclerViewAdapter.getItemCount();
            if(currSize >= 1){
                pageId = mTweets.get(currSize - 1).getTweetId();
            }
        }

        return pageId;
    }

    // Abstract method to be overridden
    protected abstract void populateTimeline(long maxId);
}
