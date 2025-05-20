package com.ipbroker.enums;

public enum ProviderMetricsStatusEnum {
   SUCCESS("success"),
   ERROR("error");


   private final String status;

   ProviderMetricsStatusEnum(String status) {
    this.status = status;
   }

   public String getStatus() {
    return status;
   }
}
