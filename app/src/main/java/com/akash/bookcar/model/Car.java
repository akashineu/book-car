package com.akash.bookcar.model;

public class Car {
    private String modelNo;
    private int image;
    private String rentPerDay;

    private String rating;

    public Car() {
    }

    public Car(String modelNo, int image, String rentPerDay, String rating) {
        this.modelNo = modelNo;
        this.image = image;
        this.rentPerDay = rentPerDay;
        this.rating = rating;
    }


    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
