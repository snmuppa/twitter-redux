package com.fetherz.saim.twitterredux.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.models.client.Tweet;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.models.utils.TweetsUtil;
import com.fetherz.saim.twitterredux.utils.GenericUtil;
import com.fetherz.saim.twitterredux.utils.LogUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.parceler.Parcels;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends TweetsListFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CURRENT_USER = "currentUser";

    User mCurrentUser;
    View mCurrentView;

    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    public NotificationsFragment() {
        // Required empty public constructor

        LogUtil.logI("Fragment Check", "NotificationsFragment");
    }

    /**
     *
     * @param currentUser
     * @return
     */
    public static NotificationsFragment newInstance(User currentUser){
        NotificationsFragment notificationsFragment = new NotificationsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CURRENT_USER, Parcels.wrap(currentUser));

        notificationsFragment.setArguments(bundle);
        return notificationsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = Parcels.unwrap(getArguments().getParcelable(ARG_CURRENT_USER));
    }

    @Override
    protected void populateTimeline(long pageId) {

        pageId = getTwitterPageId(pageId);

        mTwitterClient.getMentionsTimeLine(pageId, new TextHttpResponseHandler(){

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

    @Override
    public void onRefreshTweets(Tweet tweet) {
        if(tweet != null && tweet.getUser() != null && GenericUtil.isMatch(tweet.getText(), tweet.getUser().getScreenName())) { //add only current user mentioned tweets
            mTweets.add(0, tweet);
            mTimelineRecyclerViewAdapter.notifyItemRangeInserted(0, 1);
            mLinearLayoutManager.scrollToPosition(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        mCurrentView = view;

        unbinder = ButterKnife.bind(this, view);

        setTimelineRecyclerView();

        setSwipeRefreshContainer();

        populateTimeline(START_PAGE);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
