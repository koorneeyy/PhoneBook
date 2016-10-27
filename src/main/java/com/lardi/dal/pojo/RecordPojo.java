package com.lardi.dal.pojo;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RecordPojo {
    int id;
    @NotNull
    @Size(min=4, max=50,message = "Кількість символів має бути в діапазоні 4-50")
    @Pattern(regexp = "[^;',\"&$]{4,50}",message = "Заборонено вводити спец символи")
    String fName;

    @NotNull
    @Size(min=4, max=50,message = "Кількість символів має бути в діапазоні 4-50")
    @Pattern(regexp = "[^;',\"&$]{4,50}",message = "Заборонено вводити спец символи")
String sName;

    @NotNull
    @Size(min=4, max=50,message = "Кількість символів має бути в діапазоні 4-50")
    @Pattern(regexp = "[^;',\"&$]{4,50}",message = "Заборонено вводити спец символи")
String mName;

    @NotNull
    @Pattern(regexp = "[+][3][8][\\d]{10}",message = "Номер телефону має бути у форматі +38XXXXXXXXXX")
String mPhone;

    @Pattern(regexp = "[+][3][8][\\d]{10}|",message = "Номер телефону має бути у форматі +38XXXXXXXXXX")
    String hPhone;

    @Size(min=0, max=100,message = "Кількість символів має бути в діапазоні 0-100")
    @Pattern(regexp = "[^;'\"&$]{4,50}|",message = "Заборонено вводити спец символи")
    String adress;


    @Pattern(regexp = "(^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$)|",message = "Електронна скринька вказана з помилкою")
String mail;


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String gethPhone() {
        return hPhone;
    }

    public void sethPhone(String hPhone) {
        this.hPhone = hPhone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return fName + ";" + sName+ ";"+mName+ ";" +mPhone + ";" + hPhone + ";" + adress + ";" + mail+System.lineSeparator();
    }
}





