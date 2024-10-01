package cn.sliew.carp.processor.core.importer;

import cn.sliew.carp.processor.core.TestHelper;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.carp.processor.core.model.UserView;
import com.alibaba.ageiport.common.collections.Lists;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.AgeiPort;
import com.alibaba.ageiport.processor.core.AgeiPortOptions;
import com.alibaba.ageiport.processor.core.spi.service.TaskExecuteParam;
import com.alibaba.ageiport.processor.core.spi.service.TaskExecuteResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
public class ClusterImportProcessorTest {

    @SneakyThrows
    @Test
    public void test() {
        AgeiPortOptions options = AgeiPortOptions.debug();
        AgeiPort ageiPort = AgeiPort.ageiPort(options);

        String taskCode = ClusterImportProcessor.class.getSimpleName();
        TestHelper testHelper = new TestHelper(ageiPort);
        String filePath = testHelper.file(taskCode + ".xlsx");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        String fileKey = UUID.randomUUID().toString();
        ageiPort.getFileStore().save(fileKey, inputStream, new HashMap<>());

        TaskExecuteParam request = new TaskExecuteParam();
        UserQuery query = new UserQuery();
        query.setTotalCount(100);
        request.setTaskSpecificationCode(taskCode);
        request.setBizUserId("userId");
        request.setBizQuery(JsonUtil.toJsonString(query));
        request.setInputFileKey(fileKey);
        TaskExecuteResult response = ageiPort.getTaskService().executeTask(request);

        Assertions.assertTrue(response.getSuccess());
        testHelper.assertWithoutFile(response.getMainTaskId());
    }

    @SneakyThrows
    @Test
    public void testHasCheckError() {
        AgeiPortOptions options = AgeiPortOptions.debug();
        AgeiPort ageiPort = AgeiPort.ageiPort(options);

        String taskCode = StandaloneImportProcessor.class.getSimpleName();
        TestHelper testHelper = new TestHelper(ageiPort);
        String filePath = testHelper.file(taskCode + ".xlsx");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        String fileKey = UUID.randomUUID().toString();
        ageiPort.getFileStore().save(fileKey, inputStream, new HashMap<>());

        TaskExecuteParam request = new TaskExecuteParam();
        UserQuery query = new UserQuery();


        UserView view = new UserView();
        view.setId(1);
        view.setName("name1");

        query.setCheckErrorData(Lists.newArrayList(view));
        query.setTotalCount(100);
        query.setCheckErrorDataWhenIdIn(Lists.newArrayList("1"));

        request.setTaskSpecificationCode(taskCode);
        request.setBizUserId("userId");
        request.setBizQuery(JsonUtil.toJsonString(query));
        request.setInputFileKey(fileKey);
        TaskExecuteResult response = ageiPort.getTaskService().executeTask(request);

        Assertions.assertTrue(response.getSuccess());
        testHelper.assertWithFile(response.getMainTaskId(), query.getErrorCount());
    }
}
