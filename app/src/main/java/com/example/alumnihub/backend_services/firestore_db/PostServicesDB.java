package com.example.alumnihub.backend_services.firestore_db;

import com.example.alumnihub.data_models.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class PostServicesDB {

    private static final String COLLECTION_NAME = "posts";
    private final FirebaseFirestore db;

    // Constructor initializes the Firestore instance using DatabaseConnectivity
    public PostServicesDB() {
        db = DatabaseConnectivity.getFirestore();
    }

    /**
     * Generates a unique post ID.
     *
     * @return A unique post ID.
     */
    public String generateUniquePostId() {
        return "PI" + db.collection("posts").document().getId();
    }

    /**
     * Retrieves a post from the Firestore database by its ID.
     *
     * @param postId The unique ID of the post.
     * @return A Task representing the asynchronous operation to fetch the post.
     */
    public Task<Post> getPostById(String postId) {
        return db.collection(COLLECTION_NAME).document(postId).get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert the document to a Post object
                            return document.toObject(Post.class);
                        } else {
                            throw new Exception("Post document does not exist.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }

    /**
     * Deletes a post from the Firestore database by its ID.
     *
     * @param postId The unique ID of the post to be deleted.
     * @return A Task representing the asynchronous operation to delete the post.
     */
    public Task<Void> deletePostById(String postId) {
        return db.collection(COLLECTION_NAME).document(postId).delete();
    }

    /**
     * Retrieves all posts of a particular user from the Firestore database by user ID.
     *
     * @param userId The unique ID of the user.
     * @return A Task representing the asynchronous operation to fetch the posts.
     */
    public Task<List<Post>> getPostsByUserId(String userId) {
        return db.collection(COLLECTION_NAME).whereEqualTo("user_id", userId).get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        // Convert the query results to a list of Post objects
                        return querySnapshot.toObjects(Post.class);
                    } else {
                        throw task.getException();
                    }
                });
    }

    /**
     * Adds a new post to the Firestore database.
     *
     * @param post The post object to be added.
     * @return A Task representing the asynchronous operation to add the post.
     */
    public Task<Void> addPost(Post post) {
        DocumentReference postRef = db.collection(COLLECTION_NAME).document(post.getPostId());
        return postRef.set(post);
    }
}