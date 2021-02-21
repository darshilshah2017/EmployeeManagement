package com.infy.dto;

public class AddressDTO {

    private String addressLine;
    private String city;
    private String state;
    private String pincode;

    public AddressDTO(){}

    public AddressDTO(String addressLine, String city, String state, String pincode) {
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }
}
