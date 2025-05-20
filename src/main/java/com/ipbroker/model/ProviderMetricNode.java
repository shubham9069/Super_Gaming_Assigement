package com.ipbroker.model;

import com.ipbroker.enums.ProviderMetricsStatusEnum;

public class ProviderMetricNode {
    public long createdAt; // api call make  
    public int responseTime;
    public String result;
    public ProviderMetricsStatusEnum status;
    public ProviderMetricNode next;
    public ProviderMetricNode prev;

    public ProviderMetricNode(long createdAt, int responseTime, String error , ProviderMetricsStatusEnum status) {
        this.createdAt = createdAt;
        this.responseTime = responseTime;
        this.result = result;
        this.status = status;
        this.next = null;
        this.prev = null;
    }
    public ProviderMetricNode linkNewNode(ProviderMetricNode newNode) {
        if (this.next == null) {
            this.next = newNode;
            newNode.prev = this;
        }
        return this.next;
    }
    public void unlinkOldNode() {
        if (this.prev != null) {
            ProviderMetricNode prevNode = this.prev;
            prevNode.next = null;
        }
        this.prev = null;
    }
    
    
    
}

