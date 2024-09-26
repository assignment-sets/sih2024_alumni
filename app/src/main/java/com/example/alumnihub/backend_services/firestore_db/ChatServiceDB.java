package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alumnihub.data_models.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatServiceDB {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "chats";

    private ListenerRegistration listenerRegistration;

    public String generateUniqueChatMessageId() {
        return "CM" + firebaseFirestore.collection(COLLECTION_NAME).document().getId();
    }

    public Task<Void> addMessage(Chat chat){
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

    public void fetchAllChatMessages(OnChatMessagesFetchedListener listener) {
        CollectionReference chatReference = firebaseFirestore.collection(COLLECTION_NAME);

        listenerRegistration = chatReference.orderBy("created_at", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("ChatServiceDB", "Listen failed: ", error);
                            return;
                        }

                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<Chat> chatMessages = querySnapshot.toObjects(Chat.class);
                            listener.onChatMessagesFetched(chatMessages);
                        } else {
                            Log.d("ChatServiceDB", "No chat messages found.");
                            listener.onChatMessagesFetched(Collections.emptyList());
                        }
                    }
                });
    }


    public interface OnChatMessagesFetchedListener {
        void onChatMessagesFetched(List<Chat> chatMessages);
    }
}
