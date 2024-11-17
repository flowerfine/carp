package cn.sliew.carp.module.scheduler.api.executor.handler.bean;

import cn.sliew.carp.module.scheduler.api.executor.JobContext;
import cn.sliew.carp.module.scheduler.api.executor.JobHandler;
import cn.sliew.carp.module.scheduler.api.executor.entity.job.JobExecutionResult;

public class BeanJobHandler implements JobHandler {

    private final JobHandler bean;

    public BeanJobHandler(JobHandler bean) {
        this.bean = bean;
    }

    @Override
    public JobExecutionResult init(JobContext context) {
        return bean.init(context);
    }

    @Override
    public JobExecutionResult execute(JobContext context) {
        return bean.execute(context);
    }

    @Override
    public void destroy(JobContext context) {
        bean.destroy(context);
    }
}
