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
import com.example.alumnihub.data_models.SocialPost;

import java.util.List;

public class SocialPostAdapter extends RecyclerView.Adapter<SocialPostAdapter.SocialPostViewHolder> {
    Context context;
    List<SocialPost> socialPostList;

    public SocialPostAdapter(Context context, List<SocialPost> socialPostList) {
        this.context = context;
        this.socialPostList = socialPostList;
    }

    @NonNull
    @Override
    public SocialPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SocialPostViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_social_posts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SocialPostViewHolder holder, int position) {
        holder.socialPostImage.setImageURI(Uri.parse(socialPostList.get(position).getImageUrl()));
        holder.socialPostCaption.setText(socialPostList.get(position).getCaption());
    }

    @Override
    public int getItemCount() {
        return socialPostList.size();
    }

    public class SocialPostViewHolder extends RecyclerView.ViewHolder{
        public ImageView socialPostImage;
        public TextView socialPostCaption;
        public SocialPostViewHolder(@NonNull View itemView) {
            super(itemView);
            socialPostImage = itemView.findViewById(R.id.user_social_posts_image);
            socialPostCaption = itemView.findViewById(R.id.user_social_posts_caption);
        }
    }
}
