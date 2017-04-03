package com.fetherz.saim.twitterredux.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.adapters.IconTabsAdapter;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.fragments.ComposeTweetFragment;
import com.fetherz.saim.twitterredux.fragments.HomeTimelineFragment;
import com.fetherz.saim.twitterredux.fragments.MessagesFragment;
import com.fetherz.saim.twitterredux.fragments.MomentsFragment;
import com.fetherz.saim.twitterredux.fragments.NotificationsFragment;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.services.TwitterClient;
import com.fetherz.saim.twitterredux.utils.CircleTransform;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.fetherz.saim.twistertwit.R.string.tweet;
import static com.fetherz.saim.twitterredux.activities.DetailActivity.EXTRA_TWEET;
import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

/**
 * Created by sm032858 on 4/1/17.
 */

public class HomeActivity extends BaseActivity implements HomeTimelineFragment.OnFragmentInteractionListener,
        MomentsFragment.OnFragmentInteractionListener, NotificationsFragment.OnFragmentInteractionListener,
        MessagesFragment.OnFragmentInteractionListener, ComposeTweetFragment.ComposeTweetListener  {

    private static final String TAG = "HomeActivity" ;

    private List<Fragment> mFragments = new ArrayList<>();

    @BindView(R.id.vpHomeViews)
    ViewPager mVPHomeViewPager;

    @BindView(R.id.tlHomeTabs)
    TabLayout mTLHomeTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fabComposeTweet)
    FloatingActionButton mFabComposeTweet;

    User mCurrentUser;

    TwitterClient mTwitterClient;

    IconTabsAdapter mHomeTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(HomeActivity.this);

        mTwitterClient = TwitterApplication.getRestClient();

        //set up the toolbar as action bar
        setSupportActionBar(mToolbar);

        setUser();

        prepareFragments();

        bindTabsToFragments();

        setTabIcons();//Set the icons to the Tabs
    }


    private void bindTabsToFragments() {
        mHomeTabAdapter = new IconTabsAdapter(getSupportFragmentManager(), mFragments);

        //Bind the Adapter to the View Pager
        mVPHomeViewPager.setAdapter(mHomeTabAdapter);

        //Link View Pager and Tab Layout
        mTLHomeTabLayout.setupWithViewPager(mVPHomeViewPager);
    }

    private void setTabIcons() {
        mTLHomeTabLayout.getTabAt(0).setIcon(R.drawable.ic_home_selected);
        mTLHomeTabLayout.getTabAt(1).setIcon(R.drawable.ic_moments);
        mTLHomeTabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications);
        mTLHomeTabLayout.getTabAt(3).setIcon(R.drawable.ic_messages);
    }

    private void prepareFragments() {
        mFragments.add(HomeTimelineFragment.newInstance(mCurrentUser));
        mFragments.add(MomentsFragment.newInstance(mCurrentUser));
        mFragments.add(NotificationsFragment.newInstance(mCurrentUser));
        mFragments.add(MessagesFragment.newInstance(mCurrentUser));
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search Twitter");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchQuery) {
                if(searchQuery!= null && !searchQuery.trim().isEmpty()) {
                    Intent intent = TimelineActivity.newIntent(HomeActivity.this);
                    intent.putExtra(TimelineActivity.EXTRA_SEARCH_QUERY, searchQuery);
                    startActivity(intent);
                }

                Log.d(LOG_TAG, "Tweet selected: " + tweet);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the current user in the local cache
     */
    private void setUser() {
        mTwitterClient.getUser(new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtil.logE(TAG, "Http request failure with status code: " + statusCode + ". " + throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logI(TAG, responseString);

                if (responseString != null) {
                    mCurrentUser = TweetsUtil.getUserFromJson(responseString);

                    final Toolbar ab = mToolbar;
                    Picasso.with(HomeActivity.this)
                            .load(mCurrentUser.getProfileImageUrl())
                            .transform(new CircleTransform())
                            .into(new Target()
                            {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                                {
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                                    ab.setLogo(d);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable)
                                {
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable)
                                {
                                }
                            });

                    LogUtil.logD(TAG, mCurrentUser.toString());
                }
            }
        });
    }

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTweetSuccess(Tweet tweet) {
        int position = mTLHomeTabLayout.getSelectedTabPosition();
        Fragment currentFragment = mHomeTabAdapter.getItem(position);
        if (currentFragment != null) {
            switch (position) { //the following logic is based on the view pager behavior of infalting the views next to the currently selected view
                case 0:
                    ((HomeTimelineFragment) currentFragment).onRefreshTweets(tweet);
                break;
                case 1:
                    if(mHomeTabAdapter.getItem(position - 1) != null) {
                        ((HomeTimelineFragment) mHomeTabAdapter.getItem(position - 1)).onRefreshTweets(tweet);
                    }
                    if(mHomeTabAdapter.getItem(position + 1) != null) {
                        ((NotificationsFragment) mHomeTabAdapter.getItem(position + 1)).onRefreshTweets(tweet);
                    }
                    ((MomentsFragment) currentFragment).onRefreshTweets(tweet);
                break;
                case 2:
                    ((NotificationsFragment) currentFragment).onRefreshTweets(tweet);
                    if(mHomeTabAdapter.getItem(position - 1) != null) {
                        ((MomentsFragment) mHomeTabAdapter.getItem(position - 1)).onRefreshTweets(tweet);
                    }
                break;
                case 3:
                    if(mHomeTabAdapter.getItem(position - 1) != null) {
                        ((NotificationsFragment) mHomeTabAdapter.getItem(position - 1)).onRefreshTweets(tweet);
                    }
                break;
            }
        }
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
