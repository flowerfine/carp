import { Input, Select } from "@douyinfe/semi-ui";
import { RelationTermProps } from "./types";
import styles from './relation-tree.less';

export const RelationTerm = ({ data, onChange, readonly }: RelationTermProps) => {
    const { key, op, value } = data;

    const setOnChange = (params: Record<string, any>) => {
        console.log("RelationTerm onChange", params);
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
                    <Select
                        value={key}
                        placeholder={"请选择条件项"}
                        onChange={handleKeyChange}
                        style={{ width: 85, maxWidth: 85, minWidth: 85 }}
                        size="small"
                        disabled={readonly}
                        optionList={[
                            { label: 'Key1', value: 'Key1' },
                            { label: 'Key2', value: 'Key2' }
                        ]}
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
                    <Input
                        value={value}
                        placeholder="请输入条件值"
                        onChange={handleValueChange}
                        style={{ width: 85, maxWidth: 85, minWidth: 85 }}
                        size="small"
                        disabled={readonly}
                    />
                </span>
            </div>
        </>
    )
}
