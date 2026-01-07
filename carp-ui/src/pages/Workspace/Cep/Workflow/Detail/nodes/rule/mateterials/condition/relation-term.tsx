import { Input, Select } from "@douyinfe/semi-ui";
import { RelationTermProps } from "./types";
import styles from './relation-tree.less';
import { InjectDynamicValueInput } from "@flowgram.ai/form-materials";

export const RelationTerm = ({ data, onChange, readonly }: RelationTermProps) => {
    const { key, op, value } = data;

    const setOnChange = (params: Record<string, any>) => {
        console.log("RelationTerm onChange", params);
        if (typeof onChange === 'function') {
            // 执行传入的 onChange 回调，入参都是 { key: value } 格式
            onChange(params);
        }
    };

    const handleKeyChange = (val: any) => {
        setOnChange({ key: val });
    };

    const handleOpsChange = (val: any) => {
        setOnChange({ op: val });
    };

    const handleValueChange = (val: any) => {
        setOnChange({ value: val });
    };

    return (
        <>
            <div className={styles.term}>
                <span className={styles.element}>
                    <InjectDynamicValueInput
                        style={{ width: 200, maxWidth: 200, minWidth: 200 }}
                        readonly={readonly}
                        value={key}
                        onChange={handleKeyChange}
                    />
                </span>
                <span className={styles.comparison}>
                    <Select
                        value={op}
                        placeholder={"请选择关系符"}
                        onChange={handleOpsChange}
                        style={{ width: 85, maxWidth: 85, minWidth: 85 }}
                        size="small"
                        disabled={readonly}
                        optionList={[
                            { label: '等于', value: '==' },
                            { label: '不等于', value: '!=' },
                            { label: '大于', value: '>' },
                            { label: '小于', value: '<' },
                        ]}
                    />
                </span>
                <span className={styles.value}>
                    <InjectDynamicValueInput
                        style={{ width: 200, maxWidth: 200, minWidth: 200 }}
                        readonly={readonly}
                        value={value}
                        onChange={handleValueChange}
                    />
                </span>
            </div>
        </>
    )
}
