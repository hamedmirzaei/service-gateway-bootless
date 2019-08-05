package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.EurekaServiceStatusEntity;
import ir.navaco.core.gateway.enums.EurekaServiceStatusType;
import ir.navaco.core.gateway.repository.EurekaServiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    @Override
    public List<EurekaServiceStatusEntity> getEurekaServiceStatusEntities() {
        return eurekaServiceStatusRepository.findAll();
    }
}
