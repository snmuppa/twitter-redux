package com.fetherz.saim.twitterredux.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.adapters.TextTabsAdapter;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.fragments.LikesFragment;
import com.fetherz.saim.twitterredux.fragments.MediaFragment;
import com.fetherz.saim.twitterredux.fragments.UserTimelineFragment;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.services.TwitterClient;
import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.fetherz.saim.twistertwit.R.id.ivBackDrop;

public class UserProfileActivity extends AppCompatActivity implements UserTimelineFragment.OnFragmentInteractionListener,
MediaFragment.OnFragmentInteractionListener, LikesFragment.OnFragmentInteractionListener{

    private static final String TAG = "UserProfileActivity" ;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTabTitles = new ArrayList<>();

    @BindView(R.id.vpUserProfileViews)
    ViewPager mVPUserProfilePager;

    @BindView(R.id.tlUserProfileTabs)
    TabLayout mTLUserProfileTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(ivBackDrop)
    DynamicHeightImageView mIvBackDrop;

    @BindView(R.id.ivUserProfilePicture)
    DynamicHeightImageView mIvUserProfilePicture;

    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    @BindView(R.id.ivVerified)
    ImageView mIvVerified;

    @BindView(R.id.tvUserScreenName)
    TextView mTvUserScreenName;

    @BindView(R.id.tvUserDescription)
    TextView mTvUserDescription;

    @BindView(R.id.tvFollowingCount)
    TextView mTvFollowingCount;

    @BindView(R.id.tvFollowersCount)
    TextView mTvFollowersCount;

    User mCurrentUser;

    TwitterClient mTwitterClient;

    TextTabsAdapter mUserProfileTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ButterKnife.bind(UserProfileActivity.this);

        mTwitterClient = TwitterApplication.getRestClient();

        //set up the toolbar as action bar
        setSupportActionBar(mToolbar);

        setUser();
    }

    private void bindTabsToFragments() {
        mUserProfileTabAdapter = new TextTabsAdapter(getSupportFragmentManager(), mFragments, mTabTitles);

        //Bind the Adapter to the View Pager
        mVPUserProfilePager.setAdapter(mUserProfileTabAdapter);

        //Link View Pager and Tab Layout
        mTLUserProfileTabLayout.setupWithViewPager(mVPUserProfilePager);
    }

    // Let's prepare Data for our Tabs - Fragments and Title List
    private void prepareFragments() {

        addData(UserTimelineFragment.newInstance(mCurrentUser, true), getString(R.string.tweets_tab_text));
        addData(UserTimelineFragment.newInstance(mCurrentUser, false), getString(R.string.tweets_and_replies_tab_text));
        addData(new MediaFragment(), getString(R.string.media_tab_text));
        addData(new LikesFragment(), getString(R.string.likes_tab_text));
    }

    private void addData(Fragment fragment, String title) {
        mFragments.add(fragment);
        mTabTitles.add(title);
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
                    prepareFragments();
                    bindTabsToFragments();

                    if(mCurrentUser != null) {
                        if (mCurrentUser.getProfileBackgroundImageUrl() != null) {
                            Picasso.with(UserProfileActivity.this).load(mCurrentUser.getProfileBackgroundImageUrl())
                                    .into(mIvBackDrop);
                        }

                        Picasso.with(UserProfileActivity.this).load(mCurrentUser.getProfileImageUrl())
                                .transform(new RoundedCornersTransformation(2,2))
                                .placeholder(R.drawable.ic_photo)
                                .error(R.drawable.ic_camera)
                                .into(mIvUserProfilePicture);

                        mTvUserName.setText(mCurrentUser.getName());

                        if(mCurrentUser.isVerified()){
                            mIvVerified.setVisibility(View.VISIBLE);
                        }

                        mTvUserScreenName.setText(mCurrentUser.getScreenName());

                        mTvUserDescription.setText(mCurrentUser.getDescription());

                        mTvFollowingCount.setText(Integer.toString(mCurrentUser.getFriendsCount()));

                        mTvFollowersCount.setText(Integer.toString(mCurrentUser.getFollowersCount()));

                    }

                    LogUtil.logD(TAG, mCurrentUser.toString());
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
