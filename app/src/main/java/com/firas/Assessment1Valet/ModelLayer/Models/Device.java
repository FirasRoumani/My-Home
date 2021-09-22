package com.firas.Assessment1Valet.ModelLayer.Models;

import java.io.Serializable;

public class Device implements Serializable {
    int Id;
    String Type;
    double Price;
    String Currency;
    boolean IsFavorite;
    String ImageUrl;
    String Title;
    String Description;
    double Rating;
    int Status;

    public Device() {

    }

    public Device(int id, String type, double price, String currency, boolean isFavorite, String imageUrl, String title, String description, double rating, int status) {
        Id = id;
        Type = type;
        Price = price;
        Currency = currency;
        IsFavorite = isFavorite;
        ImageUrl = imageUrl;
        Title = title;
        Description = description;
        Rating = rating;
        Status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public boolean isFavorite() {
        return IsFavorite;
    }

    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
