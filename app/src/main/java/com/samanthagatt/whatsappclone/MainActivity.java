package com.samanthagatt.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText mPhoneNumberEditText, mCodeEditText;
    private String mVerificationCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        presentLoggedInActivityIfAble();

        mPhoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        mCodeEditText = findViewById(R.id.codeEditText);
        final Button mLoginButton = findViewById(R.id.loginButton);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                loginWithPhoneAuthCred(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // TODO: Handle exception
                Log.println(Log.DEBUG, "exception thrown",e.toString());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mLoginButton.setText("Verify Phone Number");

                mVerificationCode = s;
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (mVerificationCode != null) {
            // Verification code already sent
            PhoneAuthCredential mCred = PhoneAuthProvider.getInstance().getCredential(mVerificationCode, mCodeEditText.getText().toString());
            loginWithPhoneAuthCred(mCred);
        } else {
            // Need to send verification code
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    mPhoneNumberEditText.getText().toString(),
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks
            );
        }
    }

    private void loginWithPhoneAuthCred(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            presentLoggedInActivityIfAble();
                        } else {
                            // TODO: Handle exception
                            Log.println(Log.DEBUG, "Task not successful", task.getException().toString());
                        }
                    }
                }
        );
    }

    private void presentLoggedInActivityIfAble() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), LoggedInActivity.class));
            // Makes sure user cannot come back to page
            finish();
            return;
        }
    }
}