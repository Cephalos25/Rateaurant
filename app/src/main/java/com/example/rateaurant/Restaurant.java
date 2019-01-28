package com.example.rateaurant;

public class Restaurant {

    // keep track of name, style, rating, website link, price, location
    private String name;
    private String style;
    private double personalRating; // 0 -> 5, increments of .5
    private String websiteLink;
    private String address;
    private int price; // 1 -> 5, increments of 1

    // need a default constructor for Backendless, generally a good idea

    public Restaurant() {}

    public Restaurant(String name, String style, String websiteLink, String address) {
        this.name = name;
        this.style = style;
        this.websiteLink = websiteLink;
        this.address = address;
    }

    public Restaurant(String name, String style, double personalRating, String websiteLink, String address, int price) {
        this.name = name;
        this.style = style;
        this.personalRating = personalRating;
        this.websiteLink = websiteLink;
        this.address = address;
        this.price = price;
    }

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

    public double getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(double personalRating) {
        this.personalRating = personalRating;
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", style='" + style + '\'' +
                ", personalRating=" + personalRating +
                ", websiteLink='" + websiteLink + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }
}
