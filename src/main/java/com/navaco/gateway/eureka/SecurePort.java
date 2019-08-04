package com.navaco.gateway.eureka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("securePort")
public class SecurePort {

    @JsonProperty("$")
    private String value;

    @JsonProperty("@enabled")
    private String enabled;

    public SecurePort() {
    }

    public SecurePort(String value, String enabled) {
        this.value = value;
        this.enabled = enabled;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Port{" +
                "value='" + value + '\'' +
                ", enabled=" + enabled +
                '}';
    }

}
