package com.example.app_doc_truyen.Model;

public class User {
    public int IDuser ;
    public String Name;
    public String Avartar;
    public String DateOfBirth;
    public String SDT;
    public String GioiTinh;
    public String Email;
    public String Pass;
    public User() {
    }

    public User(int IDuser, String name, String avartar, String dateOfBirth, String SDT, String gioiTinh, String email, String pass) {
        this.IDuser = IDuser;
        Name = name;
        Avartar = avartar;
        DateOfBirth = dateOfBirth;
        this.SDT = SDT;
        GioiTinh = gioiTinh;
        Email = email;
        Pass = pass;
    }

    public int getIDuser() {
        return IDuser;
    }

    public void setIDuser(int IDuser) {
        this.IDuser = IDuser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvartar() {
        return Avartar;
    }

    public void setAvartar(String avartar) {
        Avartar = avartar;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
