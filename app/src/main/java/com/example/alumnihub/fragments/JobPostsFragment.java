package com.example.alumnihub.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alumnihub.R;
import com.example.alumnihub.activities.JobPostFormActivity;
import com.example.alumnihub.adapters.JobPostAdapter;
import com.example.alumnihub.backend_services.firestore_db.JobPostServicesDB;
import com.example.alumnihub.data_models.JobPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class JobPostsFragment extends Fragment {
    private FloatingActionButton postJobButton;
    private RecyclerView recyclerView;
    private JobPostAdapter jobPostAdapter;
    private List<JobPost> jobPostList = new ArrayList<>();
    private JobPostServicesDB jobPostServicesDB;

    public JobPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobPostServicesDB = new JobPostServicesDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_posts, container, false);
        postJobButton = view.findViewById(R.id.jobPostButton);

        recyclerView = view.findViewById(R.id.job_posts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        jobPostAdapter = new JobPostAdapter(getContext(), jobPostList);
        recyclerView.setAdapter(jobPostAdapter);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JobPostFormActivity.class));
            }
        });
        fetchAllJobPosts();

        return view;
    }

    private void fetchAllJobPosts() {
        jobPostServicesDB.getAllJobPost().addOnCompleteListener(new OnCompleteListener<List<JobPost>>() {
            @Override
            public void onComplete(@NonNull Task<List<JobPost>> task) {
                if(task.isSuccessful()){
                    List<JobPost> jobPosts = task.getResult();
                    jobPostList.clear();
                    if(jobPosts != null && !jobPosts.isEmpty()){
                        jobPostList.addAll(jobPosts);
                    }
                    jobPostAdapter.notifyDataSetChanged();
                }else {
                    Log.e("JobPostFragment", "Error fetching Job posts", task.getException());
                }
            }
        });
    }
}