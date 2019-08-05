package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.SubSystemCategoryEntity;
import ir.navaco.core.gateway.enums.SubSystemCategoryType;

import java.util.List;

public interface SubSystemCategoryService {

    SubSystemCategoryEntity getSubSystemCategoryBySubSystemCategoryType(SubSystemCategoryType subSystemCategoryType);

    List<SubSystemCategoryEntity> getSubSystemCategoryEntities();

}
