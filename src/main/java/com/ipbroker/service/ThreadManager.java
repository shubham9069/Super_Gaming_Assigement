package com.ipbroker.service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ipbroker.model.ProviderMetricNode;
import com.ipbroker.model.ThirdPartyService;
import com.ipbroker.model.Threshold;

public class ThreadManager {
    private volatile ScheduledExecutorService scheduler;
    private final Map<ThirdPartyService, Threshold> thresholdMap;
    private final Map<ThirdPartyService, ProviderMetricNode[]> providerMetricMap;
    private volatile ResetProviderApiLimit resetProvider;
    private volatile FilterProviderLinkedListBasedOnTime filterProvider;

    public ThreadManager(
            Map<ThirdPartyService, Threshold> thresholdMap,
            Map<ThirdPartyService, ProviderMetricNode[]> providerMetricMap) {
        System.out.println("Initializing ThreadManager...");
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.thresholdMap = thresholdMap;
        this.providerMetricMap = providerMetricMap;
        
        // Initialize services
        System.out.println("Creating ResetProviderApiLimit...");
        this.resetProvider = new ResetProviderApiLimit();
        System.out.println("Creating FilterProviderLinkedListBasedOnTime...");
        this.filterProvider = new FilterProviderLinkedListBasedOnTime();
        
        System.out.println("ThreadManager initialization complete");
    }

    public void start() {
        System.out.println("Starting scheduled tasks...");
        // Reset API limits every 10 seconds
        scheduler.scheduleAtFixedRate(
            () -> {
                System.out.println("Executing reset task...");
                resetProvider.reset(thresholdMap);
            },
            0, 10, TimeUnit.SECONDS
        );

        // Filter provider metrics every 10 seconds
        scheduler.scheduleAtFixedRate(
            () -> {
                System.out.println("Executing filter task...");
                filterProvider.filter(providerMetricMap);
            },
            0, 10, TimeUnit.SECONDS
        );
        System.out.println("Scheduled tasks started");
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
} 