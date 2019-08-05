package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import ir.navaco.core.gateway.routebuilder.EurekaServiceRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CamelRouteSetupRefresherServiceImpl implements CamelRouteSetupRefresherService {

    private ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService;
    private CamelContext camelContext;

    @Autowired
    public CamelRouteSetupRefresherServiceImpl(ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService) {
        this.contextPathEurekaServiceMappingService = contextPathEurekaServiceMappingService;
    }

    @Override
    public void setup(CamelContext camelContext) {
        this.camelContext = camelContext;
        List<ContextPathEurekaServiceMappingEntity> contextServiceMappingEntities = contextPathEurekaServiceMappingService.getAllContextPathEurekaServiceMappingEntities();

        // for each single micro-service which expose a rest API
        for (ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity : contextServiceMappingEntities) {
            try {
                camelContext.addRoutes(new EurekaServiceRouteBuilder(contextPathEurekaServiceMappingEntity));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
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
