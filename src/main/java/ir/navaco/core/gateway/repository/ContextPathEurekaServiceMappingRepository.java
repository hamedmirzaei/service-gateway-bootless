package ir.navaco.core.gateway.repository;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import ir.navaco.core.gateway.entity.EurekaServiceStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContextPathEurekaServiceMappingRepository extends JpaRepository<ContextPathEurekaServiceMappingEntity, Long> {
    public List<ContextPathEurekaServiceMappingEntity> findByEurekaServiceStatusEntity(EurekaServiceStatusEntity eurekaServiceStatusEntity);
}
