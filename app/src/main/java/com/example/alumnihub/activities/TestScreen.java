package com.example.alumnihub.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.ChatServiceDB;
import com.example.alumnihub.data_models.Chat;

import java.util.List;

public class TestScreen extends AppCompatActivity {

    private ChatServiceDB chatServiceDB;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTextView = findViewById(R.id.sampleTextView);
        chatServiceDB = new ChatServiceDB();

        // Fetch chat messages in real-time and display them
        chatServiceDB.getChatMessagesInRealTime(new ChatServiceDB.OnChatMessagesChangeListener() {
            @Override
            public void onChatMessagesChanged(List<Chat> chatList) {
                StringBuilder chatMessages = new StringBuilder();
                for (Chat chat : chatList) {
                    chatMessages.append(chat.getUser_name()).append(": ").append(chat.getChat_message_text()).append("\n");
                }
                resultTextView.setText(chatMessages.toString());
            }

            @Override
            public void onError(Exception e) {
                resultTextView.setText("Error fetching chat messages: " + e.getMessage());
            }
        });
    }
}