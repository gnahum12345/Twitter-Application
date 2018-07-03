package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.net.URI;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    // Tweets array in the constructor
    // for each row, inflate the layout and cache references into viewholder

    private List<Tweet> mTweetList;
    private Context mContext;
    public TweetAdapter(List<Tweet> tweets) {
        mTweetList = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);

        View tweetView = inflate.inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = mTweetList.get(position);
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        Glide.with(mContext)
                .load(tweet.user.profileURL)
                .into(holder.ivProfilePic);

    }

    @Override
    public int getItemCount() {
        return mTweetList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfilePic;
        public TextView tvUserName;
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfilePic = itemView.findViewById(R.id.ivProfileImage);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
        }
    }
}
