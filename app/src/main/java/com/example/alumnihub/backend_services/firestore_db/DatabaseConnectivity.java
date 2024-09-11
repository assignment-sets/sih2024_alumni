package com.example.alumnihub.backend_services.firestore_db;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Singleton class to manage Firestore database connectivity.
 */
public class DatabaseConnectivity {
    private static volatile FirebaseFirestore firestore;

    /**
     * Get the Firestore instance.
     *
     * @return The singleton instance of FirebaseFirestore.
     */
    public static FirebaseFirestore getFirestore() {
        if (firestore == null) {
            synchronized (DatabaseConnectivity.class) {
                if (firestore == null) {
                    // Initialize Firestore instance if it is null
                    firestore = FirebaseFirestore.getInstance();
                }
            }
        }
        return firestore;
    }
}
