package com.fetherz.saim.twitterredux.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.models.client.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sm032858 on 4/1/17.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    @BindView(R.id.ivProfilePicture)
    ImageView mIvProfilePicture;

    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    @BindView(R.id.tvScreenName)
    TextView mTvScreenName;

    @BindView(R.id.tvCreatedAt)
    TextView mTvCreatedAt;

    @BindView(R.id.tvMessageText)
    TextView mTvMessageText;

    List<Message> mMessages;
    Context mContext;

    public MessageViewHolder(Context context, View itemView, List<Message> messages) {
        super(itemView);

        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);

        mMessages = messages;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        //TODO: add some click functionality
    }
}
