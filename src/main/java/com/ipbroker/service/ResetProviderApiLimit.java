package com.ipbroker.service;

import java.util.Map;

import com.ipbroker.model.ThirdPartyService;
import com.ipbroker.model.Threshold;

public class ResetProviderApiLimit {

    public ResetProviderApiLimit() {
        System.out.println("ResetProviderApiLimit constructor called");
    }
    public void reset(Map<ThirdPartyService, Threshold> threasholdMap) {
        for (ThirdPartyService service : threasholdMap.keySet()) {
            threasholdMap.get(service).reset();
            System.out.println("Resetting API limit for " + service.serviceName);
        }
    }
}
