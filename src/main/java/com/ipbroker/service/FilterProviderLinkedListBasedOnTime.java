package com.ipbroker.service;

import java.time.Instant;
import java.util.Map;

import com.ipbroker.enums.ProviderMetricsStatusEnum;
import com.ipbroker.model.ProviderMetricNode;
import com.ipbroker.model.ThirdPartyService;

public class FilterProviderLinkedListBasedOnTime {

    public FilterProviderLinkedListBasedOnTime() {
        System.out.println("FilterProviderLinkedListBasedOnTime constructor called");
    }

    public void filter(Map<ThirdPartyService, ProviderMetricNode[]> providerMetricMap) {
        System.out.println("Starting filter operation...");
        for (ThirdPartyService service : providerMetricMap.keySet()) {
            ProviderMetricNode[] providerMetricNodes = providerMetricMap.get(service);
            ProviderMetricNode head = providerMetricNodes[0];
            long beforeTime = Instant.now().minusMillis(1000*60 * 10).toEpochMilli(); // current time - 10 min
            int totalPreviousResponseTime = 0;
            int totalPreviousCount = 0;
            int totalPreviousErrorCount = 0;
            
            while (head != null && head.createdAt < beforeTime) {
                totalPreviousResponseTime += head.responseTime;
                totalPreviousCount++;
                totalPreviousErrorCount += head.status == ProviderMetricsStatusEnum.ERROR ? 1 : 0;
                head = head.next;
            }                   
            System.out.println("Filtering total no of nodes: " + totalPreviousCount + " and service: " + service.serviceName);
            if (head != null) {
                head.unlinkOldNode();
                providerMetricNodes[0] = head;
            }
            service.update(totalPreviousResponseTime, totalPreviousErrorCount, totalPreviousCount);
        }
        System.out.println("Filter operation completed");
    }
}