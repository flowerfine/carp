package cn.sliew.carp.module.persistence.demo.repository.entity;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import lombok.Data;

@Data
public class DemoEntity extends BaseAuditDO {

    private String name;
}
