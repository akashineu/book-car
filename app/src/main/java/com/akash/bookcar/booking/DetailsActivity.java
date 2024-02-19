package com.akash.bookcar.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.bookcar.R;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageViewCar;
    private TextView textViewModel;
    private TextView textViewRent;
    private TextView textViewRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageViewCar = findViewById(R.id.imageViewCar);
        textViewModel = findViewById(R.id.textViewModel);
        textViewRent = findViewById(R.id.textViewRent);
        textViewRating = findViewById(R.id.textViewRating);

        Intent intent = getIntent();

        if (intent != null) {
            String carModel = intent.getStringExtra("CAR_MODEL");
            int carImage = intent.getIntExtra("CAR_IMAGE", 0);
            String carRent = intent.getStringExtra("CAR_RENT");
            String carRating = intent.getStringExtra("CAR_RATING");



            // Now you have the carModel, you can use it in your DetailsActivity as needed
            imageViewCar.setImageResource(carImage);
            textViewModel.setText(carModel);
            textViewRent.setText(carRent);
            textViewRating.setText(carRating);
        }
    }
}