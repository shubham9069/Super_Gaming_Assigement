package com.ipbroker.model;

import java.time.Instant;

import com.ipbroker.enums.ProviderMetricsStatusEnum;

public class Response {
    public int responseTime; 
    public long createdAt;
    public String result;
    public ProviderMetricsStatusEnum status;

    public Response( int responseTime) {
        this.responseTime = responseTime;
        this.createdAt = Instant.now().minusMillis(responseTime).toEpochMilli();
    }
    public Response success(String result) {
        this.status = ProviderMetricsStatusEnum.SUCCESS;
        this.result = result;
        return this;
    }
    public Response error(String result) {
        this.status = ProviderMetricsStatusEnum.ERROR;
        this.result = result;
        return this;
    }

}