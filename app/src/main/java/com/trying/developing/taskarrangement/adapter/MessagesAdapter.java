package com.trying.developing.taskarrangement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by developing on 5/1/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesHolder> {
    Context mContext;
    List<Chat> data=new ArrayList<>();

    public MessagesAdapter(Context mContext, List<Chat> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public MessagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chattinglistitem,parent,false);

        return new  MessagesHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesHolder holder, int position) {
        Chat chat=data.get(position);
        holder.messages.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MessagesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.messagesTextView)
        TextView messages;

        public MessagesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
