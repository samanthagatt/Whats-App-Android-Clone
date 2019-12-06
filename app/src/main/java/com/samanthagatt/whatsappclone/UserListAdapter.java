package com.samanthagatt.whatsappclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private ArrayList<UserObject> mUserList;

    public UserListAdapter(ArrayList<UserObject> userList) {
        this.mUserList = userList;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, false);
        RecyclerView.LayoutParams mLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutView.setLayoutParams(mLayoutParams);
        UserListViewHolder mRecyclerView = new UserListViewHolder(mLayoutView);
        return mRecyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        UserObject mUser = mUserList.get(position);
        holder.mNameTextView.setText(mUser.getName());
        holder.mPhoneNumberTextView.setText(mUser.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    public class UserListViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTextView, mPhoneNumberTextView;

        public UserListViewHolder(View view) {
            super(view);

            mNameTextView = view.findViewById(R.id.nameTextView);
            mPhoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        }
    }
}
