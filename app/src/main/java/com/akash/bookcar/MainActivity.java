package com.akash.bookcar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.akash.bookcar.booking.HomeRecycler;
import com.akash.bookcar.login.OtpVerificationActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static String mVerificationId = null;

    private Button buttonContinue;

    private EditText editTextPhone;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonContinue = findViewById(R.id.buttonContinue);
        editTextPhone = findViewById(R.id.editTextPhone);

        buttonContinue.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeRecycler.class));
            finish();
        } else {


            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.
                    Log.d(TAG, "onVerificationCompleted:" + credential);

                    mAuth.signInWithCredential(credential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e);
                    String error = "";
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        error = "FirebaseAuthInvalidCredentialsException";
                        Log.e(TAG, "onVerificationFailed: " + error);
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        error = "FirebaseTooManyRequestsException";
                        Log.e(TAG, "onVerificationFailed: " + error);
                        // The SMS quota for the project has been exceeded
                    } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                        error = "FirebaseAuthMissingActivityForRecaptchaException";
                        Log.e(TAG, "onVerificationFailed: " + error);
                        // reCAPTCHA verification attempted with null Activity
                    }

                    // Show a message and update the UI
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d(TAG, "onCodeSent:" + verificationId);

                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    //mResendToken = token;
                    Toast.makeText(MainActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();

                }
            };

            editTextPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    buttonContinue.setEnabled(s.length() == 10);
                }
            });
            buttonContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userPhoneNumber = "+91 " + editTextPhone.getText().toString().trim();

                    // Send a verification code to the user's phone
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(userPhoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(MainActivity.this)                 // (optional) Activity for callback binding
                            // If no activity is passed, reCAPTCHA verification can not be used.
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);

                    Intent intent = new Intent(MainActivity.this, OtpVerificationActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }
}