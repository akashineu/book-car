package com.akash.bookcar.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.akash.bookcar.R;
import com.akash.bookcar.booking.HomeRecycler;

public class AuthSuccessActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_sucess);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthSuccessActivity.this, HomeRecycler.class);
                startActivity(intent);
                finish();
            }
        });
    }
}