package com.example.alumnihub.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alumnihub.R;
import com.example.alumnihub.activities.TestScreen;
import com.example.alumnihub.adapters.UserAdapter;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.User;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private ArrayList<User> userList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;
    private ImageView searchFragmentBgLogo;
    private UserServicesDB userServicesDB;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the UserServicesDB instance
        userServicesDB = new UserServicesDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        searchFragmentBgLogo = view.findViewById(R.id.searchFragmentBackgroundLogo);

        // Add a TextWatcher to the AutoCompleteTextView to handle text changes
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    // Show the logo when the text is empty
                    searchFragmentBgLogo.setVisibility(View.VISIBLE);
                } else {
                    // Hide the logo when there is text and fetch user suggestions
                    searchFragmentBgLogo.setVisibility(View.INVISIBLE);
                    fetchUserSuggestions(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text changes
            }
        });

        // Set an item click listener to handle user selection from suggestions
        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            User selectedUser = (User) parent.getItemAtPosition(position);
            fetchAndPassUser(selectedUser.getUserName());
        });

        return view;
    }

    /**
     * Updates the AutoCompleteTextView with the user suggestions.
     */
    private void updateAutoCompleteTextView() {
        UserAdapter userAdapter = new UserAdapter(getContext(), userList);
        autoCompleteTextView.setAdapter(userAdapter);
        autoCompleteTextView.setThreshold(1); // Start showing suggestions after 1 character
        userAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
    }

    /**
     * Fetches user suggestions based on the partial username.
     *
     * @param partialUsername The partial username to search for.
     */
    private void fetchUserSuggestions(String partialUsername) {
        userServicesDB.getUsersByPartialUsername(partialUsername)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<User> users = task.getResult();
                        userList.clear();
                        if (users != null && !users.isEmpty()) {
                            userList.addAll(users);
                        }
                        updateAutoCompleteTextView(); // Update the AutoCompleteTextView
                        Log.d("SearchFragment", "Fetched users: " + users.size());
                    } else {
                        Log.e("SearchFragment", "Error fetching users", task.getException());
                        // Handle the error
                        showToast("Error fetching user suggestions.");
                    }
                });
    }

    /**
     * Fetches the user details and passes them to the next activity.
     *
     * @param username The username of the selected user.
     */
    private void fetchAndPassUser(String username) {
        userServicesDB.getUserByUsername(username)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = task.getResult();
                        if (user != null) {
                            // Pass the user details to the TestScreen activity
                            Intent intent = new Intent(getActivity(), TestScreen.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        } else {
                            // Handle user not found
                            showToast("User not found.");
                        }
                    } else {
                        // Handle the error
                        Log.e("SearchFragment", "Error fetching user", task.getException());
                        showToast("Error fetching user.");
                    }
                });
    }

    /**
     * Displays a toast message.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}