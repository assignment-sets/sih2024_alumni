package com.example.alumnihub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Event;
import com.example.alumnihub.data_models.User;
import com.example.alumnihub.utils.ImagePickerUtil;
import com.example.alumnihub.utils.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class EventPostAdapter extends RecyclerView.Adapter<EventPostAdapter.EventPostViewHolder> {
    private Context context;
    private List<Event> eventPostsList;
    private UserServicesDB userServicesDB;

    public EventPostAdapter(Context context, List<Event> eventPostsList) {
        this.context = context;
        this.eventPostsList = eventPostsList;
        this.userServicesDB = new UserServicesDB();
    }

    @NonNull
    @Override
    public EventPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventPostViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_event_posts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventPostViewHolder holder, int position) {
        Event event = eventPostsList.get(position);
        holder.eventPostTitle.setText(TextUtils.capitalizeFirstLetter(event.getTitle()));
        holder.eventPostDescription.setText(TextUtils.capitalizeFirstLetter(event.getDescription()));
        holder.eventPostSelectedMode.setText("Mode: " + TextUtils.capitalizeFirstLetter(event.getMode()));
        holder.eventPostPlatform.setText(TextUtils.capitalizeFirstLetter(event.getPlatform()));
        holder.eventPostVenue.setText(TextUtils.capitalizeFirstLetter(event.getVenue()));

        // Use ImagePickerUtil to load the image into the ImageView
        ImagePickerUtil.loadImageIntoView(context, event.getAvatarUrl(), holder.eventPostImage);

        // Fetch the user object and set the username
        userServicesDB.getUser(event.getUserId()).addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    if (user != null) {
                        holder.eventPostUsername.setText("Posted by " + user.getUserName());
                    } else {
                        holder.eventPostUsername.setText("Posted by Anon User");
                    }
                } else {
                    holder.eventPostUsername.setText("Posted by Anon User");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventPostsList.size();
    }

    public static class EventPostViewHolder extends RecyclerView.ViewHolder {
        public TextView eventPostTitle, eventPostDescription, eventPostSelectedMode, eventPostPlatform, eventPostVenue, eventPostUsername;
        public ImageView eventPostImage;

        public EventPostViewHolder(@NonNull View itemView) {
            super(itemView);
            eventPostTitle = itemView.findViewById(R.id.event_post_title);
            eventPostDescription = itemView.findViewById(R.id.event_post_description);
            eventPostSelectedMode = itemView.findViewById(R.id.event_post_select_mode);
            eventPostPlatform = itemView.findViewById(R.id.event_post_platform);
            eventPostVenue = itemView.findViewById(R.id.event_post_venue);
            eventPostImage = itemView.findViewById(R.id.event_post_image);
            eventPostUsername = itemView.findViewById(R.id.event_post_username);
        }
    }
}