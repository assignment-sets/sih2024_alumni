package com.example.alumnihub.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alumnihub.R;
import com.example.alumnihub.activities.JobPostFormActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobPostsFragment extends Fragment {
//    public JobPostsFragment() {
//        // Required empty public constructor
//    }
    private FloatingActionButton postJobButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_posts, container, false);
        postJobButton = view.findViewById(R.id.jobPostButton);

        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JobPostFormActivity.class));
            }
        });
        return view;
    }
}