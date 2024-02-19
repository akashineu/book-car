package com.akash.bookcar.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.bookcar.MainActivity;
import com.akash.bookcar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class OtpVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static final String TAG = "OtpVerificationActivity";

    private EditText editTextNumberOtp1;
    private EditText editTextNumberOtp2;

    private EditText editTextNumberOtp3;
    private EditText editTextNumberOtp4;
    private EditText editTextNumberOtp5;
    private EditText editTextNumberOtp6;

    private Button buttonOtpContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        editTextNumberOtp1 = findViewById(R.id.editTextNumberOtp1);
        editTextNumberOtp2 = findViewById(R.id.editTextNumberOtp2);
        editTextNumberOtp3 = findViewById(R.id.editTextNumberOtp3);
        editTextNumberOtp4 = findViewById(R.id.editTextNumberOtp4);
        editTextNumberOtp5 = findViewById(R.id.editTextNumberOtp5);
        editTextNumberOtp6 = findViewById(R.id.editTextNumberOtp6);
        buttonOtpContinue = findViewById(R.id.buttonOtpContinue);


        mAuth = FirebaseAuth.getInstance();



        buttonOtpContinue.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String code = editTextNumberOtp1.getText().toString() + editTextNumberOtp2.getText().toString()
                        + editTextNumberOtp3.getText().toString() + editTextNumberOtp4.getText().toString()
                        + editTextNumberOtp5.getText().toString()  + editTextNumberOtp6.getText().toString();

                // Create a PhoneAuthCredential object
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(MainActivity.mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        });



        // Move the cursor
        editTextNumberOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1){
                    // Moving the cursor to next editText
                    editTextNumberOtp2.requestFocus();

                }
            }
        });

        editTextNumberOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1){
                    editTextNumberOtp3.requestFocus();
                }
            }
        });

        editTextNumberOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1){
                    editTextNumberOtp4.requestFocus();
                }
            }
        });

        editTextNumberOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1){
                    editTextNumberOtp5.requestFocus();
                }
            }
        });

        editTextNumberOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextNumberOtp6.requestFocus();
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            Intent intent = new Intent(OtpVerificationActivity.this, AuthSuccessActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpVerificationActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}