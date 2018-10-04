package com.example.mdkhalidrazakhan.surveyprojectadmin.model;

public class Data {

/*
* surveyData.put("Name",Name);

                        surveyData.put("Sex",genderText);
                        surveyData.put("Age",ageText);
                        surveyData.put("Caste",casteText);
                        surveyData.put("Community",Com);
                        surveyData.put("Profession",Prof);
                        surveyData.put("AddMandal",Mandal);
                        surveyData.put("AddVill",Vill);
                        surveyData.put("AddSubVill",SubVill);
                        surveyData.put("AddPin",Pin);
                        surveyData.put("AddMob",Mob);
                        surveyData.put("Rating",RatStr);
                        surveyData.put("Goverment",govt_pref_text);
                        surveyData.put("Lat",lat.toString());
                        surveyData.put("Log",longt.toString());
                        surveyData.put("FullAddress",location);
* */

private String Sex,Age,Caste,Community,Profession,AddMandal,AddVill,AddSubvill,AddPin,AddMob,Rating,Goverment,Lat,Log,FullAddress,Date,By;

public Data(){}

    public Data(String sex, String age, String caste, String community, String profession, String addMandal, String addVill, String addSubvill, String addPin, String addMob, String rating, String goverment, String lat, String log, String fullAddress, String date, String by) {

        Sex = sex;
        Age = age;
        Caste = caste;
        Community = community;
        Profession = profession;
        AddMandal = addMandal;
        AddVill = addVill;
        AddSubvill = addSubvill;
        AddPin = addPin;
        AddMob = addMob;
        Rating = rating;
        Goverment = goverment;
        Lat = lat;
        Log = log;
        FullAddress = fullAddress;
        Date = date;
        By = by;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getCaste() {
        return Caste;
    }

    public void setCaste(String caste) {
        Caste = caste;
    }

    public String getCommunity() {
        return Community;
    }

    public void setCommunity(String community) {
        Community = community;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getAddMandal() {
        return AddMandal;
    }

    public void setAddMandal(String addMandal) {
        AddMandal = addMandal;
    }

    public String getAddVill() {
        return AddVill;
    }

    public void setAddVill(String addVill) {
        AddVill = addVill;
    }

    public String getAddSubvill() {
        return AddSubvill;
    }

    public void setAddSubvill(String addSubvill) {
        AddSubvill = addSubvill;
    }

    public String getAddPin() {
        return AddPin;
    }

    public void setAddPin(String addPin) {
        AddPin = addPin;
    }

    public String getAddMob() {
        return AddMob;
    }

    public void setAddMob(String addMob) {
        AddMob = addMob;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getGoverment() {
        return Goverment;
    }

    public void setGoverment(String goverment) {
        Goverment = goverment;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLog() {
        return Log;
    }

    public void setLog(String log) {
        Log = log;
    }

    public String getFullAddress() {
        return FullAddress;
    }

    public void setFullAddress(String fullAddress) {
        FullAddress = fullAddress;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBy() {
        return By;
    }

    public void setBy(String by) {
        By = by;
    }
}
