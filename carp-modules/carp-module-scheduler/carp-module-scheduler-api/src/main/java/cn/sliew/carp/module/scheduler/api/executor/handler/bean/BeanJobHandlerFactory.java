package cn.sliew.carp.module.scheduler.api.executor.handler.bean;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.framework.common.dict.schedule.ScheduleExecuteType;
import cn.sliew.carp.module.scheduler.api.executor.AbstractJobHandlerFactory;
import cn.sliew.carp.module.scheduler.api.executor.JobHandler;
import cn.sliew.carp.module.scheduler.api.executor.JobHandlerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class BeanJobHandlerFactory extends AbstractJobHandlerFactory implements JobHandlerFactory {

    @Override
    public String getType() {
        return ScheduleExecuteType.BEAN.getValue();
    }

    @Override
    protected JobHandler create(String jobHandler) {
        Class<JobHandler> clazz = ClassUtil.loadClass(jobHandler);
        Map<String, JobHandler> beans = SpringUtil.getBeansOfType(clazz);
        if (CollectionUtils.isEmpty(beans)) {
            throw new IllegalStateException("unknown job handler: " + jobHandler);
        }
        if (beans.size() > 1) {
            throw new IllegalStateException("multiple beans of type: " + jobHandler);
        }
        return beans.values().stream().findFirst().get();
    }
}
