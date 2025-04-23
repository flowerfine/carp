package cn.sliew.carp.module.persistence.demo.service.impl;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.persistence.MemoryPersistenceService;
import cn.sliew.carp.module.persistence.MemoryResourceVisitor;
import cn.sliew.carp.module.persistence.api.PersistenceService;
import cn.sliew.carp.module.persistence.api.selectors.AllSelector;
import cn.sliew.carp.module.persistence.api.selectors.IdSelector;
import cn.sliew.carp.module.persistence.demo.repository.entity.DemoEntity;
import cn.sliew.carp.module.persistence.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService, InitializingBean {

    private final MemoryResourceVisitor<DemoEntity> memoryResourceVisitor;

    private PersistenceService<Long, DemoEntity> persistenceService;

    @Override
    public void afterPropertiesSet() throws Exception {
        persistenceService = new MemoryPersistenceService<>(memoryResourceVisitor);
    }

    @Override
    public PageResult<DemoEntity> page(PageParam pageParam) {
        return persistenceService.page(pageParam, new IdSelector(1L));
    }

    @Override
    public DemoEntity get(Long id) {
        return persistenceService.getOrThrow(id);
    }

    @Override
    public void add(DemoEntity param) {
        persistenceService.add(param);
    }

    @Override
    public void update(DemoEntity param) {
        persistenceService.update(param.getId(), entity -> param);
    }

    @Override
    public void delete(Long id) {
        persistenceService.delete(id);
    }

    @Override
    public void deleteBatch(Collection<Long> ids) {
        for (Long id : ids) {
            persistenceService.delete(id);
        }
    }
}
