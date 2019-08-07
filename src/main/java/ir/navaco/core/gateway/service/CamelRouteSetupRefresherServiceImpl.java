package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import ir.navaco.core.gateway.enums.EurekaServiceStatusType;
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
                    //camelContext.setShutdownStrategy(new DefaultShutdownStrategy());
                    camelContext.stopRoute(routeDefinition.getId());
                } catch (Exception e) {
                }
                try {
                    //camelContext.removeRouteDefinition(routeDefinition);
                } catch (Exception e) {
                }
            }
        }
        // adding the routes
        setup(camelContext);
    }

    @Override
    public void addService(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        try {
            //if it is published
            if (contextPathEurekaServiceMappingEntity.getEurekaServiceStatusEntity().getEurekaServiceStatusType() == EurekaServiceStatusType.PUBLISHED) {
                camelContext.addRoutes(new EurekaServiceRouteBuilder(contextPathEurekaServiceMappingEntity));
            } else {
                //do nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateService(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        RouteDefinition routeDefinition = camelContext.getRouteDefinition("get_" + contextPathEurekaServiceMappingEntity.getContextPath());
        if (routeDefinition == null) {//does not exist
            addService(contextPathEurekaServiceMappingEntity);
        } else {//does exist
            //if it is published
            if (contextPathEurekaServiceMappingEntity.getEurekaServiceStatusEntity().getEurekaServiceStatusType() == EurekaServiceStatusType.PUBLISHED) {
                startRoutes(contextPathEurekaServiceMappingEntity);
            } else {
                stopRoutes(contextPathEurekaServiceMappingEntity);
            }
        }

    }

    @Override
    public void deleteService(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        RouteDefinition routeDefinition = camelContext.getRouteDefinition("get_" + contextPathEurekaServiceMappingEntity.getContextPath());
        if (routeDefinition == null) {//does not exist
            //do nothing
        } else {
            stopRoutes(contextPathEurekaServiceMappingEntity);
        }
    }

    private void stopRoutes(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        try {
            camelContext.stopRoute("get_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.stopRoute("post_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.stopRoute("delete_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.stopRoute("put_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.stopRoute("patch_" + contextPathEurekaServiceMappingEntity.getContextPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startRoutes(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        try {
            camelContext.startRoute("get_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.startRoute("post_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.startRoute("delete_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.startRoute("put_" + contextPathEurekaServiceMappingEntity.getContextPath());
            camelContext.startRoute("patch_" + contextPathEurekaServiceMappingEntity.getContextPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
