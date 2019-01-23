package com.lpdm.msuser.model.admin;

import javax.validation.constraints.NotNull;

public class EurekaInstance {

    @NotNull
    private String appId;

    @NotNull
    private String instanceId;

    public EurekaInstance() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String toString() {
        return "EurekaInstance{" +
                "appId='" + appId + '\'' +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
