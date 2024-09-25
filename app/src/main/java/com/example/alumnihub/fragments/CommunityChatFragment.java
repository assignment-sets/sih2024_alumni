package com.example.alumnihub.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alumnihub.R;
import com.example.alumnihub.adapters.ChatBubbleAdapter;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firestore_db.ChatServiceDB;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Chat;
import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityChatFragment extends Fragment {
    public CommunityChatFragment() {
        // Required empty public constructor
    }
    FirebaseUser firebaseUser;
    AuthServices authServices = new AuthServices();
    private  ChatServiceDB chatServiceDB;
    private UserServicesDB userServicesDB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.firebaseUser = authServices.getCurrentUser();
        chatServiceDB = new ChatServiceDB();
        userServicesDB = new UserServicesDB();
    }

    private RecyclerView chatMessagesRecyclerView;
    private ImageView sendMsgBtn;
    private EditText chatMessageEnter;
    private ChatBubbleAdapter chatBubbleAdapter;
    private List<Chat> chatList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_chat, container, false);
        chatMessagesRecyclerView = view.findViewById(R.id.chatRecyclerView);
        sendMsgBtn = view.findViewById(R.id.send_message_btn);
        chatMessageEnter = view.findViewById(R.id.enterChatMessageField);
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatBubbleAdapter = new ChatBubbleAdapter(getContext(), chatList);
        chatMessagesRecyclerView.setAdapter(chatBubbleAdapter);

        sendMsgBtn.setOnClickListener(v -> {
            String chatMessage = chatMessageEnter.getText().toString();
            if(!chatMessage.isEmpty()){
                handleChatMessages(chatMessage);
                chatMessageEnter.setText("");
            }
        });
        fetchAllChatMessages();

        return view;
    }

    private void fetchAllChatMessages() {
        chatServiceDB.getChatMessagesInRealTime(new ChatServiceDB.OnChatMessagesChangeListener() {
            @Override
            public void onChatMessagesChanged(List<Chat> chats) {
                if (chats != null && !chats.isEmpty()) {
                    chatList.clear();
                    chatList.addAll(chats);
                    chatBubbleAdapter.notifyDataSetChanged();

                    if (!chatList.isEmpty()) {
                        chatMessagesRecyclerView.scrollToPosition(chatList.size() - 1);
                    }
                }
            }


            @Override
            public void onError(Exception e) {
                Log.d("Chat", "onError: Chat messages fetching error" + e.getMessage());
            }
        });
    }


    private void handleChatMessages(String chatMessage) {
        userServicesDB.getUser(firebaseUser.getUid()).addOnSuccessListener(new OnSuccessListener<User>() {
            @Override
            public void onSuccess(User user) {
                if(user != null){
                    Chat chat = new Chat();
                    chat.setUser_id(user.getUserId());
                    chat.setChat_message_text(chatMessage);
                    chat.setUser_name(user.getUserName());
                    chat.setPfp_pic_url(user.getPfPicUrl());
                    chat.setChat_message_id(chatServiceDB.generateUniqueChatMessageId());
                    chat.setCreatedAt(new Date());

                    // chat data model ready successfully bro now push it
                    chatServiceDB.addMessage(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Chat", "onSuccess: successfully chat message added");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Chat", "onFailure: chat message does not added successfully " + e.getMessage());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Chat", "onFailure: user does not found");
            }
        });
    }
}