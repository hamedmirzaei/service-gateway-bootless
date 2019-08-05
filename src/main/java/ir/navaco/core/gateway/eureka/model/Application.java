package ir.navaco.core.gateway.eureka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@JsonRootName("application")
public class Application {

    private String name;
    private volatile boolean isDirty = false;
    private final Set<InstanceInfo> instances;
    private final AtomicReference<List<InstanceInfo>> shuffledInstances;
    private final Map<String, InstanceInfo> instancesMap;

    public Application() {
        instances = new LinkedHashSet<>();
        instancesMap = new ConcurrentHashMap<>();
        shuffledInstances = new AtomicReference<>();
    }

    public Application(String name) {
        this();
        this.name = name;
    }

    @JsonProperty("instance")
    public List<InstanceInfo> getInstances() {
        return Optional.ofNullable(shuffledInstances.get()).orElseGet(this::getInstancesAsIsFromEureka);
    }

    @JsonIgnore
    public List<InstanceInfo> getInstancesAsIsFromEureka() {
        synchronized (instances) {
            return new ArrayList<>(this.instances);
        }
    }

    public InstanceInfo getByInstanceId(String id) {
        return instancesMap.get(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Application [name=" + name + ", isDirty=" + isDirty
                + ", instances=" + instances + ", shuffledInstances="
                + shuffledInstances + ", instancesMap=" + instancesMap + "]";
    }

}
