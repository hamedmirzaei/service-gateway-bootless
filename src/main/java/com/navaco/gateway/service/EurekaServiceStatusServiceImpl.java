package com.navaco.gateway.service;

import com.navaco.gateway.entity.EurekaServiceStatusEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;
import com.navaco.gateway.repository.EurekaServiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EurekaServiceStatusServiceImpl implements EurekaServiceStatusService {

    private EurekaServiceStatusRepository eurekaServiceStatusRepository;

    @Autowired
    public EurekaServiceStatusServiceImpl(EurekaServiceStatusRepository eurekaServiceStatusRepository) {
        this.eurekaServiceStatusRepository = eurekaServiceStatusRepository;
    }

    @Transactional
    @Override
    public EurekaServiceStatusEntity getEurekaServiceStatusByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType) {
        return eurekaServiceStatusRepository.findByEurekaServiceStatusType(eurekaServiceStatusType);
    }
}
