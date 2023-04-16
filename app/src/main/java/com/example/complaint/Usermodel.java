package com.example.complaint;

public class Usermodel {
    String country;
    String city;
    String name;
    String number;
    String uid;

    public Usermodel(String country, String city, String name, String number, String uid) {
        this.country = country;
        this.city = city;
        this.name = name;
        this.number = number;
        this.uid = uid;
    }

    public String getCountry() {return country;}

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getUid() {
        return uid;
    }
}
