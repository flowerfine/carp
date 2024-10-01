package cn.sliew.carp.processor.core.importer;

import cn.sliew.carp.processor.core.model.UserData;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.carp.processor.core.model.UserView;
import com.alibaba.ageiport.common.logger.Logger;
import com.alibaba.ageiport.common.logger.LoggerFactory;
import com.alibaba.ageiport.common.utils.CollectionUtils;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.annotation.ImportSpecification;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.model.api.BizUser;
import com.alibaba.ageiport.processor.core.task.importer.ImportProcessor;
import com.alibaba.ageiport.processor.core.task.importer.api.BizImportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.importer.api.BizImportTaskRuntimeConfigImpl;
import com.alibaba.ageiport.processor.core.task.importer.model.BizImportResult;
import com.alibaba.ageiport.processor.core.task.importer.model.BizImportResultImpl;

import java.util.ArrayList;
import java.util.List;


//1.实现ImportProcessor接口
@ImportSpecification(code = "StandaloneImportProcessor", name = "StandaloneImportProcessor")
public class StandaloneImportProcessor implements ImportProcessor<UserQuery, UserData, UserView> {

    Logger logger = LoggerFactory.getLogger(StandaloneImportProcessor.class);

    //2.实现ImportProcessor接口的convertAndCheck方法
    @Override
    public BizImportResult<UserView, UserData> convertAndCheck(BizUser user, UserQuery query, List<UserView> views) {
        BizImportResultImpl<UserView, UserData> result = new BizImportResultImpl<>();

        List<UserData> data = new ArrayList<>();
        for (UserView view : views) {
            UserData datum = new UserData();
            datum.setId(view.getId());
            datum.setName(view.getName());
            datum.setGender(view.getGender());
            if (CollectionUtils.isNotEmpty(query.getCheckErrorDataWhenIdIn())) {
                if (query.getCheckErrorDataWhenIdIn().contains(view.getId().toString())) {
                    result.setView(query.getCheckErrorData());
                }
            }

            data.add(datum);
        }

        result.setData(data);

        return result;
    }

    //3.实现ExportProcessor接口的write方法，此方法负责执行写入业务逻辑。
    @Override
    public BizImportResult<UserView, UserData> write(BizUser user, UserQuery query, List<UserData> data) {
        BizImportResultImpl<UserView, UserData> result = new BizImportResultImpl<>();
        logger.info(JsonUtil.toJsonString(data));
        result.setView(query.getWriteErrorData());
        if (query.isErrorWhenWriteData()) {
            throw new IllegalStateException("Error when write");
        }
        return result;
    }

    @Override
    public BizImportTaskRuntimeConfig taskRuntimeConfig(BizUser user, UserQuery query) throws BizException {
        BizImportTaskRuntimeConfigImpl runtimeConfig = new BizImportTaskRuntimeConfigImpl();
        runtimeConfig.setExecuteType("STANDALONE");
        return runtimeConfig;
    }
}
