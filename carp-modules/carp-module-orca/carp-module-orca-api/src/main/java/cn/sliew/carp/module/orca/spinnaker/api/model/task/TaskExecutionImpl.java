package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import de.huxhorn.sulky.ulid.ULID;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A "task" is a component piece of a stage
 */
@Data
public class TaskExecutionImpl implements TaskExecution {

    private static final ULID ID_GENERATOR = new ULID();

    private Long id;
    private String uuid = ID_GENERATOR.nextULID();
    private String name;
    private String implementingClass;
    private Instant startTime;
    private Instant endTime;
    private ExecutionStatus status = ExecutionStatus.NOT_STARTED;
    private boolean stageStart;
    private boolean stageEnd;
    private boolean loopStart;
    private boolean loopEnd;
    private Map<String, Object> taskExceptionDetails = new HashMap<>();
}
