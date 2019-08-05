package ir.navaco.core.gateway.repository;

import ir.navaco.core.gateway.entity.EurekaServiceStatusEntity;
import ir.navaco.core.gateway.enums.EurekaServiceStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EurekaServiceStatusRepository extends JpaRepository<EurekaServiceStatusEntity, Long> {
    public EurekaServiceStatusEntity findByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType);
}
