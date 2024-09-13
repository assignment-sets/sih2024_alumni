package com.example.alumnihub.backend_services.firestore_db;

import com.example.alumnihub.data_models.SocialPost;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class SocialPostServicesDB {

    private static final String COLLECTION_NAME = "social_posts";
    private final FirebaseFirestore db;

    // Constructor initializes the Firestore instance using DatabaseConnectivity
    public SocialPostServicesDB() {
        db = DatabaseConnectivity.getFirestore();
    }

    /**
     * Generates a unique social post ID.
     *
     * @return A unique social post ID.
     */
    public String generateUniqueSocialPostId() {
        return "SP" + db.collection("social_posts").document().getId();
    }

    /**
     * Adds a new social post to the Firestore database.
     *
     * @param socialPost The social post object to be added.
     * @return A Task representing the asynchronous operation to add the post.
     */
    public Task<Void> addSocialPost(SocialPost socialPost) {
        DocumentReference postRef = db.collection(COLLECTION_NAME).document(socialPost.getSocialPostId());
        return postRef.set(socialPost);
    }

    /**
     * Deletes a social post from the Firestore database by its ID.
     *
     * @param socialPostId The unique ID of the social post to be deleted.
     * @return A Task representing the asynchronous operation to delete the post.
     */
    public Task<Void> deleteSocialPostById(String socialPostId) {
        return db.collection(COLLECTION_NAME).document(socialPostId).delete();
    }

    /**
     * Retrieves a social post from the Firestore database by its ID.
     *
     * @param socialPostId The unique ID of the social post.
     * @return A Task representing the asynchronous operation to fetch the post.
     */
    public Task<SocialPost> getSocialPostById(String socialPostId) {
        return db.collection(COLLECTION_NAME).document(socialPostId).get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert the document to a SocialPost object
                            return document.toObject(SocialPost.class);
                        } else {
                            throw new Exception("Social post document does not exist.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }

    /**
     * Retrieves all social posts from the Firestore database.
     *
     * @return A Task representing the asynchronous operation to fetch the posts.
     */
    public Task<List<SocialPost>> getAllSocialPosts() {
        return db.collection(COLLECTION_NAME).get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        // Convert the query results to a list of SocialPost objects
                        return querySnapshot.toObjects(SocialPost.class);
                    } else {
                        throw task.getException();
                    }
                });
    }
}