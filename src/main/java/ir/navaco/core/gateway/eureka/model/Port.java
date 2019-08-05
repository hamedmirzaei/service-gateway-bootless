package ir.navaco.core.gateway.eureka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("port")
public class Port {

    @JsonProperty("$")
    private String value;

    @JsonProperty("@enabled")
    private String enabled;

    public Port() {
    }

    public Port(String value, String enabled) {
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
