package com.fetherz.saim.twitterredux.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.models.client.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sm032858 on 4/3/17.
 */

class FollowViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivProfilePicture)
    ImageView mIvProfilePicture;

    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    @BindView(R.id.tvScreenName)
    TextView mTvScreenName;

    @BindView(R.id.btnFollow)
    ImageButton mBtnFollow;

    @BindView(R.id.tvMessageText)
    TextView mTvMessageText;

    List<User> mUsers;
    Context mContext;

    public FollowViewHolder(Context context, View itemView, List<User> users) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mUsers = users;
        mContext = context;
    }
}
