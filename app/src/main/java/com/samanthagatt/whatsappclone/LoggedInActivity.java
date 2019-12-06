package com.samanthagatt.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        Button mLogoutButton = findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                presentLoginActivity();
            }
        });

        Button mFindUserButton = findViewById(R.id.findUserButton);
        mFindUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FindUserActivity.class));
            }
        });
    }

    private void presentLoginActivity() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            return;
        }
    }
}
