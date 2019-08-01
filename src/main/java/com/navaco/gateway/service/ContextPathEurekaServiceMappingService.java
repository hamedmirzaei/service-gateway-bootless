package com.navaco.gateway.service;

import com.navaco.gateway.entity.ContextPathEurekaServiceMappingEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;
import com.navaco.gateway.repository.ContextPathEurekaServiceMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContextPathEurekaServiceMappingService {

    private ContextPathEurekaServiceMappingRepository contextPathEurekaServiceMappingRepository;
    private EurekaServiceStatusService eurekaServiceStatusService;

    @Autowired
    public ContextPathEurekaServiceMappingService(ContextPathEurekaServiceMappingRepository contextPathEurekaServiceMappingRepository,
                                                  EurekaServiceStatusService eurekaServiceStatusService) {
        this.contextPathEurekaServiceMappingRepository = contextPathEurekaServiceMappingRepository;
        this.eurekaServiceStatusService = eurekaServiceStatusService;
    }

    @Transactional
    public ContextPathEurekaServiceMappingEntity getContextPathEurekaServiceMappingEntity(Long id) {
        return contextPathEurekaServiceMappingRepository.findById(id).orElse(new ContextPathEurekaServiceMappingEntity(-1L, "", ""));
    }

    @Transactional
    public List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntities() {
        return contextPathEurekaServiceMappingRepository.findAll();
    }

    @Transactional
    public List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntitiesByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType) {
        return contextPathEurekaServiceMappingRepository.findByEurekaServiceStatusEntity(
                eurekaServiceStatusService.getEurekaServiceStatusByEurekaServiceStatusType(eurekaServiceStatusType));
    }

    @Transactional
    public ContextPathEurekaServiceMappingEntity addContextPathEurekaServiceMappingEntity(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        return contextPathEurekaServiceMappingRepository.save(contextPathEurekaServiceMappingEntity);
    }
}
