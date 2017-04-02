package com.fetherz.saim.twitterredux.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.adapters.MessageRecyclerViewAdapter;
import com.fetherz.saim.twitterredux.application.TwitterApplication;
import com.fetherz.saim.twitterredux.eventlisteners.EndlessRecyclerViewScrollListener;
import com.fetherz.saim.twitterredux.models.client.Message;
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
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

import static com.fetherz.saim.twitterredux.fragments.TweetsListFragment.START_PAGE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CURRENT_USER = "currentUser";
    private static final String TAG = "MessagesFragment" ;

    User mCurrentUser;
    View mCurrentView;

    private Unbinder unbinder;

    @BindView(R.id.rvMessages)
    RecyclerView mRvMessages;

    @BindView(R.id.srContainer)
    SwipeRefreshLayout mSwipeContainer;

    MessageRecyclerViewAdapter mMessageRecyclerViewAdapter;
    EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    LinearLayoutManager mLinearLayoutManager;
    List<Message> mMessages;

    TwitterClient mTwitterClient;

    private OnFragmentInteractionListener mListener;

    public MessagesFragment() {
        // Required empty public constructor

        LogUtil.logI("Fragment Check", "MessagesFragment");
    }

    /**
     *
     * @param currentUser
     * @return
     */
    public static MessagesFragment newInstance(User currentUser){
        MessagesFragment messagesFragment = new MessagesFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CURRENT_USER, Parcels.wrap(currentUser));

        messagesFragment.setArguments(bundle);
        return messagesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = Parcels.unwrap(getArguments().getParcelable(ARG_CURRENT_USER));

        mTwitterClient = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        mCurrentView = view;

        unbinder = ButterKnife.bind(this, view);

        setTimelineRecyclerView();

        setSwipeRefreshContainer();

        populateSentMessages(START_PAGE);
        populateReceivedMessages(START_PAGE);

        return view;
    }

    private void populateSentMessages(long startPage) {
    }

    private void populateReceivedMessages(long startPage) {
        mTwitterClient.getReceivedDirectMessages(startPage, new TextHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtil.logE(TAG, "Http request failure with status code: " + statusCode + ". " + throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logI(TAG, responseString);

                if(responseString != null) {
                    List<Message> messages = TweetsUtil.getMessagesFromJson(responseString);

                    if (messages != null) {
                        int currSize = mMessageRecyclerViewAdapter.getItemCount();
                        mMessages.addAll(messages);
                        mMessageRecyclerViewAdapter.notifyItemRangeInserted(currSize, mMessages.size());
                    }

                    // Now we call setRefreshing(false) to signal refresh has finished
                    mSwipeContainer.setRefreshing(false);

                    LogUtil.logD(TAG, messages.toString());
                }
            }
        });
    }

    /**
     * Reset Endless Scroller
     */
    private void resetEndlessScroller() {
        mMessageRecyclerViewAdapter.clear();
        // 3. Reset endless scroll listener when performing a new search
        mEndlessRecyclerViewScrollListener.resetState();
    }

    /**
     * Refreshes and populates the latest messages
     */
    private void refreshMessages() {
        resetEndlessScroller();

        populateReceivedMessages(START_PAGE);
        populateSentMessages(START_PAGE);
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
            refreshMessages();
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
        mMessages = new ArrayList<>();

        // Create adapter passing in the initial message data
        mMessageRecyclerViewAdapter = new MessageRecyclerViewAdapter(getActivity(), mMessages);

        // Attach the adapter to the recyclerview to populate items
        mRvMessages.setAdapter(mMessageRecyclerViewAdapter);

        mLinearLayoutManager =
                new LinearLayoutManager(getActivity());

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set layout manager to position the items
        mRvMessages.setLayoutManager(mLinearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateReceivedMessages(page); //twitter API page numbers start from 1

                populateSentMessages(page);
            }
        };

        // Adds the scroll listener to RecyclerView
        mRvMessages.addOnScrollListener(mEndlessRecyclerViewScrollListener);
    }

    public void appendMessages(List<Message> messages) {
        mMessages.addAll(0, messages);
        mMessageRecyclerViewAdapter.notifyItemRangeInserted(0, 1);
        mLinearLayoutManager.scrollToPosition(0);
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
