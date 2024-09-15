package com.example.alumnihub.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnihub.R;
import com.example.alumnihub.data_models.Event;

import java.util.List;

public class EventPostAdapter extends RecyclerView.Adapter<EventPostAdapter.EventPostViewHolder> {
    Context context;
    List<Event> eventPostsList;

    public EventPostAdapter(Context context, List<Event> eventPostsList) {
        this.context = context;
        this.eventPostsList = eventPostsList;
    }

    @NonNull
    @Override
    public EventPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventPostViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_event_posts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventPostViewHolder holder, int position) {
        holder.eventPostTitle.setText(eventPostsList.get(position).getTitle());
        holder.eventPostDescription.setText(eventPostsList.get(position).getDescription());
        holder.eventPostSelectedMode.setText(eventPostsList.get(position).getMode());
        holder.eventPostPlatform.setText(eventPostsList.get(position).getPlatform());
        holder.eventPostVenue.setText(eventPostsList.get(position).getVenue());
        holder.eventPostImage.setImageURI(Uri.parse(eventPostsList.get(position).getAvatarUrl()));
    }

    @Override
    public int getItemCount() {
        return eventPostsList.size();
    }

    public class EventPostViewHolder extends RecyclerView.ViewHolder{
        public TextView eventPostTitle,eventPostDescription,eventPostSelectedMode,eventPostPlatform,eventPostVenue;
        public ImageView eventPostImage;
        public EventPostViewHolder(@NonNull View itemView) {
            super(itemView);
            eventPostTitle = itemView.findViewById(R.id.event_post_title);
            eventPostDescription = itemView.findViewById(R.id.event_post_description);
            eventPostSelectedMode = itemView.findViewById(R.id.event_post_select_mode);
            eventPostPlatform = itemView.findViewById(R.id.event_post_platform);
            eventPostVenue = itemView.findViewById(R.id.event_post_venue);
            eventPostImage = itemView.findViewById(R.id.event_post_image);
        }
    }
}
