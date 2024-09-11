package com.example.alumnihub.backend_services.firebase_auth;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthServices {
    private static final String TAG = "FirebaseAuthService";
    private FirebaseAuth mAuth;

    // Constructor to initialize FirebaseAuth
    public AuthServices() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Method for signing up a new user.
     *
     * @param email    The email address of the new user.
     * @param password The password for the new user.
     * @return A Task representing the asynchronous sign-up operation.
     */
    public Task<AuthResult> signUp(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User registered successfully: " + email);
                    } else {
                        Log.e(TAG, "Sign up failed: " + task.getException().getMessage());
                    }
                });
    }

    /**
     * Method for signing in an existing user.
     *
     * @param email    The email address of the user.
     * @param password The password for the user.
     * @return A Task representing the asynchronous sign-in operation.
     */
    public Task<AuthResult> signIn(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User signed in successfully: " + email);
                    } else {
                        Log.e(TAG, "Sign in failed: " + task.getException().getMessage());
                    }
                });
    }

    /**
     * Method for signing out the current user.
     */
    public void signOut() {
        mAuth.signOut();
        Log.d(TAG, "User signed out successfully");
    }

    /**
     * Get the currently signed-in user.
     *
     * @return The currently signed-in FirebaseUser, or null if no user is signed in.
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}
