package com.example.alumnihub.backend_services.firestore_db;

import androidx.annotation.NonNull;

import com.example.alumnihub.data_models.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ChatServiceDB {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "chats";

    public Task<Void> addMessage(Chat chat){
        // add new message by using this method
        String chatMessageId = chat.getChat_message_id();
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_NAME).document(chatMessageId);
        return documentReference.set(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public Task<List<Chat>> getAllChatMessage(){
        // get All the chat messages by using this method
        return firebaseFirestore.collection(COLLECTION_NAME).get().continueWith(task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                return querySnapshot.toObjects(Chat.class);
            }else {
                throw task.getException();
            }
        });
    }
}
