package com.example.alumnihub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Chat;
import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBubbleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENDER = 0;
    private static final int VIEW_TYPE_RECEIVER = 1;

    Context context;
    List<Chat> chatList;
    UserServicesDB userServicesDB;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser;

    // Cache to store user data and avoid multiple network calls for the same user
    Map<String, User> userCache = new HashMap<>();

    public ChatBubbleAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
        this.userServicesDB = new UserServicesDB();
        currentUser = firebaseAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENDER) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_messages_bubble, parent, false);
            return new ChatBubbleViewHolderSender(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_message_left_bubble, parent, false);
            return new ChatBubbleViewHolderReceiver(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_SENDER) {
            ChatBubbleViewHolderSender sender = (ChatBubbleViewHolderSender) holder;
            sender.userMessageText.setText(chat.getChat_message_text());
            fetchUserData(chat.getUser_id(), sender.userName);
        } else {
            ChatBubbleViewHolderReceiver receiver = (ChatBubbleViewHolderReceiver) holder;
            receiver.userMessageText.setText(chat.getChat_message_text());
            fetchUserData(chat.getUser_id(), receiver.userName);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);
        return chat.getUser_id().equals(currentUser.getUid()) ? VIEW_TYPE_SENDER : VIEW_TYPE_RECEIVER;
    }

    private void fetchUserData(String userId, TextView usernameTextView) {
        if (userCache.containsKey(userId)) {
            User user = userCache.get(userId);
            usernameTextView.setText("Sent by " + (user != null ? user.getUserName() : "Anon user"));
        } else {
            userServicesDB.getUser(userId).addOnCompleteListener(new OnCompleteListener<User>() {
                @Override
                public void onComplete(@NonNull Task<User> task) {
                    if (task.isSuccessful()) {
                        User user = task.getResult();
                        userCache.put(userId, user);
                        usernameTextView.setText("Sent by " + (user != null ? user.getUserName() : "Anon user"));
                    } else {
                        usernameTextView.setText("Sent by Anon user");
                    }
                }
            });
        }
    }

    public class ChatBubbleViewHolderReceiver extends RecyclerView.ViewHolder {
        private TextView userMessageText;
        private TextView userName;

        public ChatBubbleViewHolderReceiver(@NonNull View itemView) {
            super(itemView);
            userMessageText = itemView.findViewById(R.id.chatMessageText);
            userName = itemView.findViewById(R.id.usernameText);
        }
    }

    public class ChatBubbleViewHolderSender extends RecyclerView.ViewHolder {
        private TextView userMessageText;
        private TextView userName;

        public ChatBubbleViewHolderSender(@NonNull View itemView) {
            super(itemView);
            userMessageText = itemView.findViewById(R.id.chatMessageText);
            userName = itemView.findViewById(R.id.usernameText);
        }
    }
}