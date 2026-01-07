import { IFlowConstantRefValue, IFlowRefValue } from "@flowgram.ai/form-materials";
import { IFlowValue } from "@flowgram.ai/runtime-interface";
import { InternalNamePath } from "antd/es/form/interface";


export interface FilterRulesValueType {
  
  left?: IFlowRefValue;
  operator?: string;
  right?: IFlowConstantRefValue;
}


export enum ROW_PERMISSION_RELATION {
    AND = 1,
    OR = 2,
}

export interface IComponentProps<T> {
    rowKey: string; // 当前节点的唯一标识
    disabled: boolean; // 编辑/查看状态
    name: InternalNamePath; // 使用 Form.Item 时，中间的 NamePath
    rowValues: T; // 自定义钻的数据
    onChange: (key: string, values: T) => void; // 改变数据的方法
}

export interface IFilterValue<T> {
    key: string;
    level?: number; // 当前节点的层级，用于判断一些按钮的展示
    type?: number; // 当前节点的条件关系，1 | 2
    disabled?: boolean; // 当前节点禁用
    rowValues?: T; // Form 节点的相关的信息(子节点无条件节点时才有)
    children?: IFilterValue<T>[]; // 子节点的信息(子节点存在条件节点时才有)
}

export interface IFilterValueType {
  left?: IFlowConstantRefValue;
  operator?: string;
  right?: IFlowConstantRefValue;
}