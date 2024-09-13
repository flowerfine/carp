package cn.sliew.carp.processor.core.exporter;

import cn.sliew.carp.processor.core.TestHelper;
import cn.sliew.carp.processor.core.model.UserQuery;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.AgeiPort;
import com.alibaba.ageiport.processor.core.AgeiPortOptions;
import com.alibaba.ageiport.processor.core.spi.service.TaskExecuteParam;
import com.alibaba.ageiport.processor.core.spi.service.TaskExecuteResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UdfSliceStrategyExportProcessorTest {

    @SneakyThrows
    @Test
    public void test() {
        //1.初始化AgeiPort实例
        AgeiPortOptions options = AgeiPortOptions.debug();
        AgeiPort ageiPort = AgeiPort.ageiPort(options);

        //2.构造查询参数TaskExecuteParam
        UserQuery query = new UserQuery();
        query.setTotalCount(2000);
        query.setDynamicHeaderCount(3);

        //3.调用本地方法executeTask，开始执行任务，并获取任务实例ID。
        TaskExecuteParam request = new TaskExecuteParam();
        request.setTaskSpecificationCode(UdfSliceStrategyExportProcessor.class.getSimpleName());
        request.setBizUserId("userId");
        request.setBizQuery(JsonUtil.toJsonString(query));
        TaskExecuteResult response = ageiPort.getTaskService().executeTask(request);
        Assertions.assertTrue(response.getSuccess());

        //4.使用内部封装的TaskHelp方法判断任务是否执行成功
        TestHelper testHelper = new TestHelper(ageiPort);
        testHelper.assertWithFile(response.getMainTaskId(), query.getTotalCount());
    }
}
