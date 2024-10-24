package cn.sliew.carp.processor.core.exporter;

import cn.sliew.carp.processor.core.model.UserData;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.carp.processor.core.model.UserView;
import com.alibaba.ageiport.common.collections.Lists;
import com.alibaba.ageiport.common.utils.BeanUtils;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.annotation.ExportSpecification;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.file.excel.ExcelConstants;
import com.alibaba.ageiport.processor.core.model.api.BizDataGroup;
import com.alibaba.ageiport.processor.core.model.api.BizDataItem;
import com.alibaba.ageiport.processor.core.model.api.BizExportPage;
import com.alibaba.ageiport.processor.core.model.api.BizUser;
import com.alibaba.ageiport.processor.core.model.api.impl.BizDataGroupImpl;
import com.alibaba.ageiport.processor.core.task.exporter.ExportProcessor;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfigImpl;

import java.util.*;

@ExportSpecification(code = "UdfSliceStrategyExportProcessor", name = "UdfSliceStrategyExportProcessor", sliceStrategy = "UdfExportSliceStrategy")
public class UdfSliceStrategyExportProcessor extends BaseExportProcessor {

    @Override
    public List<UserData> queryData(BizUser user, UserQuery query, BizExportPage bizExportPage) throws BizException {
        List<UserData> data = new ArrayList<>();
        String sliceKey = query.getSliceKey();
        if (Objects.equals(sliceKey, "男")) {
            data = doQueryData(user, query, bizExportPage, 0, "男");
        } else if (Objects.equals(sliceKey, "女")) {
            data = doQueryData(user, query, bizExportPage, 1, "女");
        } else if (Objects.equals(sliceKey, "其他")) {
            data = doQueryData(user, query, bizExportPage, 2, "其他");
        }
        return data;
    }

    private List<UserData> doQueryData(BizUser user, UserQuery query, BizExportPage bizExportPage, Integer groupIndex, String gender) {
        List<UserData> dataList = new ArrayList<>();
        Integer offset = bizExportPage.getOffset();
        Integer size = bizExportPage.getSize();
        for (int i = 1; i <= size; i++) {
            int index = offset + i;
            final UserData data = new UserData();
            data.setId(index);
            data.setName("name" + index);
            data.setGender(gender);
            data.setGroupIndex(groupIndex);
            data.setGroupName(String.format("%s性别问卷", gender));
            data.setOtherQuestion1(String.format("%s性别问题回答1", gender));
            data.setOtherQuestion2(String.format("%s性别问题回答2", gender));
            data.setOtherQuestion2("其他性别问题回答2");
            data.setOtherQuestion2("其他性别问题回答2");
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public BizDataGroup<UserView> group(BizUser user, UserQuery query, List<UserView> views) {
        BizDataGroupImpl<UserView> group = new BizDataGroupImpl<>();

        UserView view = views.stream().distinct().findFirst().get();
        BizDataGroupImpl.Data<UserView> data = new BizDataGroupImpl.Data<>();
        List<BizDataItem<UserView>> items = new ArrayList<>();
        data.setItems(items);
        Map<String, String> meta = new HashMap<>();
        meta.put(ExcelConstants.sheetNoKey, view.getGroupIndex().toString());
        meta.put(ExcelConstants.sheetNameKey, view.getGroupName());
        data.setMeta(meta);
        data.setCode(JsonUtil.toJsonString(meta));
        group.setData(Lists.newArrayList(data));

        for (UserView v : views) {
            BizDataGroupImpl.Item<UserView> item = new BizDataGroupImpl.Item<>();
            item.setData(v);
            items.add(item);
        }
        return group;
    }
}
