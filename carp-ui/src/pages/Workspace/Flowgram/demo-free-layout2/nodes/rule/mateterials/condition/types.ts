import { IFlowConstantValue, IFlowRefValue, IFlowValue, IJsonSchema } from "@flowgram.ai/form-materials";

export interface ConditionProps {
    value?: Record<string, IFlowValue | undefined>;
    onChange: (value?: Record<string, IFlowValue | undefined>) => void;
    readonly?: boolean;
}

export interface RelationTreeProps {
    value?: RelationGroupData;
    onChange: (value?: RelationGroupData) => void;
    readonly?: boolean;
    hasError?: boolean;
    schema?: IJsonSchema;
    style?: React.CSSProperties;
}

export interface RelationGroupProps {
    data?: RelationTermData;
    onChange: (value?: Record<string, IFlowValue | undefined>) => void;
    readonly?: boolean;
}

export interface RelationTermProps {
    data?: RelationTermData;
    onChange: (value?: Record<string, IFlowValue | undefined>) => void;
    readonly?: boolean;
}

export type RelationGroupData = {
    ops?: string;
    children: Array<RelationGroupData | RelationTermData>;
}

export type RelationTermData = {
    key: IFlowConstantValue | undefined;
    op: IFlowConstantValue | undefined;
    value: IFlowValue | undefined;
}
