package com.example.complaint;

public class OneModel {
    String image;
    String name;
    String age;
    String gender;
    String date;
    String time;
    String city;
    String address;
    String additionaladd;
    String number;

    public OneModel(String image,String name,String age, String gender,
                    String date,String time,String city, String address,String additionaladd,String number){
        this.image=image;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.date=date;
        this.time=time;
        this.city=city;
        this.address=address;
        this.additionaladd=additionaladd;
        this.number=number;

    }

    public String getImage() {return image;}

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getCity() {
        return city;
    }
    public String getAddress() {
        return address;
    }
    public String getAdditionaladd() {
        return additionaladd;
    }
    public String getNumber() {
        return number;
    }

}
