package com.ipbroker.controller;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ipbroker.enums.ProviderMetricsStatusEnum;
import com.ipbroker.model.ProviderMetricNode;
import com.ipbroker.model.Response;
import com.ipbroker.model.ThirdPartyService;
import com.ipbroker.model.Threshold;
import com.ipbroker.service.FindBestServiceProvider;
import com.ipbroker.service.ThreadManager;


public class GeoLocationFinder {
    private static final int THRESHOLD_LIMIT = 2;
    private static final int THRESHOLD_TIME = 1000*60;
    private final Map<ThirdPartyService, Threshold> threasholdMap;
    private final Map<ThirdPartyService, ProviderMetricNode[]> providerMetricMap;
    private volatile ThreadManager threadManager;
    
    private static void printGreen(String message) {
        System.out.println("\u001B[32m" + message + "\u001B[0m");
    }

    public GeoLocationFinder() {
        this.threasholdMap = new ConcurrentHashMap<>();
        this.providerMetricMap = new ConcurrentHashMap<>();
        defaultServiceProvider();
        this.threadManager = new ThreadManager(threasholdMap, providerMetricMap);
        this.threadManager.start();
    }

    private void defaultServiceProvider() {
        // service 1 
        ThirdPartyService freeipapi = new ThirdPartyService("freeipapi", "https://freeipapi.com/api/json/:ipAddress");
        threasholdMap.put(freeipapi, new Threshold(THRESHOLD_LIMIT, THRESHOLD_TIME));
        providerMetricMap.put(freeipapi, new ProviderMetricNode[2]); // [0] head, [1] tail

        // service 2
        ThirdPartyService ipapi = new ThirdPartyService("ipapi", "https://ipapi.co/:ipAddress/json/");
        threasholdMap.put(ipapi, new Threshold(THRESHOLD_LIMIT, THRESHOLD_TIME));
        providerMetricMap.put(ipapi, new ProviderMetricNode[2]); // [0] head, [1] tail
    }
    
    public String getGeoLocation(String ipAddress) {
        ThirdPartyService bestServiceProvider = new FindBestServiceProvider().find(threasholdMap);
        if (bestServiceProvider == null) {
            System.out.println("No service provider found");
            return null;
        }
       
        printGreen("Using " + bestServiceProvider.serviceName + " to get geo location");
        Response data = bestServiceProvider.getDataByIpAddress(ipAddress);
        if (data.status == ProviderMetricsStatusEnum.ERROR) {
            System.out.println("Error: " + data.result);
            return null;
        }
        threasholdMap.get(bestServiceProvider).decreaseLimit();
        addProviderMetricNode(bestServiceProvider, data);
        
        return data.result;
    }

    public void addProviderMetricNode(ThirdPartyService provider, Response response) {
        ProviderMetricNode node = new ProviderMetricNode(response.createdAt, response.responseTime, response.result, response.status);
        ProviderMetricNode[] providerMetricNodes = providerMetricMap.get(provider);
        if (providerMetricNodes[0] == null) { // head is null
            providerMetricNodes[0] = node;
            providerMetricNodes[1] = node;
        } else { 
            // if head is not null, link new node to tail
            providerMetricNodes[1].linkNewNode(node);
            providerMetricNodes[1] = node;
        }
    }
    
    // Add shutdown method to clean up threads
    public void shutdown() {
        if (threadManager != null) {
            threadManager.shutdown();
        }
    }
}
