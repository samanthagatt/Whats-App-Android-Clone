package com.samanthagatt.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

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
        getContactList();
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

    private void getContactList() {
        Cursor mContacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(mContacts.moveToNext()) {
            String mName = mContacts.getString(mContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mPhoneNumber = mContacts.getString(mContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            UserObject mUser = new UserObject(mName, mPhoneNumber);
            mUserList.add(mUser);
            mUserListAdapter.notifyDataSetChanged();
        }
    }
}
