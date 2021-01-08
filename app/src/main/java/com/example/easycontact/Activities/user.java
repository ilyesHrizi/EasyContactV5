package com.example.easycontact.Activities;

public class user {
    String cin;
    String Email;
    String Name;
    String Lastname;
    String Approve;
    String Statut;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    String photo;

    public user() {
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getApprove() {
        return Approve;
    }

    public void setApprove(String approve) {
        Approve = approve;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public user(String cin, String email, String name, String lastname, String approve, String Statut, String photo) {
        this.cin = cin;
        Email = email;
        Name = name;
        Lastname = lastname;
        Approve = approve;
        Statut = Statut;
        photo = photo;
    }
}
