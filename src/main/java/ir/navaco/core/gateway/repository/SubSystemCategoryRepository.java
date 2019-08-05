package ir.navaco.core.gateway.repository;

import ir.navaco.core.gateway.entity.SubSystemCategoryEntity;
import ir.navaco.core.gateway.enums.SubSystemCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSystemCategoryRepository extends JpaRepository<SubSystemCategoryEntity, Long> {
    public SubSystemCategoryEntity findBySubSystemCategoryType(SubSystemCategoryType subSystemCategoryType);
}
