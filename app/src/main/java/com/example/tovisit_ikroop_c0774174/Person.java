package com.example.tovisit_ikroop_c0774174;

public class Person {
    int id;

    String firstname, lastname, emailaddress, phone, address;

    public Person(int id, String firstname, String lastname, String emailaddress, String address, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailaddress = emailaddress;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getPhone() {
        return phone;
    }


    public String getAddress() {
        return address;
    }

}


