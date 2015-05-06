package com.app.drugcorner32.dc_template.Data;

import java.io.Serializable;

/**
 * Created by Tarun on 03-05-2015.
 */
public class AddressDetails implements Serializable{
    private String firstName;
    private String lastName;
    private String houseInfo;
    private String streetInfo;
    private String landmark;

    public AddressDetails(){
    }

    //Getters
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getHouseInfo(){
        return houseInfo;
    }

    public String getStreetInfo(){
        return streetInfo;
    }

    public String getLandmark(){
        return landmark;
    }

    //Setters
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setHouseInfo(String houseInfo){
        this.houseInfo = houseInfo;
    }

    public void setStreetInfo(String streetInfo){
        this.streetInfo = streetInfo;
    }

    public void setLandmark(String landmark){
        this.landmark = landmark;
    }

}
