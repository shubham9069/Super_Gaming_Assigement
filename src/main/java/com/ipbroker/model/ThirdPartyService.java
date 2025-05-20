package com.ipbroker.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ThirdPartyService {
    public String serviceName;
    public int totalResponseTime;
    public int errorCount;
    public int totolCount;
    public String baseUrl;

    

    public ThirdPartyService(String serviceName, String baseUrl) {
        this.serviceName = serviceName;
        this.baseUrl = baseUrl;
        this.totalResponseTime = 0;
        this.errorCount = 0;
        this.totolCount = 0;
    }

    public void update(int totalResponseTime, int errorCount, int totolCount) {
        this.totalResponseTime = totalResponseTime;
        this.errorCount = errorCount;
        this.totolCount = totolCount;
    }
    public int addResponseTime(int responseTime) {
        totolCount++;
        totalResponseTime += responseTime;
        return totalResponseTime;
    }

    public int addError() {
        errorCount++;
        return errorCount;
    }

    public int getAvgResponseTime() {
        if (totolCount == 0) {
            return 0;
        }
        return totalResponseTime / totolCount;
    }

    public int getErrorRate() {
        if (totolCount == 0) {
            return 0;
        }
        return errorCount / totolCount;
    }

    public int subtractResponseTime(int responseTime) {
        totolCount--;
        totalResponseTime -= responseTime;
        return totalResponseTime;
    }


    public int subtractError() {
        errorCount--;
        return errorCount;
    }
    public Response getDataByIpAddress(String ipAddress) {
        String url = baseUrl.replace(":ipAddress", ipAddress);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
            
            long startTime = System.currentTimeMillis();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            long endTime = System.currentTimeMillis();
            int responseTime = (int)(endTime - startTime);

            if (response.statusCode() == 200) {
                this.addResponseTime(responseTime);
                return new Response(responseTime).success(response.body());
            } else {
                this.addError();
                return new Response(responseTime).error("HTTP Error: " + response.statusCode());
            }
        } catch (Exception e) {
            this.addError();
            System.out.println("Error: " + e.getMessage());
            return new Response(0).error("Error: " + e.getMessage());
        }
    }
    
        
        
        
        
    }
