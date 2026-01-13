package cn.sliew.carp.module.cep.workflow.flowgram.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public enum WorkflowRuntimeType {
    ;

    private static final String ISO_8601_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?$";
    private static final Pattern ISO_8601_PATTERN = Pattern.compile(ISO_8601_REGEX);

    public static WorkflowVariableType getWorkflowType(Object value) {
        if (Objects.isNull(value)) {
            return WorkflowVariableType.NULL;
        }

        if (value instanceof String) {
            String valueString = (String) value;
            if (ISO_8601_PATTERN.matcher(valueString).matches()) {
                DateTime parseDateTime = DateUtil.parse(valueString);
                if (Objects.nonNull(parseDateTime) && parseDateTime.getTime() > 0) {
                    return WorkflowVariableType.DATE_TIME;
                }
            }
            return WorkflowVariableType.STRING;
        }

        if (value instanceof Boolean) {
            return WorkflowVariableType.BOOLEAN;
        }

        if (value instanceof Integer) {
            return WorkflowVariableType.INTEGER;
        }

        if (value instanceof Number) {
            return WorkflowVariableType.NUMBER;
        }

        if (value instanceof List || value.getClass().isArray()) {
            return WorkflowVariableType.ARRAY;
        }

        if (value instanceof Map) {
            return WorkflowVariableType.MAP;
        }

        return WorkflowVariableType.OBJECT;
    }

    public static WorkflowVariableType getItemType(Object value) {
        if (Objects.isNull(value)) {
            return WorkflowVariableType.NULL;
        }

        if (value instanceof List) {
            List valueList = (List) value;
            if (CollectionUtils.isNotEmpty(valueList)) {
                return getWorkflowType(valueList.get(0));
            }
            return WorkflowVariableType.NULL;
        }

        if (value.getClass().isArray()) {
            if (Array.getLength(value) == 0) {
                return WorkflowVariableType.NULL;
            }
            return getWorkflowType(Array.get(value, 0));
        }

        return WorkflowVariableType.NULL;
    }

    public static boolean isTypeEqual(WorkflowVariableType typeA, WorkflowVariableType typeB) {
        if ((Objects.equals(typeA, WorkflowVariableType.NUMBER) && Objects.equals(typeB, WorkflowVariableType.INTEGER)) ||
                        Objects.equals(typeA, WorkflowVariableType.INTEGER) && Objects.equals(typeB, WorkflowVariableType.NUMBER)) {
            return true;
        }
        return Objects.equals(typeA, typeB);
    }

    public static WorkflowVariableType getArrayItemsType(List<WorkflowVariableType> types) {
        WorkflowVariableType expectedType = types.get(0);
        for (WorkflowVariableType type : types) {
            if (Objects.equals(type, expectedType)) {
                throw new RuntimeException("Array items type must be same, expect " + expectedType.getLabel() + ", but got " + type.getLabel());
            }
        }
        return expectedType;
    }


}
