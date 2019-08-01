package com.navaco.gateway.service;

import com.navaco.gateway.entity.EurekaServiceStatusEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;
import com.navaco.gateway.repository.EurekaServiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EurekaServiceStatusService {

    private EurekaServiceStatusRepository eurekaServiceStatusRepository;

    @Autowired
    public EurekaServiceStatusService(EurekaServiceStatusRepository eurekaServiceStatusRepository) {
        this.eurekaServiceStatusRepository = eurekaServiceStatusRepository;
    }

    @Transactional
    public EurekaServiceStatusEntity getEurekaServiceStatusByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType) {
        return eurekaServiceStatusRepository.findByEurekaServiceStatusType(eurekaServiceStatusType);
    }
}
