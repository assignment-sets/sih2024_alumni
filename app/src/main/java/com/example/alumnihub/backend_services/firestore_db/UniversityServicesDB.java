package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import com.example.alumnihub.data_models.University;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UniversityServicesDB {
    private static final String TAG = "UniversityServicesDB";
    private final FirebaseFirestore db = DatabaseConnectivity.getFirestore();
    private static final String COLLECTION_NAME = "university";

    /**
     * Updates an existing university in the Firestore database.
     *
     * @param university The university object to be updated.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> updateUniversity(University university) {
        String unvId = university.getUnvId();
        DocumentReference universityRef = db.collection(COLLECTION_NAME).document(unvId);

        return universityRef.set(university)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "University updated successfully: " + unvId))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating university: " + unvId, e));
    }

    /**
     * Retrieves a particular university from the Firestore database.
     *
     * @param unvId The ID of the university to retrieve.
     * @return A Task representing the asynchronous operation to fetch the university.
     */
    public Task<University> getUniversityById(String unvId) {
        DocumentReference universityRef = db.collection(COLLECTION_NAME).document(unvId);

        return universityRef.get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            return document.toObject(University.class);
                        } else {
                            throw new Exception("University document does not exist.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }
}