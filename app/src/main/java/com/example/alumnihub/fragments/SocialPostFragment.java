package com.example.alumnihub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnihub.R;
import com.example.alumnihub.activities.CreateSocialPostFormScreen;
import com.example.alumnihub.adapters.SocialPostAdapter;
import com.example.alumnihub.backend_services.firestore_db.SocialPostServicesDB;
import com.example.alumnihub.data_models.SocialPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class SocialPostFragment extends Fragment {

    private static final String TAG = "SocialPostFragment";
    private RecyclerView socialPostsRecyclerView;
    private SocialPostAdapter socialPostAdapter;
    private List<SocialPost> socialPostList = new ArrayList<>();
    private SocialPostServicesDB socialPostServicesDB;
    private FloatingActionButton socialPostButton;

    public SocialPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socialPostServicesDB = new SocialPostServicesDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social_post, container, false);
        socialPostsRecyclerView = view.findViewById(R.id.social_posts_recyclerview);
        socialPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        socialPostAdapter = new SocialPostAdapter(getContext(), socialPostList);
        socialPostsRecyclerView.setAdapter(socialPostAdapter);
        socialPostButton = view.findViewById(R.id.socialPostButton);

        // Set OnClickListener for the FloatingActionButton
        socialPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreateSocialPostFormScreen
                Intent intent = new Intent(getActivity(), CreateSocialPostFormScreen.class);
                startActivity(intent);
            }
        });

        fetchSocialPosts();
        return view;
    }

    private void fetchSocialPosts() {
        Log.d(TAG, "Fetching social posts...");
        socialPostServicesDB.getAllSocialPosts().addOnCompleteListener(new OnCompleteListener<List<SocialPost>>() {
            @Override
            public void onComplete(@NonNull Task<List<SocialPost>> task) {
                if (task.isSuccessful()) {
                    List<SocialPost> posts = task.getResult();
                    socialPostList.clear();
                    if (posts != null && !posts.isEmpty()) {
                        socialPostList.addAll(posts);
                    }
                    socialPostAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Fetched " + socialPostList.size() + " social posts.");
                } else {
                    Log.e(TAG, "Error fetching social posts", task.getException());
                }
            }
        });
    }
}