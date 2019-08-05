package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.SubSystemCategoryEntity;
import ir.navaco.core.gateway.enums.SubSystemCategoryType;
import ir.navaco.core.gateway.repository.SubSystemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubSystemCategoryServiceImpl implements SubSystemCategoryService {

    private SubSystemCategoryRepository subSystemCategoryRepository;

    @Autowired
    public SubSystemCategoryServiceImpl(SubSystemCategoryRepository subSystemCategoryRepository) {
        this.subSystemCategoryRepository = subSystemCategoryRepository;
    }

    @Transactional
    @Override
    public SubSystemCategoryEntity getSubSystemCategoryBySubSystemCategoryType(SubSystemCategoryType subSystemCategoryType) {
        return subSystemCategoryRepository.findBySubSystemCategoryType(subSystemCategoryType);
    }

    @Override
    public List<SubSystemCategoryEntity> getSubSystemCategoryEntities() {
        return subSystemCategoryRepository.findAll();
    }
}
