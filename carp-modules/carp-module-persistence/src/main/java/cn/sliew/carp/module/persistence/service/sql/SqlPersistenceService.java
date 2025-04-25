package cn.sliew.carp.module.persistence.service.sql;

import cn.sliew.carp.module.persistence.service.ResourceStorageService;
import cn.sliew.carp.module.persistence.service.dto.ResourceStorageDTO;
import cn.sliew.carp.module.persistence.service.sql.entity.ResourceStorage;

import java.util.List;

public class SqlPersistenceService implements ResourceStorageService {


    @Override
    public List<ResourceStorageDTO> list(ResourceStorage resourceStorage) {
        return List.of();
    }
}
