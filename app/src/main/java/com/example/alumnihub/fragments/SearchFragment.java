// SearchFragment.java
package com.example.alumnihub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.alumnihub.adapters.SearchUserAdapter;
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

    private void updateAutoCompleteTextView() {
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(getContext(), userList);
        autoCompleteTextView.setAdapter(searchUserAdapter);
        autoCompleteTextView.setThreshold(1); // Start showing suggestions after 1 character
        searchUserAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
    }

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

    private void fetchAndPassUser(String username) {
        userServicesDB.getUserByUsername(username)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = task.getResult();
                        if (user != null) {
                            // Pass the user details to the ProfileViewFragment
                            ProfileViewFragment profileViewFragment = ProfileViewFragment.newInstance(user);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.every_content_position, profileViewFragment); // Use every_content_position instead of fragment_container
                            transaction.addToBackStack(null);
                            transaction.commit();
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

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}