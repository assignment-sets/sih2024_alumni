package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import com.example.alumnihub.data_models.Application;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApplicationServicesDB {
    private static final String TAG = "ApplicationServices";
    private final FirebaseFirestore db = DatabaseConnectivity.getFirestore();

    /**
     * Generates a unique application ID.
     *
     * @return A unique application ID.
     */
    public String generateUniqueApplicationId() {
        return db.collection("verification_applications").document().getId();
    }

    /**
     * Adds a new application to the Firestore database.
     *
     * @param application The application object to be added.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> addApplication(Application application) {
        String applicationId = application.getApplicationId();
        Log.d(TAG, "Adding application: " + applicationId);

        // Reference to the "verification_applications" collection
        DocumentReference applicationRef = db.collection("verification_applications").document(applicationId);

        // Add the application document to Firestore
        return applicationRef.set(application)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Application added successfully: " + applicationId))
                .addOnFailureListener(e -> Log.e(TAG, "Error adding application: " + applicationId, e));
    }

    /**
     * Updates an existing application in the Firestore database.
     *
     * @param application The updated application object.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> updateApplication(Application application) {
        String applicationId = application.getApplicationId();
        DocumentReference applicationRef = db.collection("verification_applications").document(applicationId);
        return applicationRef.set(application)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Application updated successfully: " + applicationId))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating application: " + applicationId, e));
    }

    /**
     * Retrieves an application from the Firestore database.
     *
     * @param applicationId The unique ID of the application.
     * @return A Task representing the asynchronous operation to fetch the application.
     */
    public Task<Application> getApplication(String applicationId) {
        DocumentReference applicationRef = db.collection("verification_applications").document(applicationId);

        return applicationRef.get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert the document to an Application object
                            return document.toObject(Application.class);
                        } else {
                            throw new Exception("Application document does not exist.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }
}
