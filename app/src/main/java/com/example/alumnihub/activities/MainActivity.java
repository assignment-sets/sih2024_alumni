package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.alumnihub.R;
import com.example.alumnihub.fragments.CommunityChatFragment;
import com.example.alumnihub.fragments.MaterialShareFragment;
import com.example.alumnihub.fragments.PostsFragment;
import com.example.alumnihub.fragments.ProfileViewFragment;
import com.example.alumnihub.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout every_fragment_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // statusbar set korchi with color
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.bgSplashScreen));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.materialToolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        every_fragment_position = findViewById(R.id.every_content_position);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.alumni_hub);

        //from here i start fragments navigation logic
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.every_content_position, new CommunityChatFragment()); // by default user will start with community chat fragment
        fragmentTransaction.commit();

        // bottomNavigationView screen replace logic
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                if (menuItem.getItemId() == R.id.chat) {
                    fragmentTransaction1.replace(R.id.every_content_position, new CommunityChatFragment());
                    fragmentTransaction1.addToBackStack(null);
                } else if (menuItem.getItemId() == R.id.study_material) {
                    fragmentTransaction1.replace(R.id.every_content_position, new MaterialShareFragment());
                    fragmentTransaction1.addToBackStack(null);
                } else if (menuItem.getItemId() == R.id.search_account) {
                    fragmentTransaction1.replace(R.id.every_content_position, new SearchFragment());
                    fragmentTransaction1.addToBackStack(null);
                } else if (menuItem.getItemId() == R.id.posts) {
                    fragmentTransaction1.replace(R.id.every_content_position, new PostsFragment());
                    fragmentTransaction1.addToBackStack(null);
                } else if (menuItem.getItemId() == R.id.profile) {
                    // Start ProfileViewActivity when profile is selected
                    Intent intent = new Intent(MainActivity.this, ProfileViewActivity.class);
                    startActivity(intent);
                    return true; // Return here
                }
                fragmentTransaction1.commit();
                return true;
            }
        });

    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Check if returning from ProfileViewActivity
//        if (requestCode == 1) {
//            // Reset BottomNavigationView to the last selected fragment
//            // Assuming "chat" was the default selected fragment
//            bottomNavigationView.setSelectedItemId(R.id.chat); // Set to the default fragment or last known selected
//        }
//    }

}