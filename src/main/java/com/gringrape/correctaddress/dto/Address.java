package com.gringrape.correctaddress.dto;

public class Address {
    private final String region;
    private final String city;
    private final String street;

    public Address(String region, String city, String street) {
        this.region = region;
        this.city = city;
        this.street = street;
    }

    public String[] districtNames() {
        return new String[]{region, city, street};
    }
}
