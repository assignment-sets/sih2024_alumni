package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import com.example.alumnihub.data_models.Event;  // Adjust the import according to your package
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class EventServicesDB {
    private static final String TAG = "EventServiceDB";
    private final FirebaseFirestore db = DatabaseConnectivity.getFirestore();
    private static final String COLLECTION_NAME = "events";

    /**
     * Generates a unique event ID.
     *
     * @return A unique event ID.
     */
    public String generateUniqueEventId() {
        return "EI" + db.collection("events").document().getId();
    }

    /**
     * Adds a new event to the Firestore database.
     *
     * @param event The event object to be added.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> addEvent(Event event) {
        String eventId = event.getEventId();
        DocumentReference eventRef = db.collection(COLLECTION_NAME).document(eventId);

        return eventRef.set(event)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Event added successfully: " + eventId))
                .addOnFailureListener(e -> Log.e(TAG, "Error adding event: " + eventId, e));
    }

    /**
     * Deletes an event from the Firestore database.
     *
     * @param eventId The ID of the event to be deleted.
     * @return A Task representing the asynchronous operation.
     */
    public Task<Void> deleteEvent(String eventId) {
        DocumentReference eventRef = db.collection(COLLECTION_NAME).document(eventId);

        return eventRef.delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Event deleted successfully: " + eventId))
                .addOnFailureListener(e -> Log.e(TAG, "Error deleting event: " + eventId, e));
    }

    /**
     * Retrieves a particular event from the Firestore database.
     *
     * @param eventId The ID of the event to retrieve.
     * @return A Task representing the asynchronous operation to fetch the event.
     */
    public Task<Event> getEvent(String eventId) {
        DocumentReference eventRef = db.collection(COLLECTION_NAME).document(eventId);

        return eventRef.get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            return document.toObject(Event.class);
                        } else {
                            throw new Exception("Event document does not exist.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }

    /**
     * Retrieves a list of all events from the Firestore database.
     *
     * @return A Task representing the asynchronous operation to fetch all events.
     */
    public Task<List<Event>> getAllEvents() {
        return db.collection(COLLECTION_NAME).get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        return querySnapshot.toObjects(Event.class);
                    } else {
                        throw task.getException();
                    }
                });
    }
}
