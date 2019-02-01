package com.example.rateaurant;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable{

    // keep track of name, style, rating, website link, price, location
    private String name;
    private String style;
    private double rating; // 0 -> 5, increments of .5
    private String websiteLink;
    private String address;
    private int price; // 1 -> 5, increments of 1
    private String objectId;
    private String ownerId;

    // need a default constructor for Backendless, generally a good idea

    public Restaurant(){}

    public Restaurant(String name, String style, String websiteLink, String address) {
        this.name = name;
        this.style = style;
        this.websiteLink = websiteLink;
        this.address = address;
    }

    public Restaurant(String name, String style, double rating, String websiteLink, String address, int price) {
        this.name = name;
        this.style = style;
        this.rating = rating;
        this.websiteLink = websiteLink;
        this.address = address;
        this.price = price;
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        style = in.readString();
        rating = in.readDouble();
        websiteLink = in.readString();
        address = in.readString();
        price = in.readInt();
        objectId = in.readString();
        ownerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(style);
        dest.writeDouble(rating);
        dest.writeString(websiteLink);
        dest.writeString(address);
        dest.writeInt(price);
        dest.writeString(objectId);
        dest.writeString(ownerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", style='" + style + '\'' +
                ", personalRating=" + rating +
                ", websiteLink='" + websiteLink + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }
}
