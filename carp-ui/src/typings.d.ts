declare module 'slash2';
declare module '*.css';
declare module '*.less';
declare module '*.scss';
declare module '*.sass';
declare module '*.svg';
declare module '*.png';
declare module '*.jpg';
declare module '*.jpeg';
declare module '*.gif';
declare module '*.bmp';
declare module '*.tiff';
declare module 'omit.js';
declare module 'numeral';
declare module 'mockjs';
declare module 'react-fittext';

declare const REACT_APP_ENV: 'test' | 'dev' | 'pre' | false;

export type QueryParam = {
    pageSize?: number;
    current?: number;
    sorter?: SoterField[];
};

export type SoterField = {
    field: string;
    order: string;
};

export type ResponseBody<T> = {
    success: boolean;
    data?: T;
    errorCode?: string;
    errorMessage?: string;
    showType?: string;
};

export type PageResponse<T> = {
    size: number;
    current: number;
    total: number;
    records: T[];
};

export type Page<T> = {
    pageSize: number;
    current: number;
    total: number;
    data: T[];
};

export type Dict = {
    label?: string;
    value?: string | number;
    remark?: string;
};

export type Props<T> = {
    data: T;
};

export type ModalProps<T> = {
    open?: boolean;
    data?: T;
    handleOk?: (isOpen: boolean, value?: any) => void;
    handleCancel?: () => void;
};

export type ModalFormProps<T> = {
    data: T | null | undefined;
    visible: boolean;
    onCancel?: () => void;
    onFinish?: (values: any) => void;
};

export type ModalFormParentProps<T> = {
    parent: T | null | undefined;
    data: T | null | undefined;
    visible: boolean;
    onCancel?: () => void;
    onFinish?: (values: any) => void;
};
