package cn.sliew.carp.module.orca.spinnaker.api.operations;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class OperationsInput {

    /**
     * The operations collection.
     */
    private Collection<? extends Map<String, Map>> operations;

    /**
     * The {@link StageExecution} that runs this operation.
     */
    private StageExecution stageExecution;

    /**
     * The context key is passed to {@link OperationsContext#contextKey()} and used to identify the
     * corresponding {@link OperationsContext#contextValue()}.
     */
    @Nullable
    private String contextKey;

    public static OperationsInput of(
            Collection<? extends Map<String, Map>> operations,
            StageExecution stageExecution) {
        return builder()
                .operations(operations)
                .stageExecution(stageExecution)
                .build();
    }

}
