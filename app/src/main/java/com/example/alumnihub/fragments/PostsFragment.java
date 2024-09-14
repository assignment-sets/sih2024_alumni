package com.example.alumnihub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.alumnihub.R;
import com.google.android.material.tabs.TabLayout;

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        frameLayout = view.findViewById(R.id.posts_content_place);

        // by default job post section fragment selected thakba
        loadChildFragmentDiffPosts(new JobPostsFragment());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // change hoba based on selected tab
                switch (tab.getPosition()){
                    case 0:
                        loadChildFragmentDiffPosts(new JobPostsFragment());
                        break;
                    case 1:
                        loadChildFragmentDiffPosts(new SocialPostFragment());
                        break;
                    case 2:
                        loadChildFragmentDiffPosts(new EventPostFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void loadChildFragmentDiffPosts(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.posts_content_place, fragment);
        fragmentTransaction.commit();
    }
}