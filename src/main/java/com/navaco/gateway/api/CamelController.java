package com.navaco.gateway.api;

import com.navaco.gateway.entity.ContextPathEurekaServiceMappingEntity;
import com.navaco.gateway.entity.EurekaServiceStatusEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;
import com.navaco.gateway.service.CamelRouteSetupRefresherService;
import com.navaco.gateway.service.ContextPathEurekaServiceMappingService;
import com.navaco.gateway.service.EurekaServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/camel")
public class CamelController {

    private ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService;
    private EurekaServiceStatusService eurekaServiceStatusService;
    private CamelRouteSetupRefresherService camelRouteSetupRefresherService;

    @Autowired
    public CamelController(ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService,
                           EurekaServiceStatusService eurekaServiceStatusService,
                           CamelRouteSetupRefresherService camelRouteSetupRefresherService) {
        this.contextPathEurekaServiceMappingService = contextPathEurekaServiceMappingService;
        this.eurekaServiceStatusService = eurekaServiceStatusService;
        this.camelRouteSetupRefresherService = camelRouteSetupRefresherService;
    }

    @GetMapping("/refresh")
    public ResponseEntity refreshRoutes() {
        camelRouteSetupRefresherService.refresh();
        return ResponseEntity.ok("CamelContext Refreshed Successfully");
    }

    @GetMapping("/test1")
    public ResponseEntity<List<ContextPathEurekaServiceMappingEntity>> test1() {
        return ResponseEntity.ok(contextPathEurekaServiceMappingService.getAllContextPathEurekaServiceMappingEntitiesByEurekaServiceStatusType(EurekaServiceStatusType.PUBLISHED));
    }

    @GetMapping("/test2")
    public ResponseEntity<EurekaServiceStatusEntity> test2() {
        return ResponseEntity.ok(eurekaServiceStatusService.getEurekaServiceStatusByEurekaServiceStatusType(EurekaServiceStatusType.PUBLISHED));
    }


    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("Server is UP");
    }
}
