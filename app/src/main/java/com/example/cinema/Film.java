package com.example.cinema;

public class Film {

    private String name;
    private String duration;
    private String price;
    private String genre;

    public Film() {
        // Required empty public constructor for Firebase
    }

    public Film(String name, String duration, String price, String genre) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
