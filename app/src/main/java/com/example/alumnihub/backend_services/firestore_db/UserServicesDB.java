package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserServicesDB {
    private static final String TAG = "UserServices";
    private final FirebaseFirestore db = DatabaseConnectivity.getFirestore();

    /**
     * Adds a new user to the Firestore database.
     *
     * @param user_id  The unique ID of the user.
     * @param email    The email address of the user.
     * @param username The username of the user.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> addUser(String user_id, String username, String email) {
        Log.d(TAG, "Adding user: " + user_id);

        // Create a user object to store in Firestore
        User user = new User();
        user.setUserId(user_id);
        user.setEmail(email);
        user.setUserName(username);

        // Reference to the "users" collection
        DocumentReference userRef = db.collection("users").document(user_id);

        // Add the user document to Firestore
        return userRef.set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User added successfully: " + user_id))
                .addOnFailureListener(e -> Log.e(TAG, "Error adding user: " + user_id, e));
    }

    /**
     * Updates an existing user in the Firestore database.
     *
     * @param user_id     The unique ID of the user.
     * @param updatedUser The updated user object.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> updateUser(String user_id, User updatedUser) {
        DocumentReference userRef = db.collection("users").document(user_id);
        return userRef.set(updatedUser)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User updated successfully: " + user_id))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating user: " + user_id, e));
    }

    /**
     * Retrieves a user from the Firestore database.
     *
     * @param userId The unique ID of the user.
     * @return A Task representing the asynchronous operation to fetch the user.
     */
    public Task<User> getUser(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);

        return userRef.get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert the document to a User object
                            return document.toObject(User.class);
                        } else {
                            throw new Exception("User document does not exist.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }
}
