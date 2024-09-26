package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.alumnihub.data_models.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceDB {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "chats";

    public String generateUniqueChatMessageId() {
        return "CM" + firebaseFirestore.collection("chats").document().getId();
    }

    public Task<Void> addMessage(Chat chat){
        // add new message by using this method
        String chatMessageId = chat.getChat_message_id();
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_NAME).document(chatMessageId);
        return documentReference.set(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ChatAdd", "onSuccess: ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ChatAdd", "onFailure: ");
            }
        });
    }

    public void getChatMessagesInRealTime(OnChatMessagesChangeListener listener) {
        firebaseFirestore.collection(COLLECTION_NAME)
                .orderBy("created_at")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("ChatService", "Error fetching chat messages: ", error);
                        listener.onError(error);
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        List<Chat> chatList = value.toObjects(Chat.class);
                        listener.onChatMessagesChanged(chatList);
                    } else {
                        listener.onChatMessagesChanged(new ArrayList<>()); // Return an empty list if no documents are found
                    }
                });
    }

    public interface OnChatMessagesChangeListener {
        void onChatMessagesChanged(List<Chat> chatList);
        void onError(Exception e);
    }
}