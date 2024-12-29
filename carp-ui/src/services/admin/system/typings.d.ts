// @ts-ignore
/* eslint-disable */

import {QueryParam} from "@/typings";

declare namespace AdminSystemAPI {

  type SysDictDefinition = {
    code: string;
    name: string;
    provider: string;
    remark?: string;
  };

  type SysDictDefinitionParam = QueryParam & {
    code?: string;
    name?: string;
  };

  type SysDictInstance = {
    value: string;
    label: string;
    remark?: string;
    valid: boolean;
  };

  type SysDictInstanceParam = QueryParam & {
    dictDefinitionCode?: string;
    value?: string;
    label?: string;
  };

}
