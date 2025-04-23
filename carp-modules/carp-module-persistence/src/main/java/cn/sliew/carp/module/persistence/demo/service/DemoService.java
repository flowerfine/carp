package cn.sliew.carp.module.persistence.demo.service;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.persistence.demo.repository.entity.DemoEntity;

import java.util.Collection;

public interface DemoService {

    PageResult<DemoEntity> page(PageParam pageParam);

    DemoEntity get(Long id);

    void add(DemoEntity param);

    void update(DemoEntity param);

    void delete(Long id);

    void deleteBatch(Collection<Long> ids);
}
