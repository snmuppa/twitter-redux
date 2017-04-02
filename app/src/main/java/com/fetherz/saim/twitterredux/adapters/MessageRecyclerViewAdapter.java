package com.fetherz.saim.twitterredux.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.models.client.Message;
import com.fetherz.saim.twitterredux.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sm032858 on 4/1/17.
 */

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Message> mMessages;
    Context mContext;

    public MessageRecyclerViewAdapter(Context context, List<Message> messages){
        this.mContext = context;
        this.mMessages = messages;
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

        View messageView = inflater.inflate(R.layout.direct_message, parent, false);
        viewHolder = new MessageViewHolder(mContext, messageView, mMessages);

        return viewHolder;
    }

    /**
     * Bind the view holder based on the view type
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        bindMessageViewHolder(messageViewHolder, position);
    }

    private void bindMessageViewHolder(MessageViewHolder messageViewHolder, int position) {
        Message message = mMessages.get(position);

        if(message != null) {
            Picasso.with(mContext).load(message.getSender().getProfileImageUrl()).
                    placeholder(R.drawable.ic_photo).
                    error(R.drawable.ic_camera).
                    transform(new CircleTransform()).into(messageViewHolder.mIvProfilePicture);

            messageViewHolder.mTvCreatedAt.setText(message.getCreatedAt());

            messageViewHolder.mTvUserName.setText(message.getSender().getName());
            messageViewHolder.mTvScreenName.setText(message.getSender().getScreenName());
            messageViewHolder.mTvMessageText.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return this.mMessages.size();
    }

    /**
     * Clear all the messages
     */
    public void clear() {
        mMessages.clear();
        notifyDataSetChanged();
    }

    /***
     * Adds a list of messages
     * @param messages
     */
    public void addAll(List<Message> messages) {
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }
}
