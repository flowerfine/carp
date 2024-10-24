package cn.sliew.carp.processor.core.exporter;

import cn.sliew.carp.processor.core.model.UserData;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.carp.processor.core.model.UserView;
import com.alibaba.ageiport.common.utils.BeanUtils;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.model.api.BizExportPage;
import com.alibaba.ageiport.processor.core.model.api.BizUser;
import com.alibaba.ageiport.processor.core.task.exporter.ExportProcessor;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfigImpl;

import java.util.ArrayList;
import java.util.List;

public class BaseExportProcessor implements ExportProcessor<UserQuery, UserData, UserView> {

    @Override
    public Integer totalCount(BizUser bizUser, UserQuery query) throws BizException {
        return query.getTotalCount();
    }

    @Override
    public List<UserData> queryData(BizUser user, UserQuery query, BizExportPage bizExportPage) throws BizException {
        List<UserData> dataList = new ArrayList<>();

        Integer totalCount = query.getTotalCount();
        for (int i = 1; i <= totalCount; i++) {
            final UserData data = new UserData();
            data.setId(i);
            data.setName("name" + i);
            if (i % 3 == 0) {
                data.setGender("男");
            }
            if (i % 3 == 1) {
                data.setGender("女");
            }
            if (i % 3 == 2) {
                data.setGender("其他");
            }
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public List<UserView> convert(BizUser user, UserQuery query, List<UserData> data) throws BizException {
        List<UserView> dataList = new ArrayList<>();
        for (UserData datum : data) {
            UserView view = BeanUtils.cloneProp(datum, UserView.class);
            dataList.add(view);
        }
        return dataList;
    }

    public BizExportTaskRuntimeConfig taskRuntimeConfig(BizUser user, UserQuery query) throws BizException {
        final BizExportTaskRuntimeConfigImpl runtimeConfig = new BizExportTaskRuntimeConfigImpl();
        runtimeConfig.setExecuteType("STANDALONE");
        return runtimeConfig;
    }
}
