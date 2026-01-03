import React from 'react';
import { message } from 'antd';
import { getIntl, getLocale } from '@umijs/max';
import { cloneDeep } from 'lodash';
import { nanoid } from 'nanoid';

import { RulesController } from './ruleController';
import { IComponentProps, IFilterValue, ROW_PERMISSION_RELATION } from './types';
import { IFlowValue, useObjectList } from '@flowgram.ai/form-materials';

interface INormalProps<T> {
    value?: IFilterValue<T>; // 组件的值
    disabled?: false; // 编辑状态
    maxLevel?: number; // 节点层级
    maxSize?: number;
    initValues: T; // 自定义组件的初始值
    notEmpty?: { data: boolean; message?: string }; // 是否保留最后一条数据
    component: (props: IComponentProps<T>) => React.ReactNode; // 自定义展示组件
    onChange?: (value: IFilterValue<T> | undefined) => void; // 改变数据的方法
}

interface IDisabledProps<T> {
    value?: IFilterValue<T>; // 组件的值
    disabled: true; // 查看状态
    component: (props: IComponentProps<T>) => React.ReactNode; // 自定义展示组件
}

function isDisabled<T>(props: IProps<T>): props is IDisabledProps<T> {
    return props.disabled === true;
}

export type IProps<T> = IDisabledProps<T> | INormalProps<T>;

const FilterRules = <T,>(props: IProps<T>) => {
    const intl = getIntl(getLocale())
    const { component, disabled = false, value } = props;
    const {
        maxLevel = 5,
        maxSize = 100,
        notEmpty = { data: true, message: intl.formatMessage({ id: 'filterRules.notEmpty' }) },
        initValues,
        onChange,
    } = (!isDisabled(props) && props) as INormalProps<T>;

    console.log('FilterRules props', props, disabled);

    const { list, updateKey, updateValue, remove, add } = useObjectList<IFlowValue | undefined>({
        value,
        onChange,
        sortIndexKey: 'extra.index',
    });

    // 查找当前操作的节点
    const findRelationNode = (
        parentData: IFilterValue<T>,
        targetKey: string,
        needCurrent?: boolean
    ): IFilterValue<T> | null | undefined => {
        const parentDataTemp = parentData;
        if (parentDataTemp.key === targetKey) return parentDataTemp;
        if (!parentDataTemp.children?.length) return null;
        for (let i = 0; i < parentDataTemp.children.length; i++) {
            const current = parentDataTemp.children[i];
            if (current.key === targetKey) return needCurrent ? current : parentDataTemp;
            const node: IFilterValue<T> | null | undefined = findRelationNode(
                current,
                targetKey,
                needCurrent
            );
            if (node) return node;
        }
    };

    const handleAddCondition = (keyObj: { key: string; isOut?: boolean }) => {
        const cloneData = cloneDeep(value);
        const appendNode = findRelationNode(cloneData as IFilterValue<T>, keyObj.key, keyObj.isOut);
        addCondition(appendNode, keyObj, initValues as T);
        onChange?.(cloneData);
    };

    // 增加新的数据
    // 判断是在当前节点下新增或者新生成一个条件节点
    const addCondition = (
        treeNode: any,
        keyObj: { key: string; isOut?: boolean },
        initRowValue: T
    ) => {
        const key = keyObj.key;
        if (keyObj.isOut)
            return treeNode.children.push(
                Object.assign(
                    {},
                    { rowValues: initRowValue },
                    { key: nanoid(), level: treeNode.level }
                )
            );
        const children = treeNode?.children;
        if (!children) {
            const newNode = {
                key: treeNode.key,
                level: treeNode.level + 1,
                type: ROW_PERMISSION_RELATION.AND,
                children: [
                    { rowValues: treeNode.rowValues, key: nanoid(), level: treeNode?.level + 1 },
                    { rowValues: initRowValue, key: nanoid(), level: treeNode?.level + 1 },
                ],
            };
            delete treeNode.rowValues;
            Object.assign(treeNode, newNode);
            return;
        }
        for (let i = 0; i < children.length; i += 1) {
            if (children[i].key !== key) continue;
            if (treeNode?.level <= maxLevel) {
                children[i] = {
                    key: children[i].key,
                    type: ROW_PERMISSION_RELATION.AND,
                    level: treeNode?.level + 1,
                    children: [
                        Object.assign({}, children[i], {
                            key: nanoid(),
                            level: treeNode?.level + 1,
                        }),
                        Object.assign({
                            key: nanoid(),
                            rowValues: initRowValue,
                            level: treeNode?.level + 1,
                        }),
                    ],
                };
            }
        }
    };

    const handleDeleteCondition = (key: string) => {
        const cloneData = cloneDeep(value);
        const deleteNode = findRelationNode(cloneData as IFilterValue<T>, key, false);
        if (notEmpty.data && !deleteNode?.children) return message.info(notEmpty.message);
        if (!notEmpty.data && !deleteNode?.children) {
            return onChange?.(undefined);
        }
        deleteCondition(deleteNode as IFilterValue<T>, key);
        onChange?.(cloneData);
    };

    // 删除节点
    // 删除当前节点下的一条数据或者是删除一个条件节点
    const deleteCondition = (parentData: IFilterValue<T>, key: string) => {
        let parentDataTemp = parentData;
        parentDataTemp.children = parentDataTemp?.children?.filter((item) => item.key !== key);
        if (parentDataTemp?.children?.length === 1) {
            const newChild = updateLevel(parentDataTemp.children[0]);
            const key = parentDataTemp.key;
            delete parentDataTemp.children;
            delete parentDataTemp.type;
            parentDataTemp = Object.assign(parentDataTemp, {
                ...newChild,
                key,
                level: newChild.level,
            });
        }
    };

    // 删除一个条件节点时，更新当前数据的层级
    const updateLevel = (node: IFilterValue<T>) => {
        let newChildren;
        if (node.children) newChildren = node.children.map((element) => updateLevel(element));
        const newNode: IFilterValue<T> = {
            ...node,
            children: newChildren,
            level: (node?.level as number) - 1,
        };
        return newNode;
    };

    // 更改条件节点的条件
    const handleChangeCondition = (key: string, type: ROW_PERMISSION_RELATION) => {
        const cloneData = cloneDeep(value);
        const changeNode = findRelationNode(
            cloneData as IFilterValue<T>,
            key,
            true
        ) as IFilterValue<T>;
        changeNode.type =
            type === ROW_PERMISSION_RELATION.AND
                ? ROW_PERMISSION_RELATION.OR
                : ROW_PERMISSION_RELATION.AND;
        onChange?.(cloneData);
    };

    // 改变节点的的数据
    const handleChangeRowValues = (key: string, values: T) => {
        const cloneData = cloneDeep(value);
        const changeNode = findRelationNode(
            cloneData as IFilterValue<T>,
            key,
            true
        ) as IFilterValue<T>;
        changeNode.rowValues = {
            ...(changeNode.rowValues ?? {}),
            ...values,
        };
        onChange?.(cloneData);
    };
    return (
        <RulesController<T>
            maxLevel={maxLevel}
            maxSize={maxSize}
            disabled={disabled}
            value={value}
            component={component}
            onAddCondition={handleAddCondition}
            onDeleteCondition={handleDeleteCondition}
            onChangeCondition={handleChangeCondition}
            onChangeRowValues={handleChangeRowValues}
        />
    );
};

export default FilterRules;
