package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import ir.navaco.core.gateway.enums.EurekaServiceStatusType;

import java.util.List;

public interface ContextPathEurekaServiceMappingService {

    ContextPathEurekaServiceMappingEntity getContextPathEurekaServiceMappingEntity(Long id);

    List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntities();

    List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntitiesByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType);

    ContextPathEurekaServiceMappingEntity addContextPathEurekaServiceMappingEntity(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity);

}
