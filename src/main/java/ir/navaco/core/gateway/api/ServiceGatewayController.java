package ir.navaco.core.gateway.api;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import ir.navaco.core.gateway.entity.EurekaServiceStatusEntity;
import ir.navaco.core.gateway.entity.SubSystemCategoryEntity;
import ir.navaco.core.gateway.service.CamelRouteSetupRefresherService;
import ir.navaco.core.gateway.service.ContextPathEurekaServiceMappingService;
import ir.navaco.core.gateway.service.EurekaServiceStatusService;
import ir.navaco.core.gateway.service.SubSystemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-gateway")
public class ServiceGatewayController {

    private ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService;
    private EurekaServiceStatusService eurekaServiceStatusService;
    private SubSystemCategoryService subSystemCategoryService;
    private CamelRouteSetupRefresherService camelRouteSetupRefresherService;

    @Autowired
    public ServiceGatewayController(ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService,
                                    EurekaServiceStatusService eurekaServiceStatusService,
                                    SubSystemCategoryService subSystemCategoryService,
                                    CamelRouteSetupRefresherService camelRouteSetupRefresherService) {
        this.contextPathEurekaServiceMappingService = contextPathEurekaServiceMappingService;
        this.eurekaServiceStatusService = eurekaServiceStatusService;
        this.subSystemCategoryService = subSystemCategoryService;
        this.camelRouteSetupRefresherService = camelRouteSetupRefresherService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Server is UP";
    }

    @GetMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContextPathEurekaServiceMappingEntity> getAllServices() {
        return contextPathEurekaServiceMappingService.getAllContextPathEurekaServiceMappingEntities();
    }

    @GetMapping(value = "/services/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContextPathEurekaServiceMappingEntity getService(@PathVariable("id") Long id) {
        return contextPathEurekaServiceMappingService.getContextPathEurekaServiceMappingEntity(id);
    }

    @PostMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ContextPathEurekaServiceMappingEntity addService(@RequestBody ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        ContextPathEurekaServiceMappingEntity ce = contextPathEurekaServiceMappingService.addContextPathEurekaServiceMappingEntity(contextPathEurekaServiceMappingEntity);
        // if the service is active and published to the world then refresh the camel routes
        camelRouteSetupRefresherService.addService(ce);
        return ce;
    }

    @PutMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ContextPathEurekaServiceMappingEntity updateService(@RequestBody ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        ContextPathEurekaServiceMappingEntity ce = contextPathEurekaServiceMappingService.addContextPathEurekaServiceMappingEntity(contextPathEurekaServiceMappingEntity);
        // if the service changed then refresh the camel routes
        camelRouteSetupRefresherService.updateService(ce);
        return ce;
    }

    @DeleteMapping(value = "/services/{id}")
    public void deleteService(@PathVariable("id") Long id) {
        camelRouteSetupRefresherService.deleteService(contextPathEurekaServiceMappingService.getContextPathEurekaServiceMappingEntity(id));
        contextPathEurekaServiceMappingService.deleteContextPathEurekaServiceMappingEntity(id);
    }

    @GetMapping(value = "/statuses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EurekaServiceStatusEntity> getAllStatuses() {
        return eurekaServiceStatusService.getEurekaServiceStatusEntities();
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubSystemCategoryEntity> getAllCategories() {
        return subSystemCategoryService.getSubSystemCategoryEntities();
    }

}
