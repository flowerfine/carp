import React from "react";
import {Button, Descriptions, Popover, Space, Typography} from "antd";
import {HolderOutlined, InfoCircleOutlined, MenuOutlined} from "@ant-design/icons";
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from "@antv/xflow";
import {Dropdown} from "@antv/x6-react-components";
import classnames from 'classnames'
import insertCss from 'insert-css'

// 节点类型
enum NodeType {
  INPUT = 'INPUT', // 数据输入
  FILTER = 'FILTER', // 数据过滤
  JOIN = 'JOIN', // 数据连接
  UNION = 'UNION', // 数据合并
  AGG = 'AGG', // 数据聚合
  OUTPUT = 'OUTPUT', // 数据输出
}

// 元素校验状态
enum CellStatus {
  DEFAULT = 'default',
  SUCCESS = 'success',
  ERROR = 'error',
}

// 节点位置信息
interface Position {
  x: number
  y: number
}

// 加工类型列表
const PROCESSING_TYPE_LIST = [
  {
    type: 'FILTER',
    name: '数据筛选',
  },
  {
    type: 'JOIN',
    name: '数据连接',
  },
  {
    type: 'UNION',
    name: '数据合并',
  },
  {
    type: 'AGG',
    name: '数据聚合',
  },

  {
    type: 'OUTPUT',
    name: '数据输出',
  },
]

// 不同节点类型的icon
const NODE_TYPE_LOGO = {
  INPUT:
    'https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*RXnuTpQ22xkAAAAAAAAAAAAADtOHAQ/original', // 数据输入
  FILTER:
    'https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*ZJ6qToit8P4AAAAAAAAAAAAADtOHAQ/original', // 数据筛选
  JOIN: 'https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*EHqyQoDeBvIAAAAAAAAAAAAADtOHAQ/original', // 数据连接
  UNION:
    'https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*k4eyRaXv8gsAAAAAAAAAAAAADtOHAQ/original', // 数据合并
  AGG: 'https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*TKG8R6nfYiAAAAAAAAAAAAAADtOHAQ/original', // 数据聚合
  OUTPUT:
    'https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*zUgORbGg1HIAAAAAAAAAAAAADtOHAQ/original', // 数据输出
}

const ProcessNode = ({node}: { node: Node }) => {
  const intl = getIntl(getLocale())
  return (
    <XFlow>
      <div> Process Node</div>
    </XFlow>
  );
}


insertCss(`
  .data-processing-dag-node {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .main-area {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 12px;
    width: 180px;
    height: 48px;
    color: rgba(0, 0, 0, 65%);
    font-size: 12px;
    font-family: PingFangSC;
    line-height: 24px;
    background-color: #fff;
    box-shadow: 0 -1px 4px 0 rgba(209, 209, 209, 50%), 1px 1px 4px 0 rgba(217, 217, 217, 50%);
    border-radius: 2px;
    border: 1px solid transparent;
  }
  .main-area:hover {
    border: 1px solid rgba(0, 0, 0, 10%);
    box-shadow: 0 -2px 4px 0 rgba(209, 209, 209, 50%), 2px 2px 4px 0 rgba(217, 217, 217, 50%);
  }

  .node-logo {
    display: inline-block;
    width: 24px;
    height: 24px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100%;
  }

  .node-name {
    overflow: hidden;
    display: inline-block;
    width: 70px;
    margin-left: 6px;
    color: rgba(0, 0, 0, 65%);
    font-size: 12px;
    font-family: PingFangSC;
    white-space: nowrap;
    text-overflow: ellipsis;
    vertical-align: top;
  }

  .status-action {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .status-icon {
    display: inline-block;
    width: 24px;
    height: 24px;
  }

  .status-icon-error {
    background: url('https://gw.alipayobjects.com/mdn/rms_43231b/afts/img/A*SEISQ6My-HoAAAAAAAAAAAAAARQnAQ')
      no-repeat center center / 100% 100%;
  }

  .status-icon-success {
    background: url('https://gw.alipayobjects.com/mdn/rms_43231b/afts/img/A*6l60T6h8TTQAAAAAAAAAAAAAARQnAQ')
      no-repeat center center / 100% 100%;
  }

  .more-action-container {
    margin-left: 12px;
    width: 15px;
    height: 15px;
    text-align: center;
    cursor: pointer;
  }

  .more-action {
    display: inline-block;
    width: 3px;
    height: 15px;
    background: url('https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*tFw7SIy-ttQAAAAAAAAAAAAADtOHAQ/original')
      no-repeat center center / 100% 100%;
  }

  .plus-dag {
    visibility: hidden;
    position: relative;
    margin-left: 12px;
    height: 48px;
  }

  .plus-action {
    position: absolute;
    top: calc(50% - 8px);
    left: 0;
    width: 16px;
    height: 16px;
    background: url('https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*ScX2R4ODfokAAAAAAAAAAAAADtOHAQ/original')
      no-repeat center center / 100% 100%;
    cursor: pointer;
  }
  .plus-action:hover {
    background-image: url('https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*tRaoS5XhsuQAAAAAAAAAAAAADtOHAQ/original');
  }

  .plus-action:active,
  .plus-action-selected {
    background-image: url('https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*k9cnSaSmlw4AAAAAAAAAAAAADtOHAQ/original');
  }

  .x6-node-selected .main-area {
    border-color: #3471f9;
  }

  .x6-node-selected .plus-dag {
    visibility: visible;
  }

  .processing-node-menu {
    padding: 2px 0;
    width: 105px;
    background-color: #fff;
    box-shadow: 0 9px 28px 8px rgba(0, 0, 0, 5%), 0 6px 16px 0 rgba(0, 0, 0, 8%),
      0 3px 6px -4px rgba(0, 0, 0, 12%);
    border-radius: 2px;
  }
  .processing-node-menu ul {
    margin: 0;
    padding: 0;
  }
  .processing-node-menu li {
    list-style:none;
  }

  .each-sub-menu {
    padding: 6px 12px;
    width: 100%;
  }

  .each-sub-menu:hover {
    background-color: rgba(0, 0, 0, 4%);
  }

  .each-sub-menu a {
    display: inline-block;
    width: 100%;
    height: 16px;
    font-family: PingFangSC;
    font-weight: 400;
    font-size: 12px;
    color: rgba(0, 0, 0, 65%);
  }

  .each-sub-menu span {
    margin-left: 8px;
    vertical-align: top;
  }

  .each-disabled-sub-menu a {
    cursor: not-allowed;
      color: rgba(0, 0, 0, 35%);
  }

  .node-mini-logo {
    display: inline-block;
    width: 16px;
    height: 16px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100%;
    vertical-align: top;
  }

  @keyframes running-line {
    to {
      stroke-dashoffset: -1000;
    }
  }
`)


export {ProcessNode};
