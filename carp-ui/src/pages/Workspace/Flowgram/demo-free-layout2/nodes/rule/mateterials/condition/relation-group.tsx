import React from 'react';
import { Button, IconButton, Select, Tooltip } from '@douyinfe/semi-ui';
import { IconPlusCircle } from '@douyinfe/semi-icons';
import { RelationItem } from './relation-item';
import styles from './relation-tree.less';

export const posSeparator = '_';
export const defaultOpsValue = 'and';

export const RelationGroup = ({ data, pos, setElementTerm, onAddGroup, onAddTerm, onOpsChange, onDeleteTerm, onTermChange }) => {
    const { ops, children } = data;
    const relationValue = ops || defaultOpsValue;

    const getLastPos = () => {
        const arrPos = getArrPos(pos);
        const { children } = data;
        arrPos.push(children.length - 1)
        return arrPos.join(posSeparator);
    };
    const handleOpsChange = (value) => {
        if (typeof onOpsChange === 'function') {
            onOpsChange(pos, { ...data, ops: value });
        }
    };
    const handleAddTermClick = () => {
        const record = {};
        const pos = getLastPos();
        if (typeof onAddTerm === 'function') {
            onAddTerm(pos, record);
        }
    };
    const handleAddGroupClick = () => {
        const record = { ops: defaultOpsValue, children: [{}] };
        const pos = getLastPos();
        if (typeof onAddGroup === 'function') {
            onAddGroup(pos, record);
        }
    };


    return (
        <div className={styles.vuiRelationGroup}>
            <div className={styles.relational}>
                <Select
                    className={styles.relationSign}
                    value={relationValue}
                    size="small"
                    onChange={handleOpsChange}
                    optionList={[
                        { label: '且', value: 'and' },
                        { label: '或', value: 'or' }
                    ]}
                />
            </div>
            <div className={styles.conditions}>
                {children.map((record, i) => {
                    console.log('record', record)
                    const { children: list } = record;
                    const newPos = getNewPos(pos, i);

                    return list && list.length ? (
                        <RelationGroup
                            pos={newPos}
                            key={newPos}
                            data={record}
                            setElementTerm={setElementTerm}
                            onAddGroup={onAddGroup}
                            onAddTerm={onAddTerm}
                            onOpsChange={onOpsChange}
                            onDeleteTerm={onDeleteTerm}
                            onTermChange={onTermChange}
                        />
                    ) : (
                        <RelationItem
                            pos={newPos}
                            key={newPos}
                            data={record}
                            setElementTerm={setElementTerm}
                            onDeleteTerm={onDeleteTerm}
                            onTermChange={onTermChange}
                        />
                    );
                })}
                <div className={styles.operators}>
                    <Tooltip content={"加条件"}>
                        <IconButton
                            theme="borderless"
                            icon={<IconPlusCircle />}
                            size="small"
                            onClick={handleAddTermClick}
                        />
                    </Tooltip>

                    <Tooltip content={"加条件组"}>
                        <IconButton
                            theme="borderless"
                            icon={<IconPlusCircle color={"#fff"} />}
                            size="small"
                            onClick={handleAddGroupClick}
                        />
                    </Tooltip>
                </div>
            </div>
        </div>
    );
}

const getNewPos = (pos, i) => {
    // 如果当前项是整个 value (即组件的起始项)时，新位置即当前序号
    return pos ? `${pos}${posSeparator}${i}` : String(i);
};

export const getArrPos = (pos) => {
    return (pos && pos.split(posSeparator)) || [];
};
