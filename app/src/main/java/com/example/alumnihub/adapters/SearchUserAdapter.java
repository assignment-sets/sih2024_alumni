package com.example.alumnihub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alumnihub.R;
import com.example.alumnihub.data_models.User;
import com.example.alumnihub.utils.ImagePickerUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> users;

    public SearchUserAdapter(Context context, List<User> users) {
        super(context, R.layout.search_item_suggestion, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item_suggestion, parent, false);
        }

        User user = users.get(position);

        CircleImageView imageView = convertView.findViewById(R.id.suggestionImageView);
        TextView userNameTextView = convertView.findViewById(R.id.suggestionUserName);
        TextView fullNameTextView = convertView.findViewById(R.id.suggestionFullName);

        // Load user image
        ImagePickerUtil.loadImageIntoUserProfile(context, user.getPfPicUrl(), imageView);

        // Set user name and full name
        userNameTextView.setText(user.getUserName());
        fullNameTextView.setText(user.getFullName());

        return convertView;
    }
}