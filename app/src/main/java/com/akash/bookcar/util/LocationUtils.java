package com.akash.bookcar.util;

import android.location.Location;

public class LocationUtils {

    // Method to calculate distance between two locations using Haversine formula
    public static double calculateDistance(double startLatitude, double startLongitude,
                                           double endLatitude, double endLongitude) {
        // Radius of the earth in kilometers
        double earthRadius = 6371;

        // Convert latitude and longitude from degrees to radians
        double latDistance = Math.toRadians(endLatitude - startLatitude);
        double lonDistance = Math.toRadians(endLongitude - startLongitude);

        // Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startLatitude)) * Math.cos(Math.toRadians(endLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance
        return earthRadius * c;
    }
}

