package ir.navaco.core.gateway.eureka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@JsonRootName("instance")
public class InstanceInfo {

    private static final String VERSION_UNKNOWN = "unknown";

    private static final Logger logger = LoggerFactory.getLogger(InstanceInfo.class);

    private String instanceId;
    @JsonProperty("app")
    private String appName;
    private String appGroupName;
    private String ipAddr;
    private Port port;
    private SecurePort securePort;
    private String homePageUrl;
    private String statusPageUrl;
    private String healthCheckUrl;
    private String secureHealthCheckUrl;
    private String vipAddress;
    private String secureVipAddress;
    private String statusPageRelativeUrl;
    private String statusPageExplicitUrl;
    private String healthCheckRelativeUrl;
    private String healthCheckSecureExplicitUrl;
    private String vipAddressUnresolved;
    private String secureVipAddressUnresolved;
    private String healthCheckExplicitUrl;
    private boolean isSecurePortEnabled = false;
    private boolean isUnsecurePortEnabled = true;
    private DataCenterInfo dataCenterInfo;
    private String hostName;
    private InstanceStatus status = InstanceStatus.UP;
    private InstanceStatus overriddenStatus = InstanceStatus.UNKNOWN;
    private boolean isInstanceInfoDirty = false;
    private LeaseInfo leaseInfo;
    @JsonProperty("isCoordinatingDiscoveryServer")
    private Boolean isCoordinatingDiscoveryServer = Boolean.FALSE;
    private Map<String, String> metadata;
    private Long lastUpdatedTimestamp;
    private Long lastDirtyTimestamp;
    private ActionType actionType;
    private String asgName;
    private String version = VERSION_UNKNOWN;
    private Integer countryId;

    private InstanceInfo() {
        this.metadata = new ConcurrentHashMap<>();
        this.lastUpdatedTimestamp = System.currentTimeMillis();
        this.lastDirtyTimestamp = lastUpdatedTimestamp;
    }

    public enum InstanceStatus {
        UP, // Ready to receive traffic
        DOWN, // Do not send traffic- healthcheck callback failed
        STARTING, // Just about starting- initializations to be done - do not
        // send traffic
        OUT_OF_SERVICE, // Intentionally shutdown for traffic
        UNKNOWN;

        public static InstanceStatus toEnum(String s) {
            if (s != null) {
                try {
                    return InstanceStatus.valueOf(s.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // ignore and fall through to unknown
                    logger.debug("illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}", s, UNKNOWN);
                }
            }
            return UNKNOWN;
        }
    }

    public enum PortType {
        SECURE, UNSECURE
    }

    public enum ActionType {
        ADDED, // Added in the discovery server
        MODIFIED, // Changed in the discovery server
        DELETED
        // Deleted from the discovery server
    }

    @JsonIgnoreType
    public static final class Builder {
        private static final String COLON = ":";
        private static final String HTTPS_PROTOCOL = "https://";
        private static final String HTTP_PROTOCOL = "http://";
        private InstanceInfo result;

        private String namespace;

        private Builder(InstanceInfo result) {
            this.result = result;
        }

        public static Builder newBuilder() {
            return new Builder(new InstanceInfo());
        }

        public Builder setInstanceId(String instanceId) {
            result.instanceId = instanceId;
            return this;
        }

        /**
         * Set the application name of the instance.This is mostly used in
         * querying of instances.
         *
         * @param appName the application name.
         * @return the instance info builder.
         */
        public Builder setAppName(String appName) {
            result.appName = appName.toUpperCase(Locale.ROOT);
            return this;
        }

        public Builder setAppNameForDeser(String appName) {
            result.appName = appName;
            return this;
        }


        public Builder setAppGroupName(String appGroupName) {
            if (appGroupName != null) {
                result.appGroupName = appGroupName.toUpperCase(Locale.ROOT);
            } else {
                result.appGroupName = null;
            }
            return this;
        }

        public Builder setAppGroupNameForDeser(String appGroupName) {
            result.appGroupName = appGroupName;
            return this;
        }

        /**
         * Sets the fully qualified hostname of this running instance.This is
         * mostly used in constructing the {@link java.net.URL} for communicating with
         * the instance.
         *
         * @param hostName the host name of the instance.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder setHostName(String hostName) {
            if (hostName == null || hostName.isEmpty()) {
                logger.warn("Passed in hostname is blank, not setting it");
                return this;
            }

            String existingHostName = result.hostName;
            result.hostName = hostName;
            if ((existingHostName != null)
                    && !(hostName.equals(existingHostName))) {
                refreshStatusPageUrl().refreshHealthCheckUrl()
                        .refreshVIPAddress().refreshSecureVIPAddress();
            }
            return this;
        }

        /**
         * Sets the status of the instances.If the status is UP, that is when
         * the instance is ready to service requests.
         *
         * @param status the {@link InstanceStatus} of the instance.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder setStatus(InstanceStatus status) {
            result.status = status;
            return this;
        }

        /**
         * Sets the status overridden by some other external process.This is
         * mostly used in putting an instance out of service to block traffic to
         * it.
         *
         * @param status the overridden {@link InstanceStatus} of the instance.
         * @return @return the {@link InstanceInfo} builder.
         */
        public Builder setOverriddenStatus(InstanceStatus status) {
            result.overriddenStatus = status;
            return this;
        }

        /**
         * Sets the ip address of this running instance.
         *
         * @param ip the ip address of the instance.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder setIPAddr(String ip) {
            result.ipAddr = ip;
            return this;
        }

        /**
         * Sets the port number that is used to service requests.
         *
         * @param port the port number that is used to service requests.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder setPort(Port port) {
            result.port = port;
            return this;
        }

        /**
         * Sets the secure port that is used to service requests.
         *
         * @param port the secure port that is used to service requests.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder setSecurePort(SecurePort port) {
            result.securePort = port;
            return this;
        }

        /**
         * Enabled or disable secure/non-secure ports .
         *
         * @param type      Secure or Non-Secure.
         * @param isEnabled true if enabled.
         * @return the instance builder.
         */
        public Builder enablePort(PortType type, boolean isEnabled) {
            if (type == PortType.SECURE) {
                result.isSecurePortEnabled = isEnabled;
            } else {
                result.isUnsecurePortEnabled = isEnabled;
            }
            return this;
        }


        /**
         * Sets the absolute home page {@link java.net.URL} for this instance. The users
         * can provide the <code>homePageUrlPath</code> if the home page resides
         * in the same instance talking to discovery, else in the cases where
         * the instance is a proxy for some other server, it can provide the
         * full {@link java.net.URL}. If the full {@link java.net.URL} is provided it takes
         * precedence.
         * <p>
         * <p>
         * The full {@link java.net.URL} should follow the format
         * http://${netflix.appinfo.hostname}:7001/ where the value
         * ${netflix.appinfo.hostname} is replaced at runtime.
         * </p>
         *
         * @param relativeUrl the relative url path of the home page.
         * @param explicitUrl - The full {@link java.net.URL} for the home page
         * @return the instance builder.
         */
        public Builder setHomePageUrl(String relativeUrl, String explicitUrl) {
            String hostNameInterpolationExpression = "${" + namespace + "hostname}";
            if (explicitUrl != null) {
                result.homePageUrl = explicitUrl.replace(
                        hostNameInterpolationExpression, result.hostName);
            } else if (relativeUrl != null) {
                result.homePageUrl = HTTP_PROTOCOL + result.hostName + COLON
                        + result.port + relativeUrl;
            }
            return this;
        }

        /**
         * {@link #setHomePageUrl(String, String)} has complex logic that should not be invoked when
         * we deserialize {@link InstanceInfo} object, or home page URL is formatted already by the client.
         */
        public Builder setHomePageUrlForDeser(String homePageUrl) {
            result.homePageUrl = homePageUrl;
            return this;
        }

        /**
         * Sets the absolute status page {@link java.net.URL} for this instance. The
         * users can provide the <code>statusPageUrlPath</code> if the status
         * page resides in the same instance talking to discovery, else in the
         * cases where the instance is a proxy for some other server, it can
         * provide the full {@link java.net.URL}. If the full {@link java.net.URL} is provided it
         * takes precedence.
         * <p>
         * <p>
         * The full {@link java.net.URL} should follow the format
         * http://${netflix.appinfo.hostname}:7001/Status where the value
         * ${netflix.appinfo.hostname} is replaced at runtime.
         * </p>
         *
         * @param relativeUrl - The {@link java.net.URL} path for status page for this instance
         * @param explicitUrl - The full {@link java.net.URL} for the status page
         * @return - Builder instance
         */
        public Builder setStatusPageUrl(String relativeUrl, String explicitUrl) {
            String hostNameInterpolationExpression = "${" + namespace + "hostname}";
            result.statusPageRelativeUrl = relativeUrl;
            result.statusPageExplicitUrl = explicitUrl;
            if (explicitUrl != null) {
                result.statusPageUrl = explicitUrl.replace(
                        hostNameInterpolationExpression, result.hostName);
            } else if (relativeUrl != null) {
                result.statusPageUrl = HTTP_PROTOCOL + result.hostName + COLON
                        + result.port + relativeUrl;
            }
            return this;
        }

        /**
         * {@link #setStatusPageUrl(String, String)} has complex logic that should not be invoked when
         * we deserialize {@link InstanceInfo} object, or status page URL is formatted already by the client.
         */
        public Builder setStatusPageUrlForDeser(String statusPageUrl) {
            result.statusPageUrl = statusPageUrl;
            return this;
        }

        /**
         * Sets the absolute health check {@link java.net.URL} for this instance for both
         * secure and non-secure communication The users can provide the
         * <code>healthCheckUrlPath</code> if the healthcheck page resides in
         * the same instance talking to discovery, else in the cases where the
         * instance is a proxy for some other server, it can provide the full
         * {@link java.net.URL}. If the full {@link java.net.URL} is provided it takes precedence.
         * <p>
         * <p>
         * The full {@link java.net.URL} should follow the format
         * http://${netflix.appinfo.hostname}:7001/healthcheck where the value
         * ${netflix.appinfo.hostname} is replaced at runtime.
         * </p>
         *
         * @param relativeUrl       - The {@link java.net.URL} path for healthcheck page for this
         *                          instance.
         * @param explicitUrl       - The full {@link java.net.URL} for the healthcheck page.
         * @param secureExplicitUrl the full secure explicit url of the healthcheck page.
         * @return the instance builder
         */
        public Builder setHealthCheckUrls(String relativeUrl,
                                          String explicitUrl, String secureExplicitUrl) {
            String hostNameInterpolationExpression = "${" + namespace + "hostname}";
            result.healthCheckRelativeUrl = relativeUrl;
            result.healthCheckExplicitUrl = explicitUrl;
            result.healthCheckSecureExplicitUrl = secureExplicitUrl;
            if (explicitUrl != null) {
                result.healthCheckUrl = explicitUrl.replace(
                        hostNameInterpolationExpression, result.hostName);
            } else if (result.isUnsecurePortEnabled) {
                result.healthCheckUrl = HTTP_PROTOCOL + result.hostName + COLON
                        + result.port + relativeUrl;
            }

            if (secureExplicitUrl != null) {
                result.secureHealthCheckUrl = secureExplicitUrl.replace(
                        hostNameInterpolationExpression, result.hostName);
            } else if (result.isSecurePortEnabled) {
                result.secureHealthCheckUrl = HTTPS_PROTOCOL + result.hostName
                        + COLON + result.securePort + relativeUrl;
            }
            return this;
        }

        /**
         * {@link #setHealthCheckUrls(String, String, String)} has complex logic that should not be invoked when
         * we deserialize {@link InstanceInfo} object, or health check URLs are formatted already by the client.
         */
        public Builder setHealthCheckUrlsForDeser(String healthCheckUrl, String secureHealthCheckUrl) {
            if (healthCheckUrl != null) {
                result.healthCheckUrl = healthCheckUrl;
            }
            if (secureHealthCheckUrl != null) {
                result.secureHealthCheckUrl = secureHealthCheckUrl;
            }
            return this;
        }

        /**
         * Sets the Virtual Internet Protocol address for this instance. The
         * address should follow the format <code><hostname:port></code> This
         * address needs to be resolved into a real address for communicating
         * with this instance.
         *
         * @param vipAddress - The Virtual Internet Protocol address of this instance.
         * @return the instance builder.
         */
        public Builder setVIPAddress(final String vipAddress) {
            result.vipAddressUnresolved = vipAddress;
            result.vipAddress = vipAddress;
            return this;
        }

        /**
         * Setter used during deserialization process, that does not do macro expansion on the provided value.
         */
        public Builder setVIPAddressDeser(String vipAddress) {
            result.vipAddress = vipAddress;
            return this;
        }

        /**
         * Sets the Secure Virtual Internet Protocol address for this instance.
         * The address should follow the format <hostname:port> This address
         * needs to be resolved into a real address for communicating with this
         * instance.
         *
         * @param secureVIPAddress the secure VIP address of the instance.
         * @return - Builder instance
         */
        public Builder setSecureVIPAddress(final String secureVIPAddress) {
            result.secureVipAddressUnresolved = secureVIPAddress;
            result.secureVipAddress = secureVIPAddress;
            return this;
        }

        /**
         * Setter used during deserialization process, that does not do macro expansion on the provided value.
         */
        public Builder setSecureVIPAddressDeser(String secureVIPAddress) {
            result.secureVipAddress = secureVIPAddress;
            return this;
        }

        /**
         * Sets the datacenter information.
         *
         * @param datacenter the datacenter information for where this instance is
         *                   running.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder setDataCenterInfo(DataCenterInfo datacenter) {
            result.dataCenterInfo = datacenter;
            return this;
        }

        /**
         * Set the instance lease information.
         *
         * @param info the lease information for this instance.
         */
        public Builder setLeaseInfo(LeaseInfo info) {
            result.leaseInfo = info;
            return this;
        }

        /**
         * Add arbitrary metadata to running instance.
         *
         * @param key The key of the metadata.
         * @param val The value of the metadata.
         * @return the {@link InstanceInfo} builder.
         */
        public Builder add(String key, String val) {
            result.metadata.put(key, val);
            return this;
        }

        /**
         * Replace the existing metadata map with a new one.
         *
         * @param mt the new metadata name.
         * @return instance info builder.
         */
        public Builder setMetadata(Map<String, String> mt) {
            result.metadata = mt;
            return this;
        }

        /**
         * Returns the encapsulated instance info even it it is not built fully.
         *
         * @return the existing information about the instance.
         */
        public InstanceInfo getRawInstance() {
            return result;
        }

        /**
         * Build the {@link InstanceInfo} object.
         *
         * @return the {@link InstanceInfo} that was built based on the
         * information supplied.
         */
        public InstanceInfo build() {
            if (!isInitialized()) {
                throw new IllegalStateException("name is required!");
            }
            return result;
        }

        public boolean isInitialized() {
            return (result.appName != null);
        }

        /**
         * Sets the AWS ASG name for this instance.
         *
         * @param asgName the asg name for this instance.
         * @return the instance info builder.
         */
        public Builder setASGName(String asgName) {
            result.asgName = asgName;
            return this;
        }

        private Builder refreshStatusPageUrl() {
            setStatusPageUrl(result.statusPageRelativeUrl,
                    result.statusPageExplicitUrl);
            return this;
        }

        private Builder refreshHealthCheckUrl() {
            setHealthCheckUrls(result.healthCheckRelativeUrl,
                    result.healthCheckExplicitUrl,
                    result.healthCheckSecureExplicitUrl);
            return this;
        }

        private Builder refreshSecureVIPAddress() {
            setSecureVIPAddress(result.secureVipAddressUnresolved);
            return this;
        }

        private Builder refreshVIPAddress() {
            setVIPAddress(result.vipAddressUnresolved);
            return this;
        }

        public Builder setIsCoordinatingDiscoveryServer(boolean isCoordinatingDiscoveryServer) {
            result.isCoordinatingDiscoveryServer = isCoordinatingDiscoveryServer;
            return this;
        }

        public Builder setLastUpdatedTimestamp(long lastUpdatedTimestamp) {
            result.lastUpdatedTimestamp = lastUpdatedTimestamp;
            return this;
        }

        public Builder setLastDirtyTimestamp(long lastDirtyTimestamp) {
            result.lastDirtyTimestamp = lastDirtyTimestamp;
            return this;
        }

        public Builder setActionType(ActionType actionType) {
            result.actionType = actionType;
            return this;
        }

        public Builder setNamespace(String namespace) {
            this.namespace = namespace.endsWith(".")
                    ? namespace
                    : namespace + ".";
            return this;
        }
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppGroupName() {
        return appGroupName;
    }

    public void setAppGroupName(String appGroupName) {
        this.appGroupName = appGroupName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public SecurePort getSecurePort() {
        return securePort;
    }

    public void setSecurePort(SecurePort securePort) {
        this.securePort = securePort;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getSecureHealthCheckUrl() {
        return secureHealthCheckUrl;
    }

    public void setSecureHealthCheckUrl(String secureHealthCheckUrl) {
        this.secureHealthCheckUrl = secureHealthCheckUrl;
    }

    public String getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(String vipAddress) {
        this.vipAddress = vipAddress;
    }

    public String getSecureVipAddress() {
        return secureVipAddress;
    }

    public void setSecureVipAddress(String secureVipAddress) {
        this.secureVipAddress = secureVipAddress;
    }

    public String getStatusPageRelativeUrl() {
        return statusPageRelativeUrl;
    }

    public void setStatusPageRelativeUrl(String statusPageRelativeUrl) {
        this.statusPageRelativeUrl = statusPageRelativeUrl;
    }

    public String getStatusPageExplicitUrl() {
        return statusPageExplicitUrl;
    }

    public void setStatusPageExplicitUrl(String statusPageExplicitUrl) {
        this.statusPageExplicitUrl = statusPageExplicitUrl;
    }

    public String getHealthCheckRelativeUrl() {
        return healthCheckRelativeUrl;
    }

    public void setHealthCheckRelativeUrl(String healthCheckRelativeUrl) {
        this.healthCheckRelativeUrl = healthCheckRelativeUrl;
    }

    public String getHealthCheckSecureExplicitUrl() {
        return healthCheckSecureExplicitUrl;
    }

    public void setHealthCheckSecureExplicitUrl(String healthCheckSecureExplicitUrl) {
        this.healthCheckSecureExplicitUrl = healthCheckSecureExplicitUrl;
    }

    public String getVipAddressUnresolved() {
        return vipAddressUnresolved;
    }

    public void setVipAddressUnresolved(String vipAddressUnresolved) {
        this.vipAddressUnresolved = vipAddressUnresolved;
    }

    public String getSecureVipAddressUnresolved() {
        return secureVipAddressUnresolved;
    }

    public void setSecureVipAddressUnresolved(String secureVipAddressUnresolved) {
        this.secureVipAddressUnresolved = secureVipAddressUnresolved;
    }

    public String getHealthCheckExplicitUrl() {
        return healthCheckExplicitUrl;
    }

    public void setHealthCheckExplicitUrl(String healthCheckExplicitUrl) {
        this.healthCheckExplicitUrl = healthCheckExplicitUrl;
    }

    public boolean isSecurePortEnabled() {
        return isSecurePortEnabled;
    }

    public void setSecurePortEnabled(boolean securePortEnabled) {
        isSecurePortEnabled = securePortEnabled;
    }

    public boolean isUnsecurePortEnabled() {
        return isUnsecurePortEnabled;
    }

    public void setUnsecurePortEnabled(boolean unsecurePortEnabled) {
        isUnsecurePortEnabled = unsecurePortEnabled;
    }

    public DataCenterInfo getDataCenterInfo() {
        return dataCenterInfo;
    }

    public void setDataCenterInfo(DataCenterInfo dataCenterInfo) {
        this.dataCenterInfo = dataCenterInfo;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public void setStatus(InstanceStatus status) {
        this.status = status;
    }

    public InstanceStatus getOverriddenStatus() {
        return overriddenStatus;
    }

    public void setOverriddenStatus(InstanceStatus overriddenStatus) {
        this.overriddenStatus = overriddenStatus;
    }

    public boolean isInstanceInfoDirty() {
        return isInstanceInfoDirty;
    }

    public void setInstanceInfoDirty(boolean instanceInfoDirty) {
        isInstanceInfoDirty = instanceInfoDirty;
    }

    public LeaseInfo getLeaseInfo() {
        return leaseInfo;
    }

    public void setLeaseInfo(LeaseInfo leaseInfo) {
        this.leaseInfo = leaseInfo;
    }

    public Boolean getCoordinatingDiscoveryServer() {
        return isCoordinatingDiscoveryServer;
    }

    public void setCoordinatingDiscoveryServer(Boolean coordinatingDiscoveryServer) {
        isCoordinatingDiscoveryServer = coordinatingDiscoveryServer;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Long getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(Long lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public Long getLastDirtyTimestamp() {
        return lastDirtyTimestamp;
    }

    public void setLastDirtyTimestamp(Long lastDirtyTimestamp) {
        this.lastDirtyTimestamp = lastDirtyTimestamp;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getAsgName() {
        return asgName;
    }

    public void setAsgName(String asgName) {
        this.asgName = asgName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @JsonIgnore
    public boolean isPortEnabled(PortType type) {
        if (type == PortType.SECURE) {
            return isSecurePortEnabled;
        } else {
            return isUnsecurePortEnabled;
        }
    }

    @Override
    public String toString() {
        return "InstanceInfo [instanceId = " + this.instanceId + ", appName = " + this.appName +
                ", hostName = " + this.hostName + ", status = " + this.status +
                ", ipAddr = " + this.ipAddr + ", port = " + this.port + ", securePort = " + this.securePort +
                ", dataCenterInfo = " + this.dataCenterInfo;
    }
}
