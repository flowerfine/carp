package cn.sliew.carp.module.scheduler.api.executor;

import cn.sliew.carp.framework.common.dict.schedule.ScheduleExecuteType;
import cn.sliew.milky.registry.Registry;

public interface JobHandlerFactoryRegistry extends Registry<JobHandlerFactory, Void> {

    JobHandlerFactory get(ScheduleExecuteType jobType);

    void put(ScheduleExecuteType jobType, JobHandlerFactory jobHandlerFactory);
}
