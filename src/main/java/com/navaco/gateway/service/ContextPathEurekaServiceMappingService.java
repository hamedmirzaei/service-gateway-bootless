package com.navaco.gateway.service;

import com.navaco.gateway.entity.ContextPathEurekaServiceMappingEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;

import java.util.List;

public interface ContextPathEurekaServiceMappingService {

    ContextPathEurekaServiceMappingEntity getContextPathEurekaServiceMappingEntity(Long id);

    List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntities();

    List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntitiesByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType);

    ContextPathEurekaServiceMappingEntity addContextPathEurekaServiceMappingEntity(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity);
}
