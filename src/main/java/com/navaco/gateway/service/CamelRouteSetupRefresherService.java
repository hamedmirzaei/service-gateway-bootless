package com.navaco.gateway.service;

import com.navaco.gateway.entity.ContextPathEurekaServiceMappingEntity;
import com.navaco.gateway.routebuilder.ServiceRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CamelRouteSetupRefresherService {

    private ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService;
    private CamelContext camelContext;

    @Autowired
    public CamelRouteSetupRefresherService(ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService) {
        this.contextPathEurekaServiceMappingService = contextPathEurekaServiceMappingService;
    }

    public void setup(CamelContext camelContext) {
        this.camelContext = camelContext;
        List<ContextPathEurekaServiceMappingEntity> contextServiceMappingEntities = contextPathEurekaServiceMappingService.getAllContextPathEurekaServiceMappingEntities();

        // for each single micro-service which expose a rest API
        for (ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity : contextServiceMappingEntities) {
            try {
                camelContext.addRoutes(new ServiceRouteBuilder(contextPathEurekaServiceMappingEntity));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh() {
        // removing the routes
        List<RouteDefinition> currentRoutes = new ArrayList<>(camelContext.getRouteDefinitions());
        for (RouteDefinition routeDefinition : currentRoutes) {
            // if the route belong to one of services
            if (routeDefinition.getInputs().get(0).getUri().contains(":services/")) {
                try {
                    camelContext.stopRoute(routeDefinition.getId());
                } catch (Exception e) {
                }
                try {
                    camelContext.removeRouteDefinition(routeDefinition);
                } catch (Exception e) {
                }
            }
        }
        // adding the routes
        setup(camelContext);
    }
}
