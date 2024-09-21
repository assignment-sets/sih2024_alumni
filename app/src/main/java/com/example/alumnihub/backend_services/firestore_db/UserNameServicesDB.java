package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import com.example.alumnihub.data_models.UserName;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserNameServicesDB {
    private static final String TAG = "UserNameServices";
    private final FirebaseFirestore db = DatabaseConnectivity.getFirestore();

    /**
     * Checks if a username already exists in the Firestore database.
     *
     * @param username The username to check.
     * @return A Task representing the asynchronous operation to check existence.
     */
    public Task<Boolean> doesUsernameExist(String username) {
        DocumentReference userNameRef = db.collection("user_names").document(username);
        return userNameRef.get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        return document.exists();  // Return true if document exists, false otherwise
                    } else {
                        throw task.getException();
                    }
                });
    }

    /**
     * Adds a new username to the Firestore database.
     *
     * @param username The username to be added.
     * @return A Task representing the asynchronous operation.
     */
     public Task<Void> addUsername(String username) {
        Log.d(TAG, "Adding username: " + username);

        // Reference to the "user_names" collection with the username as the document ID
        DocumentReference userNameRef = db.collection("user_names").document(username);

        // Add an empty document with the username as the document ID
        UserName userNameObj = new UserName();
        return userNameRef.set(userNameObj)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Username added successfully: " + username))
                .addOnFailureListener(e -> Log.e(TAG, "Error adding username: " + username, e));
    }
}
