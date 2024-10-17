package cn.sliew.carp.processor.core.importer;

import cn.sliew.carp.processor.core.model.UserData;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.carp.processor.core.model.UserView;
import com.alibaba.ageiport.common.logger.Logger;
import com.alibaba.ageiport.common.logger.LoggerFactory;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.annotation.ImportSpecification;
import com.alibaba.ageiport.processor.core.constants.ExecuteType;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.model.api.BizUser;
import com.alibaba.ageiport.processor.core.task.importer.ImportProcessor;
import com.alibaba.ageiport.processor.core.task.importer.api.BizImportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.importer.api.BizImportTaskRuntimeConfigImpl;
import com.alibaba.ageiport.processor.core.task.importer.model.BizImportResult;
import com.alibaba.ageiport.processor.core.task.importer.model.BizImportResultImpl;

import java.util.ArrayList;
import java.util.List;


@ImportSpecification(code = "ClusterImportProcessor", name = "ClusterImportProcessor", executeType = ExecuteType.CLUSTER)
public class ClusterImportProcessor implements ImportProcessor<UserQuery, UserData, UserView> {

    Logger logger = LoggerFactory.getLogger(ClusterImportProcessor.class);

    @Override
    public BizImportResult<UserView, UserData> convertAndCheck(BizUser user, UserQuery query, List<UserView> views) {
        BizImportResultImpl<UserView, UserData> result = new BizImportResultImpl<>();

        List<UserData> data = new ArrayList<>();
        for (UserView view : views) {
            UserData datum = new UserData();
            datum.setId(view.getId());
            datum.setName(view.getName());
            datum.setGender(view.getGender());
            data.add(datum);
        }

        result.setData(data);
        result.setView(query.getCheckErrorData());
        return result;
    }

    @Override
    public BizImportResult<UserView, UserData> write(BizUser user, UserQuery query, List<UserData> data) {
        BizImportResultImpl<UserView, UserData> result = new BizImportResultImpl<>();
        logger.info(JsonUtil.toJsonString(data));
        result.setView(query.getWriteErrorData());
        return result;
    }

    @Override
    public BizImportTaskRuntimeConfig taskRuntimeConfig(BizUser user, UserQuery query) throws BizException {
        BizImportTaskRuntimeConfigImpl runtimeConfig = new BizImportTaskRuntimeConfigImpl();
        runtimeConfig.setExecuteType("CLUSTER");
        return runtimeConfig;
    }
}
