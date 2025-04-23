package cn.sliew.carp.module.persistence.demo.repository;

import cn.sliew.carp.module.persistence.MemoryPersistenceService;
import cn.sliew.carp.module.persistence.MemoryResourceVisitor;
import cn.sliew.carp.module.persistence.demo.repository.entity.DemoEntity;

public class DemoRepository extends MemoryPersistenceService<DemoEntity> {

    public DemoRepository(MemoryResourceVisitor resourceVisitor) {
        super(resourceVisitor);
    }
}
