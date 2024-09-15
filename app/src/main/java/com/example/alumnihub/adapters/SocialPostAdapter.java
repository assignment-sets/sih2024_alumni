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
import com.example.alumnihub.data_models.SocialPost;
import com.example.alumnihub.data_models.User;
import com.example.alumnihub.utils.ImagePickerUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SocialPostAdapter extends RecyclerView.Adapter<SocialPostAdapter.SocialPostViewHolder> {
    private Context context;
    private List<SocialPost> socialPostList;
    private UserServicesDB userServicesDB;

    public SocialPostAdapter(Context context, List<SocialPost> socialPostList) {
        this.context = context;
        this.socialPostList = socialPostList;
        this.userServicesDB = new UserServicesDB();
    }

    @NonNull
    @Override
    public SocialPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_social_posts, parent, false);
        return new SocialPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialPostViewHolder holder, int position) {
        SocialPost socialPost = socialPostList.get(position);
        if (socialPost != null) {
            holder.socialPostCaption.setText(socialPost.getCaption());
            ImagePickerUtil.loadImageIntoView(context, socialPost.getImageUrl(), holder.socialPostImage);

//             Fetch the user object and set the profile picture and username
            userServicesDB.getUser(socialPost.getUserId()).addOnCompleteListener(new OnCompleteListener<User>() {
                @Override
                public void onComplete(@NonNull Task<User> task) {
                    if (task.isSuccessful()) {
                        User user = task.getResult();
                        if (user != null) {
                            holder.userName.setText(user.getUserName());
                            ImagePickerUtil.loadImageIntoUserProfile(context, user.getPfPicUrl(), holder.userProfileImage);
                        } else {
                            holder.userName.setText("Unknown User");
                            ImagePickerUtil.loadImageIntoUserProfile(context, null, holder.userProfileImage);
                        }
                    } else {
                        holder.userName.setText("Unknown User");
                        ImagePickerUtil.loadImageIntoUserProfile(context, null, holder.userProfileImage);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return socialPostList != null ? socialPostList.size() : 0;
    }

    public static class SocialPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView socialPostImage;
        public TextView socialPostCaption;
        public CircleImageView userProfileImage;
        public TextView userName;

        public SocialPostViewHolder(@NonNull View itemView) {
            super(itemView);
            socialPostImage = itemView.findViewById(R.id.user_social_posts_image);
            socialPostCaption = itemView.findViewById(R.id.user_social_posts_caption);
            userProfileImage = itemView.findViewById(R.id.user_profile_image);
            userName = itemView.findViewById(R.id.user_name);
        }
    }
}