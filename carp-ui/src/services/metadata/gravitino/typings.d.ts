// @ts-ignore
/* eslint-disable */

declare namespace MetadataGravitinoAPI {
  type Metalake = {
    name: string;
    properties: Record<string, any>;
    remark?: string;
    creator?: string;
    createTime?: Date;
    editor?: string;
    updateTime?: Date;
  };

  type Catalog = {
    name: string;
    type: string;
    provider: string;
    properties: Record<string, any>;
    remark?: string;
    creator?: string;
    createTime?: Date;
    editor?: string;
    updateTime?: Date;
  };

  type Schema = {
    name: string;
    properties: Record<string, any>;
    remark?: string;
    creator?: string;
    createTime?: Date;
    editor?: string;
    updateTime?: Date;
  };

  type Table = {
    name: string;
    columns: Array<Column>;
    properties: Record<string, any>;
    comment?: string;
    creator?: string;
    createTime?: Date;
    editor?: string;
    updateTime?: Date;
  };

  type Column = {
    name: string;
    dataType: string;
    comment?: string;
    nullable?: boolean;
    autoIncrement?: boolean;
  };
}
