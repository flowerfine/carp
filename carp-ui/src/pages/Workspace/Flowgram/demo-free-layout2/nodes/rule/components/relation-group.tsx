import React from 'react';
import { Button, Select } from '@douyinfe/semi-ui';
import { RelationItem } from './relation-item';

const { Option } = Select;
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
        <div className="vui-relation-group">
            <div className="relational">
                <Select className="relation-sign" value={relationValue} onChange={handleOpsChange}>
                    <Option value="and">且</Option>
                    <Option value="or">或</Option>
                </Select>
            </div>
            <div className="conditions">
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
                <div className="operators">
                    <Button className="add-term" onClick={handleAddTermClick}>加条件</Button>
                    <Button className="add-group" onClick={handleAddGroupClick}>加条件组</Button>
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