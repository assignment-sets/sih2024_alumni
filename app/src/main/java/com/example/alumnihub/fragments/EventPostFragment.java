package com.example.alumnihub.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.alumnihub.R;
import com.example.alumnihub.activities.CreateEventPostFormScreen;
import com.example.alumnihub.adapters.EventPostAdapter;
import com.example.alumnihub.backend_services.firestore_db.EventServicesDB;
import com.example.alumnihub.data_models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class EventPostFragment extends Fragment {
    private RecyclerView eventPostsRecyclerView;
    private EventPostAdapter eventPostAdapter;
    private List<Event> eventPostsList = new ArrayList<>();
    private EventServicesDB eventServicesDB;
    private FloatingActionButton eventPostButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventServicesDB = new EventServicesDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_post, container, false);
        eventPostsRecyclerView = view.findViewById(R.id.event_posts_recyclerview);
        eventPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventPostAdapter = new EventPostAdapter(getContext(), eventPostsList);
        eventPostsRecyclerView.setAdapter(eventPostAdapter);
        eventPostButton = view.findViewById(R.id.eventPostButton);
        eventPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateEventPostFormScreen.class);
            startActivity(intent);
        });
        fetchAllEvents();
        return view;
    }

    private void fetchAllEvents() {
        eventServicesDB.getAllEvents().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Event> events = task.getResult();
                eventPostsList.clear();
                if (events != null && !events.isEmpty()) {
                    eventPostsList.addAll(events);
                }
                eventPostAdapter.notifyDataSetChanged();
            } else {
                // Handle the error
                Log.e("EventPostFragment", "Error fetching events", task.getException());
            }
        });
    }
}