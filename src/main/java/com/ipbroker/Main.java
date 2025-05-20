package com.ipbroker;

import com.ipbroker.controller.GeoLocationFinder;

public class Main {
    public static void main(String[] args) {
        System.out.println("API BROKER service started");


        GeoLocationFinder geoLocationFinder = new GeoLocationFinder();
        String[] ipAddresses = {"127.0.0.1", "192.168.0.104", "192.168.0.104", "192.168.0.104", "192.168.0.104"};
        String result;
        try {   
            for (String ipAddress : ipAddresses) {
                result = geoLocationFinder.getGeoLocation(ipAddress);
                System.out.println(ipAddress + " : " + result);
            }
        Thread.sleep(12000);    
        for (String ipAddress : ipAddresses) {
            result = geoLocationFinder.getGeoLocation(ipAddress);
                System.out.println(ipAddress + " : " + result);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}