package com.akash.bookcar.util;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akash.bookcar.R;
import com.akash.bookcar.booking.DetailsActivity;
import com.akash.bookcar.booking.HomeRecycler;
import com.akash.bookcar.model.Car;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    private ArrayList<Car> carArrayList;

    public CustomAdapter(Context context, ArrayList<Car> carArrayList) {
        this.context = context;
        this.carArrayList = carArrayList;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewCar;
        private TextView textViewModel;
        private TextView textViewRent;
        private ImageView imageViewStar;
        private TextView textViewRating;
        private Button buttonBookNow;
        private Button buttonDetails;

        public ViewHolder(View view) {
            super(view);

            // Define click listener for the ViewHolder's View
            imageViewCar = view.findViewById(R.id.imageViewCar);
            textViewModel = view.findViewById(R.id.textViewModel);
            textViewRent = view.findViewById(R.id.textViewRent);
            imageViewStar = view.findViewById(R.id.imageViewStar);
            textViewRating = view.findViewById(R.id.textViewRating);
            buttonBookNow = view.findViewById(R.id.buttonBookNow);
            buttonDetails = view.findViewById(R.id.buttonDetails);

            buttonBookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Access the car item associated with the clicked button
                        Car clickedCar = carArrayList.get(position);

                        // Perform the "Book Now" action
                        // Example: Show a toast message
//                        Util.showDatePickerDialog(context);


                        Toast.makeText(context, "Book Now: " + clickedCar.getModelNo(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            buttonDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // Here can you write the code to start activity DetailsActivity
                    Car clickedCar = carArrayList.get(position);

                    // Start DetailsActivity and pass necessary data (if any)
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("CAR_MODEL", clickedCar.getModelNo());
                    intent.putExtra("CAR_RENT", clickedCar.getRentPerDay());
                    intent.putExtra("CAR_RATING", clickedCar.getRating());
                    intent.putExtra("CAR_IMAGE", clickedCar.getImage());
                    // Add more data if needed

                    context.startActivity(intent);

                }
            });

        }

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.imageViewCar.setImageResource(carArrayList.get(position).getImage());
        viewHolder.textViewModel.setText(carArrayList.get(position).getModelNo());
        viewHolder.textViewRent.setText(carArrayList.get(position).getRentPerDay());
        viewHolder.textViewRating.setText(carArrayList.get(position).getRating());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("Firebase", "getItemCount: " + carArrayList.size());
        return carArrayList.size();
    }
}

