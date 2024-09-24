package com.example.alumnihub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alumnihub.R;
import com.example.alumnihub.adapters.ChatBubbleAdapter;
import com.example.alumnihub.data_models.Chat;

import java.util.ArrayList;
import java.util.List;

public class CommunityChatFragment extends Fragment {
    public CommunityChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Chat chat = new Chat();
        chat.setChat_message_text("Hello Testing is done");
        chat.setUser_id("such283");
        chatList.add(chat);
        chatBubbleAdapter = new ChatBubbleAdapter(getContext(), chatList);
        chatMessagesRecyclerView.setAdapter(chatBubbleAdapter);

        return view;
    }
}