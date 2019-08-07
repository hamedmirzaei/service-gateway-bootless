package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import org.apache.camel.CamelContext;

public interface CamelRouteSetupRefresherService {

    void setup(CamelContext camelContext);

    void refresh();

    void addService(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity);

    void updateService(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity);

    void deleteService(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity);
}
