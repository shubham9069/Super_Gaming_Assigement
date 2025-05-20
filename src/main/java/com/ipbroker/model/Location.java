package com.ipbroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a geographical location with country and city information.
 */
public class Location {
    @JsonProperty("country")
    private final String country;
    
    @JsonProperty("city")
    private final String city;
    
    @JsonProperty("region")
    private final String region;
    
    @JsonProperty("postal_code")
    private final String postalCode;
    
    @JsonProperty("latitude")
    private final Double latitude;
    
    @JsonProperty("longitude")
    private final Double longitude;

    public Location(String country, String city) {
        this(country, city, null, null, null, null);
    }

    public Location(String country, String city, String region, String postalCode, 
                   Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getRegion() { return region; }
    public String getPostalCode() { return postalCode; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }
} 