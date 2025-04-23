package cn.sliew.carp.module.persistence.service.sql;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.persistence.api.PersistenceListener;
import cn.sliew.carp.module.persistence.api.PersistenceService;
import cn.sliew.carp.module.persistence.api.selectors.Selector;
import cn.sliew.carp.module.persistence.service.ResourceStorageService;
import cn.sliew.carp.module.persistence.service.sql.entity.ResourceStorage;

import java.util.Optional;
import java.util.function.Function;

public class SqlPersistenceService implements ResourceStorageService {

    @Override
    public PageResult<ResourceStorage> page(ResourceStorage resourceStorage) {
        return null;
    }
}
