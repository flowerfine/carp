package cn.sliew.carp.processor.core.exporter;

import cn.sliew.carp.processor.core.model.Data;
import cn.sliew.carp.processor.core.model.MultiSheetView;
import cn.sliew.carp.processor.core.model.Query;
import com.alibaba.ageiport.common.utils.BeanUtils;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.annotation.ExportSpecification;
import com.alibaba.ageiport.processor.core.exception.BizException;
import com.alibaba.ageiport.processor.core.file.excel.ExcelConstants;
import com.alibaba.ageiport.processor.core.model.api.*;
import com.alibaba.ageiport.processor.core.model.api.impl.BizDataGroupImpl;
import com.alibaba.ageiport.processor.core.task.exporter.ExportProcessor;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfig;
import com.alibaba.ageiport.processor.core.task.exporter.api.BizExportTaskRuntimeConfigImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//1.实现ExportProcessor接口
@ExportSpecification(code = "MultiSheetExportProcessor", name = "MultiSheetExportProcessor")
public class MultiSheetExportProcessor implements ExportProcessor<Query, Data, MultiSheetView> {

    //2.实现ExportProcessor接口的TotalCount方法
    @Override
    public Integer totalCount(BizUser bizUser, Query query) throws BizException {
        return query.getTotalCount();
    }

    //3.实现ExportProcessor接口的queryData方法
    @Override
    public List<Data> queryData(BizUser user, Query query, BizExportPage bizExportPage) throws BizException {
        List<Data> dataList = new ArrayList<>();

        Integer offset = bizExportPage.getOffset();
        Integer size = bizExportPage.getSize();
        for (int i = 1; i <= size; i++) {
            int index = offset + i;
            final Data data = new Data();
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
            data.setAge(new BigDecimal(i % 100));
            data.setManQuestion1("男性问题回答1");
            data.setManQuestion2("男性问题回答2");
            data.setWomenQuestion1("女性问题回答1");
            data.setWomenQuestion2("女性问题回答2");
            data.setOtherQuestion1("其他性别问题回答1");
            data.setOtherQuestion2("其他性别问题回答2");

            dataList.add(data);
        }
        return dataList;
    }

    //4.实现ExportProcessor接口的convert方法
    @Override
    public List<MultiSheetView> convert(BizUser user, Query query, List<Data> data) throws BizException {
        List<MultiSheetView> dataList = new ArrayList<>();
        for (Data datum : data) {
            MultiSheetView view = BeanUtils.cloneProp(datum, MultiSheetView.class);
            dataList.add(view);
        }
        return dataList;
    }

    @Override
    public BizDataGroup<MultiSheetView> group(BizUser user, Query query, List<MultiSheetView> views) {
        BizDataGroupImpl<MultiSheetView> group = new BizDataGroupImpl<>();

        BizDataGroupImpl.Data<MultiSheetView> dataMan = new BizDataGroupImpl.Data<>();
        List<BizDataItem<MultiSheetView>> itemsMain = new ArrayList<>();
        dataMan.setItems(itemsMain);
        Map<String, String> metaMan = new HashMap<>();
        metaMan.put(ExcelConstants.sheetNameKey, "男");
        metaMan.put(ExcelConstants.sheetNoKey, "0");
        dataMan.setMeta(metaMan);
        dataMan.setCode(JsonUtil.toJsonString(metaMan));

        BizDataGroupImpl.Data<MultiSheetView> dataWomen = new BizDataGroupImpl.Data<>();
        List<BizDataItem<MultiSheetView>> itemsWomen = new ArrayList<>();
        dataWomen.setItems(itemsWomen);
        Map<String, String> metaWomen = new HashMap<>();
        metaWomen.put(ExcelConstants.sheetNameKey, "女");
        metaWomen.put(ExcelConstants.sheetNoKey, "1");
        dataWomen.setMeta(metaWomen);
        dataWomen.setCode(JsonUtil.toJsonString(metaWomen));

        BizDataGroupImpl.Data<MultiSheetView> dataOther = new BizDataGroupImpl.Data<>();
        List<BizDataItem<MultiSheetView>> itemsOther = new ArrayList<>();
        dataOther.setItems(itemsOther);
        Map<String, String> metaOther = new HashMap<>();
        metaOther.put(ExcelConstants.sheetNameKey, "其他");
        metaOther.put(ExcelConstants.sheetNoKey, "2");
        dataOther.setMeta(metaOther);
        dataWomen.setCode(JsonUtil.toJsonString(metaOther));

        List<BizData<MultiSheetView>> dataList = new ArrayList<>();
        dataList.add(dataMan);
        dataList.add(dataWomen);
        dataList.add(dataOther);

        group.setData(dataList);
        for (MultiSheetView view : views) {
            BizDataGroupImpl.Item<MultiSheetView> item = new BizDataGroupImpl.Item<>();
            item.setData(view);
            if (view.getGender().equals("男")) {
                itemsMain.add(item);
            } else if (view.getGender().equals("女")) {
                itemsWomen.add(item);
            } else {
                itemsOther.add(item);
            }
        }

        return group;
    }

    @Override
    public BizExportTaskRuntimeConfig taskRuntimeConfig(BizUser user, Query query) throws BizException {
        final BizExportTaskRuntimeConfigImpl runtimeConfig = new BizExportTaskRuntimeConfigImpl();
        runtimeConfig.setExecuteType("STANDALONE");
        return runtimeConfig;
    }

}
