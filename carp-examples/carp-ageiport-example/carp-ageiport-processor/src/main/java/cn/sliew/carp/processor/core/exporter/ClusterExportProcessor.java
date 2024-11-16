package cn.sliew.carp.processor.core.exporter;

import cn.sliew.carp.processor.core.model.UserQuery;
import com.alibaba.ageiport.processor.core.annotation.ExportSpecification;
import com.alibaba.ageiport.processor.core.constants.ExecuteType;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.model.api.BizUser;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfigImpl;

@ExportSpecification(code = "ClusterExportProcessor", name = "ClusterExportProcessor", executeType = ExecuteType.CLUSTER)
public class ClusterExportProcessor extends BaseExportProcessor {

    @Override
    public BizExportTaskRuntimeConfig taskRuntimeConfig(BizUser user, UserQuery query) throws BizException {
        final BizExportTaskRuntimeConfigImpl runtimeConfig = new BizExportTaskRuntimeConfigImpl();
        runtimeConfig.setExecuteType("CLUSTER");
        return runtimeConfig;
    }
}
