package com.example.alumnihub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.alumnihub.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ArrayList<String> userNameList = new ArrayList<>(); // ami ata likha raklam pora, tui tor use moton ata ka change koris
    // jokhon user search er logic ta likbi
    // Arraylist nilam karon parameterized constructor use kora MainActivity thaka List of user data pass kora Akhana set kora use korta parbi
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private AutoCompleteTextView autoCompleteTextView;
    private ImageView searchUserImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // all are those demo input, test korar jonno
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        searchUserImg = view.findViewById(R.id.searchUserAccount);

        userNameList.add("Subhajit");
        userNameList.add("Kunal");
        userNameList.add("Gourav");
        userNameList.add("Rajib");
        userNameList.add("Shubham");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, userNameList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUserImg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}