package com.navaco.gateway.service;

import com.navaco.gateway.entity.ContextPathEurekaServiceMappingEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;
import com.navaco.gateway.repository.ContextPathEurekaServiceMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContextPathEurekaServiceMappingServiceImpl implements ContextPathEurekaServiceMappingService {

    private ContextPathEurekaServiceMappingRepository contextPathEurekaServiceMappingRepository;
    private EurekaServiceStatusService eurekaServiceStatusService;

    @Autowired
    public ContextPathEurekaServiceMappingServiceImpl(ContextPathEurekaServiceMappingRepository contextPathEurekaServiceMappingRepository,
                                                      EurekaServiceStatusService eurekaServiceStatusService) {
        this.contextPathEurekaServiceMappingRepository = contextPathEurekaServiceMappingRepository;
        this.eurekaServiceStatusService = eurekaServiceStatusService;
    }

    @Transactional
    @Override
    public ContextPathEurekaServiceMappingEntity getContextPathEurekaServiceMappingEntity(Long id) {
        return contextPathEurekaServiceMappingRepository.findById(id).orElse(new ContextPathEurekaServiceMappingEntity(-1L, "", ""));
    }

    @Transactional
    @Override
    public List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntities() {
        return contextPathEurekaServiceMappingRepository.findAll();
    }

    @Transactional
    @Override
    public List<ContextPathEurekaServiceMappingEntity> getAllContextPathEurekaServiceMappingEntitiesByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType) {
        return contextPathEurekaServiceMappingRepository.findByEurekaServiceStatusEntity(
                eurekaServiceStatusService.getEurekaServiceStatusByEurekaServiceStatusType(eurekaServiceStatusType));
    }

    @Transactional
    @Override
    public ContextPathEurekaServiceMappingEntity addContextPathEurekaServiceMappingEntity(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        return contextPathEurekaServiceMappingRepository.save(contextPathEurekaServiceMappingEntity);
    }
}
