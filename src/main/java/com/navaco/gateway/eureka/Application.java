package com.navaco.gateway.eureka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The application class holds the list of instances for a particular
 * application.
 *
 *
 */
@JsonRootName("application")
public class Application {

    @Override
    public String toString() {
        return "Application [name=" + name + ", isDirty=" + isDirty
                + ", instances=" + instances + ", shuffledInstances="
                + shuffledInstances + ", instancesMap=" + instancesMap + "]";
    }

    private String name;

    private volatile boolean isDirty = false;

    private final Set<InstanceInfo> instances;

    private final AtomicReference<List<InstanceInfo>> shuffledInstances;

    private final Map<String, InstanceInfo> instancesMap;

    public Application() {
        instances = new LinkedHashSet<InstanceInfo>();
        instancesMap = new ConcurrentHashMap<String, InstanceInfo>();
        shuffledInstances = new AtomicReference<List<InstanceInfo>>();
    }

    public Application(String name) {
        this();
        this.name = name;
    }




    /**
     * Gets the list of instances associated with this particular application.
     * <p>
     * Note that the instances are always returned with random order after
     * shuffling to avoid traffic to the same instances during startup. The
     * shuffling always happens once after every fetch cycle as specified in
     * </p>
     *
     * @return the list of shuffled instances associated with this application.
     */
    @JsonProperty("instance")
    public List<InstanceInfo> getInstances() {
        return Optional.ofNullable(shuffledInstances.get()).orElseGet(this::getInstancesAsIsFromEureka);
    }

    /**
     * Gets the list of non-shuffled and non-filtered instances associated with this particular
     * application.
     *
     * @return list of non-shuffled and non-filtered instances associated with this particular
     *         application.
     */
    @JsonIgnore
    public List<InstanceInfo> getInstancesAsIsFromEureka() {
        synchronized (instances) {
           return new ArrayList<InstanceInfo>(this.instances);
        }
    }


    /**
     * Get the instance info that matches the given id.
     *
     * @param id
     *            the id for which the instance info needs to be returned.
     * @return the instance info object.
     */
    public InstanceInfo getByInstanceId(String id) {
        return instancesMap.get(id);
    }

    /**
     * Gets the name of the application.
     *
     * @return the name of the application.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the application.
     *
     * @param name
     *            the name of the application.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the number of instances in this application
     */
    public int size() {
        return instances.size();
    }

}
