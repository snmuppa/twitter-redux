package com.fetherz.saim.twitterredux.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.services.TwitterClient;
import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class ComposeTweetFragment extends DialogFragment {
    /**
     * Define the events that the fragment will use to communicate
     */
    public interface ComposeTweetListener {
        // This can be any number of events to be sent to the activity
        public void onTweetSuccess(Tweet tweet);
    }

    @BindView(R.id.ivClose)
    ImageView mIvClose;

    @BindView(R.id.ivComposeUserProfilePicture)
    DynamicHeightImageView mIvComposeUserProfilePicture;

    @BindView(R.id.etTweet)
    EditText mEtTweet;

    @BindView(R.id.btnTweet)
    Button mBtnTweet;

    @BindView(R.id.tvTweetLength)
    TextView mTvTweetLength;

    TwitterClient mTwitterClient;

    User mCurrentUser;
    View mCurrentView;

    static final String TAG = "ComposeTweetFragment";

    /**
     * Define the listener of the interface type
     * listener will the activity instance containing fragment
     */
    private ComposeTweetListener mTweetListener;

    /**
     *
     */
    public ComposeTweetFragment(){}

    /**
     *
     * @return
     * @param currentUser
     */
    public static ComposeTweetFragment newInstance(User currentUser){
        ComposeTweetFragment composeTweetFragment = new ComposeTweetFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("currentUser", Parcels.wrap(currentUser));

        composeTweetFragment.setArguments(bundle);
        return composeTweetFragment;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstance
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_compose_tweet, container);
        ButterKnife.bind(this, view);

        mTwitterClient = TwitterApplication.getRestClient();

        mCurrentView = view;
        return view;
    }

    @Override
    public void onViewCreated(View  view, Bundle savedInstance){
        mCurrentUser = Parcels.unwrap(getArguments().getParcelable("currentUser"));

        setProfileImage();

        setTweetTextListener();
    }

    /**
     * Sets the profile image
     */
    private void setProfileImage() {
        if(mCurrentUser!= null && mCurrentUser.getProfileImageUrl() != null) {
            //Glide.with(getContext()).load(mCurrentUser.getProfileImageUrl())
            //        .placeholder(R.drawable.ic_photo)
            //        .error(R.drawable.ic_camera)
            //        .fitCenter()
            //        .into(mIvComposeUserProfilePicture);

            Picasso.with(getContext()).load(mCurrentUser.getProfileImageUrl())
                    .transform(new RoundedCornersTransformation(2,2))
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_camera)
                    .into(mIvComposeUserProfilePicture);
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
     * Store the listener (activity) that will have events fired once the fragment is attached
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComposeTweetListener) {
            mTweetListener = (ComposeTweetListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ComposeTweetFragment.ComposeTweetListener");
        }
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

                Snackbar.make(mCurrentView, "Error occured while composing a new Tweet.", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logI(TAG, responseString);

                if(responseString != null) {
                    Tweet tweet = TweetsUtil.getTweetFromJson(responseString);

                    LogUtil.logD(TAG, tweet.toString());

                    dismiss();

                    mTweetListener.onTweetSuccess(tweet); //pass the updated instance of the FilterSelection class

                }
            }
        });
    }

    @OnClick(R.id.ivClose)
    public void onDismiss(){
        dismiss();
    }

    /**
     *
     */
    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.90), (int) (size.y * 0.80));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
