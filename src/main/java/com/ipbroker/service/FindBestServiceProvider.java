package com.ipbroker.service;

import java.util.Map;

import com.ipbroker.model.ThirdPartyService;
import com.ipbroker.model.Threshold;
public class FindBestServiceProvider {
    

    public ThirdPartyService find(Map<ThirdPartyService, Threshold> threasholdMap) {
        ThirdPartyService bestServiceProvider = null;
        int bestResponseTime = Integer.MAX_VALUE;
        // int bestErrorRate = Integer.MAX_VALUE;

        for (ThirdPartyService service : threasholdMap.keySet()) {
            Threshold threshold = threasholdMap.get(service);
            // first check if the service is not in the threasholdMap
            if (threshold.LIMIT <= 0) {
                continue;
            }
            int responseTime = service.getAvgResponseTime();
             // int errorRate = service.getErrorRate(); 
             // first check if the response time is less than the best response time
            if (responseTime < bestResponseTime ) {
                bestResponseTime = responseTime;
                bestServiceProvider = service;
            }
            
        }


        return bestServiceProvider;
    }
}
