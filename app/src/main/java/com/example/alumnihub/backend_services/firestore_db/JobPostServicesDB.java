package com.example.alumnihub.backend_services.firestore_db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.alumnihub.data_models.JobPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class JobPostServicesDB {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "jobs";
    private static final String LOGTAG = "JobPostServiceDB";

    public String generateUniqueJobId() {
        return "JI" + firebaseFirestore.collection("jobs").document().getId();
    }

    public Task<Void> addJobPost(JobPost jobPost){
        String jobPostId = jobPost.getJob_id();
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_NAME).document(jobPostId);
        
        return documentReference.set(jobPost).addOnSuccessListener(unused -> {
            Log.d(LOGTAG, "Job post added successfully: " + jobPostId);
        }).addOnFailureListener(e -> {
            Log.d(LOGTAG, "Job Post denied " + jobPostId);
        });
    }

    public Task<Void> deleteJobPost(String jobId){
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_NAME).document(jobId);

        return documentReference.delete().addOnSuccessListener(unused -> {
            Log.d(LOGTAG, "deleteJobPost successfully : " + jobId);
        }).addOnFailureListener(e -> {
            Log.d(LOGTAG, "deleteJobPost denied: " + jobId);
        });
    }

    public Task<JobPost> getJobPost(String jobId) {
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_NAME).document(jobId);

        return documentReference.get().continueWith(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                JobPost jobPost = task.getResult().toObject(JobPost.class);
                if (jobPost != null) {
                    Log.d(LOGTAG, "Job post retrieved successfully: " + jobId);
                    return jobPost;
                } else {
                    Log.d(LOGTAG, "No such job post found: " + jobId);
                    return null;
                }
            } else {
                Log.d(LOGTAG, "Error retrieving job post: " + task.getException());
                return null;
            }
        });
    }


    public Task<List<JobPost>> getAllJobPost(){
        return firebaseFirestore.collection(COLLECTION_NAME).get().continueWith(task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                return querySnapshot.toObjects(JobPost.class);
            }else {
                throw  task.getException();
            }
        });
    }
}
