package com.samanthagatt.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    private ArrayList<UserObject> mUserList;

    private RecyclerView mUserListRecyclerView;
    // Controls where each card is shown in the list
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        mUserList = new ArrayList<>();

        setupUserListRecyclerView();
    }

    private void setupUserListRecyclerView() {
        mUserListRecyclerView = findViewById(R.id.userListRecyclerView);
        // Lets it scroll seamlessly
        mUserListRecyclerView.setNestedScrollingEnabled(false);
        mUserListRecyclerView.setHasFixedSize(false);

        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mUserListRecyclerView.setLayoutManager(mUserListLayoutManager);

        mUserListAdapter = new UserListAdapter(mUserList);
        mUserListRecyclerView.setAdapter(mUserListAdapter);
    }
}
