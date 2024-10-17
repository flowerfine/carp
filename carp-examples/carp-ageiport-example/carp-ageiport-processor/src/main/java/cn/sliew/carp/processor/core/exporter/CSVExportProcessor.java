package cn.sliew.carp.processor.core.exporter;

import cn.sliew.carp.processor.core.model.UserData;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.carp.processor.core.model.UserView;
import com.alibaba.ageiport.common.utils.BeanUtils;
import com.alibaba.ageiport.processor.core.annotation.ExportSpecification;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.model.api.BizExportPage;
import com.alibaba.ageiport.processor.core.model.api.BizUser;
import com.alibaba.ageiport.processor.core.task.exporter.ExportProcessor;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfigImpl;

import java.util.ArrayList;
import java.util.List;


//1.实现ExportProcessor接口
@ExportSpecification(code = "CSVExportProcessor", name = "CSVExportProcessor")
public class CSVExportProcessor implements ExportProcessor<UserQuery, UserData, UserView> {

    //2.实现ExportProcessor接口的TotalCount方法
    @Override
    public Integer totalCount(BizUser bizUser, UserQuery query) throws BizException {
        return query.getTotalCount();
    }

    //3.实现ExportProcessor接口的queryData方法
    @Override
    public List<UserData> queryData(BizUser user, UserQuery query, BizExportPage bizExportPage) throws BizException {
        List<UserData> dataList = new ArrayList<>();

        Integer offset = bizExportPage.getOffset();
        Integer size = bizExportPage.getSize();
        for (int i = 1; i <= size; i++) {
            int index = offset + i;
            final UserData data = new UserData();
            data.setId(index);
            data.setName("name" + index);
            if (index % 3 == 0) {
                data.setGender("男");
            }
            if (index % 3 == 1) {
                data.setGender("女");
            }
            if (index % 3 == 2) {
                data.setGender("其他");
            }
            dataList.add(data);
        }
        return dataList;
    }

    //4.实现ExportProcessor接口的convert方法
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
        runtimeConfig.setFileType("csv");
        return runtimeConfig;
    }
}
