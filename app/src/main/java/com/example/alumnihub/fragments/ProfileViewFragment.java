package com.example.alumnihub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ProfileViewFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User user;
    private UserServicesDB userServicesDB;
    private TextView userAccountName, userFullName, userEnrollmentNo, userEmail, userType, userGradYear;

    public ProfileViewFragment() {
        // Required empty public constructor
    }

    public static ProfileViewFragment newInstance(User user) {
        ProfileViewFragment fragment = new ProfileViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userServicesDB = new UserServicesDB();
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userAccountName = view.findViewById(R.id.user_account_name);
        userFullName = view.findViewById(R.id.user_full_name);
        userEnrollmentNo = view.findViewById(R.id.user_enrollment_no);
        userEmail = view.findViewById(R.id.user_email);
        userType = view.findViewById(R.id.user_type);
        userGradYear = view.findViewById(R.id.user_graduation_year);

        if (user != null) {
            displayUserDetails(user);
        } else {
//            fetchCurrentUser();
        }
    }

    private void displayUserDetails(User user) {
        userAccountName.setText(user.getUserName());
        userFullName.setText(user.getFullName());
        userEnrollmentNo.setText(user.getEnrollmentNum());
        userEmail.setText(user.getEmail());
        userType.setText(user.getType());
        userGradYear.setText(user.getGraduationYear());
    }

//    private void fetchCurrentUser() {
//        String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//        userServicesDB.getUser(currentUserId).addOnCompleteListener(new OnCompleteListener<User>() {
//            @Override
//            public void onComplete(@NonNull Task<User> task) {
//                if (task.isSuccessful()) {
//                    User user = task.getResult();
//                    if (user != null) {
//                        displayUserDetails(user);
//                    } else {
//                        resultTextView.setText("No user data available");
//                    }
//                } else {
//                    resultTextView.setText("Failed to fetch user data");
//                }
//            }
//        });
//    }  it is the main code but for testing i replace it

//    private void fetchCurrentUser() {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            String currentUserId = auth.getCurrentUser().getUid();
//            userServicesDB.getUser(currentUserId).addOnCompleteListener(new OnCompleteListener<User>() {
//                @Override
//                public void onComplete(@NonNull Task<User> task) {
//                    if (task.isSuccessful()) {
//                        User user = task.getResult();
//                        if (user != null) {
//                            displayUserDetails(user);
//                        } else {
//                            resultTextView.setText("No user data available");
//                        }
//                    } else {
//                        resultTextView.setText("Failed to fetch user data");
//                    }
//                }
//            });
//        } else {
//            resultTextView.setText("User is not logged in");
//        }
//    }

}