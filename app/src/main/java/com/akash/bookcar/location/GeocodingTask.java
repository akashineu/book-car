package com.akash.bookcar.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingTask extends AsyncTask<Double, Void, String> {
    private Context context;
    private GeocodingListener listener;

    public GeocodingTask(Context context, GeocodingListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Double... params) {
        double latitude = params[0];
        double longitude = params[1];

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                // Build the address string
                StringBuilder addressStringBuilder = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressStringBuilder.append(address.getAddressLine(i));

                    if (i < address.getMaxAddressLineIndex()) {
                        addressStringBuilder.append(", ");
                    }
                }

                return addressStringBuilder.toString();
            }
        } catch (IOException e) {
            Log.e("GeocodingTask", "Error getting address", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String address) {
        if (listener != null) {
            listener.onGeocodingCompleted(address);
        }
    }

    public interface GeocodingListener {
        void onGeocodingCompleted(String address);
    }
}

