package com.example.moham.ticketyourtrain;

/**
 * Created by moham on 5/14/2016.
 */

public class UserModel {


    private int id;
    private String name;
    private String phone;
    private byte[] image;
    private String birth_date;
    private String gender;
    private double balance;

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return gender;
    }

    public void setType(String type) {
        this.gender = type;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) throws Exception {
        if(name.matches("[a-z]{6,12}"))
        this.name = name;
        else{
            throw new Exception("Invalid name");
        }
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws Exception {
       if (phone.matches("01\\d{9}"))
            this.phone = phone;
       else{
            throw new Exception("Enter Valid phone");
        }
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
