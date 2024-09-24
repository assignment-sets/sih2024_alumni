package com.example.alumnihub.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firebase_storage.StorageServices;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Chat;
import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatBubbleAdapter extends RecyclerView.Adapter<ChatBubbleAdapter.ChatBubbleViewHolder> {
    Context context;
    List<Chat> chatList;
    UserServicesDB userServicesDB;
    StorageServices storageServices;
    AuthServices authServices = new AuthServices();

    public ChatBubbleAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
        this.userServicesDB = new UserServicesDB();
        this.storageServices = new StorageServices();
    }

    @NonNull
    @Override
    public ChatBubbleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){ // show in the right side for logged in user
            View view = LayoutInflater.from(context).inflate(R.layout.chat_messages_bubble, parent, false);
            return new ChatBubbleViewHolder(view);
        }else { // show in left side for other user's messages
            View view = LayoutInflater.from(context).inflate(R.layout.chat_message_left_bubble, parent, false);
            return new ChatBubbleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBubbleViewHolder holder, int position) {
        Chat chat = chatList.get(position);
//        storageServices.getDownloadUrl("/profile_pics/"+chat.getUser_id()).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if(task.isSuccessful() && task.getResult() != null){
//                    Uri userPfPic = task.getResult();
//
//                    Picasso.get().load(userPfPic.toString()).placeholder(R.drawable.ic_default_user_profile_pic)
//                            .into(holder.userProfileImage);
//                }
//            }
//        }); // user profile image i will show latter wait 
        holder.userMessageText.setText(chat.getChat_message_text());

        userServicesDB.getUser(chat.getUser_id()).addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if(task.isSuccessful()){
                    User user = task.getResult();
                    if(user != null){
                        holder.userName.setText("Send by "+user.getUserName());
                    }else {
                        holder.userName.setText("Send by Anon user");
                    }
                }else {
                    holder.userName.setText("Send by Anon user");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).getUser_id().equals(authServices.getCurrentUser().getUid()) ? 0 : 1;
    }

    public class ChatBubbleViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView userProfileImage;
        private TextView userMessageText;
        private TextView userName;
        public ChatBubbleViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            userMessageText = itemView.findViewById(R.id.chatMessageText);
            userName = itemView.findViewById(R.id.usernameText);
        }
    }
}
