/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.dto;

import com.mycompany.webtechnikonproject.model.PropertyOwner;




/**
 *
 * @author ADMIN
 */
public class PropertyOwnerDto {
  
  
    private int vat;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    public PropertyOwnerDto() {

    }

    public PropertyOwnerDto(PropertyOwner propertyOwner) {
        this.vat = propertyOwner.getVat();
        this.name = propertyOwner.getName();
        this.surname = propertyOwner.getSurname();
        this.address = propertyOwner.getAddress();
        this.phoneNumber = propertyOwner.getPhoneNumber();
        this.email = propertyOwner.getEmail();
        this.username = propertyOwner.getUsername();
        this.password = propertyOwner.getPassword();

    }

    public PropertyOwner asOwner() {
        PropertyOwner propertyOwner = new PropertyOwner();
        propertyOwner.setVat(vat);
        propertyOwner.setName(name);
        propertyOwner.setSurname(surname);
        propertyOwner.setAddress(address);
        propertyOwner.setPhoneNumber(phoneNumber);
        propertyOwner.setEmail(email);
        propertyOwner.setUsername(username);
        propertyOwner.setPassword(password);
        return propertyOwner;

    }


    

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
