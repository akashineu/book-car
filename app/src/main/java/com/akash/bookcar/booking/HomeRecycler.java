package com.akash.bookcar.booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.bookcar.R;
import com.akash.bookcar.location.GeocodingTask;
import com.akash.bookcar.model.Car;
import com.akash.bookcar.util.CustomAdapter;
import com.akash.bookcar.util.LocationUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeRecycler extends AppCompatActivity {

    private CustomAdapter adapter;

    private RecyclerView recyclerView;

    private ArrayList<Car> carArrayList = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationClient;

    private TextView textViewAddress;

    private FirebaseFirestore db;

    public static final String TAG = "HomeRecycler";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recycler);

        Log.d("Location", "onCreate: ");

        db = FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.recyclerView);
        textViewAddress = findViewById(R.id.textViewAddress);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        // Create a new user with a first and last name
//        Map<String, Object> bmw = new HashMap<>();
//        bmw.put("modelNo", "BMW");
//        bmw.put("image", R.drawable.bmw);
//        bmw.put("rent", "3000/day");
//        bmw.put("rating", "4.9 (180 ratings)");
//
//// Add a new document with a generated ID
//        db.collection("cars").document("BMW")
//                .set(bmw).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "onSuccess: BMW added");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: " + e);
//                    }
//                });
//
//        Map<String, Object> mercedes = new HashMap<>();
//        mercedes.put("modelNo", "Mercedes");
//        mercedes.put("image", R.drawable.mercedes);
//        mercedes.put("rent", "2500/day");
//        mercedes.put("rating", "4.8 (200 ratings)");
//
//// Add a new document with a generated ID
//        db.collection("cars").document("Mercedes")
//                .set(mercedes).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "onSuccess: Mercedes added");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: " + e);
//                    }
//                });
//
//        Map<String, Object> rangerover = new HashMap<>();
//        rangerover.put("modelNo", "Range Rover");
//        rangerover.put("image", R.drawable.rangerover);
//        rangerover.put("rent", "3500/day");
//        rangerover.put("rating", "4.5 (160 ratings)");
//
//// Add a new document with a generated ID
//        db.collection("cars").document("Range Rover")
//                .set(rangerover).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "onSuccess: Range Rover");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: " + e);
//                    }
//                });

        //better method to add items

//        db.collection("cars").document("BMW").set(new Car("BMW", R.drawable.bmw, " ₹ 3000/day", "4.9 (180 ratings)"));
//        db.collection("cars").document("Mercedes").set(new Car("Mercedes", R.drawable.mercedes, " ₹ 2500/day", "4.8 (200 ratings)"));
//        db.collection("cars").document("Range Rover").set(new Car("Range Rover", R.drawable.rangerover, " ₹ 3500/day", "4.5 (160 ratings)"));

        db.collection("cars").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                carArrayList.clear();
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Car car = snapshot.toObject(Car.class);
                    carArrayList.add(car);
                }
                adapter = new CustomAdapter(HomeRecycler.this, carArrayList);
                recyclerView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e);
            }
        });


//        db.collection("cars")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                //Todo : Check the warnings
//                                carArrayList.add(new Car(document.getString("modelNo"), Math.toIntExact(document.getLong("image")), document.getString("rent"),
//                                        document.getString("rating")));
//
//                                Log.d("Firestore", document.getId() + " => " + document.getData());
//                                Log.d("Firestore", "onComplete: " +document.get("modelNo"));
//                            }
//                            CustomAdapter adapter = new CustomAdapter(HomeRecycler.this, carArrayList);
//                            recyclerView.setAdapter(adapter);
//                            Log.d("Firestore", "onComplete: " + carArrayList.size());
//                        } else {
//                            Log.w("Firestore", "Error getting documents.", task.getException());
//                        }
//
//                    }
//                });

//        carArrayList.add(new Car("BMW", R.drawable.bmw, " ₹ 3000/day", "4.9 (180 ratings)"));
//        carArrayList.add(new Car("Mercedes", R.drawable.mercedes, " ₹ 2500/day", "4.8 (200 ratings)"));
//        carArrayList.add(new Car("Range Rover", R.drawable.rangerover, " ₹ 3500/day", "4.5 (160 ratings)"));


        Log.d("Firestore", "onCreate: " + carArrayList.size());


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Location", "onResume: ");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {

//                                double distance = LocationUtils.calculateDistance(location.getLatitude(), location.getLongitude(), 26.8474, 85.0104);
//
//                                if(distance > 10){
//                                    recyclerView.setVisibility(View.INVISIBLE);
//                                    Toast.makeText(HomeRecycler.this, "Sorry We are not serviceable at your location", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    recyclerView.setVisibility(View.VISIBLE);
//                                }

                                // Logic to handle location object
                                GeocodingTask geocodingTask = new GeocodingTask(HomeRecycler.this, new GeocodingTask.GeocodingListener() {
                                    @Override
                                    public void onGeocodingCompleted(String address) {
                                        Log.d("Location", "onGeocodingCompleted: " + address);

                                        textViewAddress.setText(address);
                                    }
                                });
                                geocodingTask.execute(location.getLatitude(), location.getLongitude());
                                Log.d("Location", "onSuccess: " + location.getLatitude() + " " + location.getLongitude());
                            } else {
                                Log.d("Location", "onSuccess: location is null");
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.collection("cars").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.d(TAG, "onEvent: " + error);
                }else{

                    if (value != null) {
                        carArrayList.clear();
                        Log.d(TAG, "onEvent: before for loop" + carArrayList.size());
                        for(QueryDocumentSnapshot c : value){
                            Car car = c.toObject(Car.class);
                            carArrayList.add(car);
                            Log.d(TAG, "onEvent: " + car.getModelNo() + " " + car.getRating());
                        }
                        Log.d(TAG, "onEvent: after for loop" + carArrayList.size());

                        adapter = new CustomAdapter(HomeRecycler.this, carArrayList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });
    }


}