// Java
package com.example.alumnihub.backend_services.firebase_storage;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;

public class StorageServices {

    private static final String TAG = "StorageServices";
    private StorageReference mStorageRef;

    // Constructor to initialize FirebaseStorage and StorageReference
    public StorageServices() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    /**
     * Method to upload a file to Firebase Storage.
     *
     * @param fileUri The URI of the file to upload.
     * @param path    The path in Firebase Storage where the file will be stored.
     * @return A Task representing the asynchronous upload operation.
     */
    public Task<Uri> uploadFile(Uri fileUri, String path) {
        StorageReference fileRef = mStorageRef.child(path);
        return fileRef.putFile(fileUri)
                .continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                })
                .addOnSuccessListener(uri -> Log.d(TAG, "File uploaded successfully: " + uri.toString()))
                .addOnFailureListener(exception -> Log.e(TAG, "File upload failed: " + exception.getMessage()));
    }

    /**
     * Method to get the download URL for a file in Firebase Storage.
     *
     * @param path The path of the file in Firebase Storage.
     * @return A Task representing the asynchronous operation to get the download URL.
     */
    public Task<Uri> getDownloadUrl(String path) {
        StorageReference fileRef = mStorageRef.child(path);
        return fileRef.getDownloadUrl()
                .addOnSuccessListener(uri -> Log.d(TAG, "Download URL retrieved successfully: " + uri.toString()))
                .addOnFailureListener(exception -> Log.e(TAG, "Failed to get download URL: " + exception.getMessage()));
    }

    /**
     * Method to delete a file from Firebase Storage.
     *
     * @param path The path of the file in Firebase Storage to delete.
     * @return A Task representing the asynchronous delete operation.
     */
    public Task<Void> deleteFile(String path) {
        StorageReference fileRef = mStorageRef.child(path);
        return fileRef.delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "File deleted successfully: " + path))
                .addOnFailureListener(exception -> Log.e(TAG, "Failed to delete file: " + exception.getMessage()));
    }
}