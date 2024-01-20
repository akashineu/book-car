package com.akash.bookcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText editTextNumberOtp1;
    private EditText editTextNumberOtp2;

    private EditText editTextNumberOtp3;
    private EditText editTextNumberOtp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        editTextNumberOtp1 = findViewById(R.id.editTextNumberOtp1);
        editTextNumberOtp2 = findViewById(R.id.editTextNumberOtp2);
        editTextNumberOtp3 = findViewById(R.id.editTextNumberOtp3);
        editTextNumberOtp4 = findViewById(R.id.editTextNumberOtp4);

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

        editTextNumberOtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}