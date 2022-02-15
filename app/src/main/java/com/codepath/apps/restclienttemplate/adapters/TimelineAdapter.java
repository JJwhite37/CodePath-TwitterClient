package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.models.Tweets;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder>{
    Context context;
    List<Tweets> tweets;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TimelineAdapter(Context context, List<Tweets> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(timelineView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweets tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTextBody;
        TextView tvUserName;
        TextView tvDate;
        ImageView ivProfilePic;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            tvTextBody = itemView.findViewById(R.id.tvTextBody);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(Tweets tweets) {
            tvTextBody.setText(tweets.body);
            tvUserName.setText(tweets.user.userName);
            tvDate.setText("- " + TimeFormatter.getTimeDifference(tweets.dateCreated));
            Glide.with(context)
                    .load(tweets.user.profilePicUrl)
                    .transform(new CropCircleTransformation())
                    .into(ivProfilePic);
        }
    }
}

