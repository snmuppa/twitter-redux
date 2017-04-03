package com.fetherz.saim.twitterredux.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.activities.FollowActivity;
import com.fetherz.saim.twitterredux.models.client.User;
import com.fetherz.saim.twitterredux.utils.CircleTransform;
import com.fetherz.saim.twitterredux.utils.GenericUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.fetherz.saim.twitterredux.activities.FollowActivity.FOLLOWERS_TYPE;

/**
 * Created by sm032858 on 4/3/17.
 */

public class FollowsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    List<User> mUsers;
    Context mContext;
    String mFollowType;

    public FollowsRecyclerViewAdapter(Context context, List<User> users, String followType){
        this.mContext = context;
        this.mUsers = users;
        this.mFollowType = followType;
    }

    /**
     * Inflate the view holder based on the view type
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View messageView = inflater.inflate(R.layout.follow_base, parent, false);
        viewHolder = new FollowViewHolder(mContext, messageView, mUsers);

        return viewHolder;
    }

    /**
     * Bind the view holder based on the view type
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FollowViewHolder followViewHolder = (FollowViewHolder) holder;
        bindMessageViewHolder(followViewHolder, position);
    }

    private void bindMessageViewHolder(FollowViewHolder followViewHolder, int position) {
        User user = mUsers.get(position);

        if(user != null) {
            Picasso.with(mContext).load(user.getProfileImageUrl()).
                    placeholder(R.drawable.ic_photo).
                    error(R.drawable.ic_camera).
                    transform(new CircleTransform()).into(followViewHolder.mIvProfilePicture);

            if(GenericUtil.isMatch(mFollowType, FollowActivity.FOLLOWERS_TYPE) && !user.isUserFollowing()) {
                    followViewHolder.mBtnFollow.setBackgroundResource(R.drawable.btn_connect);
                    followViewHolder.mBtnFollow.setImageResource(R.drawable.ic_connect);
            }
            else {
                followViewHolder.mBtnFollow.setBackgroundResource(R.drawable.btn_edit_profile);
                followViewHolder.mBtnFollow.setImageResource(R.drawable.ic_following);
            }

            followViewHolder.mTvUserName.setText(user.getName());
            followViewHolder.mTvScreenName.setText(user.getScreenName());
            if(user.getDescription()!= null) {
                followViewHolder.mTvMessageText.setText(user.getDescription());
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.mUsers.size();
    }

    /**
     * Clear all the messages
     */
    public void clear() {
        mUsers.clear();
        notifyDataSetChanged();
    }

    /***
     * Adds a list of messages
     * @param messages
     */
    public void addAll(List<User> users) {
        mUsers.addAll(users);
        notifyDataSetChanged();
    }
}
