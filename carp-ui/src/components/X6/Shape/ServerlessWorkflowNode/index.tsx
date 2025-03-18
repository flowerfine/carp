import React, {useState} from "react";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import BasicNode from "@/components/X6/Shape/BasicNode";
import ServerlessNodeConfig from "@/pages/Workspace/ServerlessWorkflow/Instance/NodeConfig";

const SERVERLESS_WORKFLOW_NODE = 'serverless-workflow-node';
const SERVERLESS_WORKFLOW_EDGE = 'serverless-workflow-edge';

const ServerlessWorkflowNode = ({node}: { node: Node }) => {
  const [openDrawerForm, setOpenDrawerForm] = useState<boolean>(false)

  return (
    <XFlow>
      <div onDoubleClick={() => setOpenDrawerForm(true)}>
        <BasicNode
          node={node}
        />
      </div>
      {openDrawerForm && (
        <ServerlessNodeConfig
          data={node}
          visible={openDrawerForm}
          onCancel={() => setOpenDrawerForm(false)}
          onFinish={(values) => {
            // 移除 undefined 字段，否则会更新异常
            const attrs: Record<string, any> = Object.keys(values)
              .filter((key) => values[key] != null && values[key] != undefined)
              .reduce((acc, key) => ({...acc, [key]: values[key]}), {});
            node?.setData({...node?.data, nodeData: attrs})
            setOpenDrawerForm(false);
          }}
        />
      )}
    </XFlow>
  );
}

register({
  shape: SERVERLESS_WORKFLOW_NODE,
  width: 240,
  height: 60,
  component: ServerlessWorkflowNode,
  // port默认不可见. stroke 和 fill 设置为 transparent
  ports: {
    groups: {
      out: {
        position: 'right',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#85A5FF',
            strokeWidth: 1,
            fill: '#fff',
          },
        },
      },

      in: {
        position: 'left',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#85A5FF',
            strokeWidth: 1,
            fill: '#fff',
          },
        },
      },
    },
  },
})

Graph.registerEdge(
  SERVERLESS_WORKFLOW_EDGE,
  {
    markup: [
      {
        tagName: 'path',
        selector: 'wrap',
        attrs: {
          fill: 'none',
          cursor: 'pointer',
          stroke: 'transparent',
          strokeLinecap: 'round',
        },
      },
      {
        tagName: 'path',
        selector: 'line',
        attrs: {
          fill: 'none',
          pointerEvents: 'none',
        },
      },
    ],
    connector: {name: 'smooth'},
    attrs: {
      wrap: {
        connection: true,
        strokeWidth: 10,
        strokeLinejoin: 'round',
      },
      line: {
        connection: true,
        stroke: '#A2B1C3',
        strokeWidth: 1,
        targetMarker: {
          name: 'classic',
          size: 6,
        },
      },
    }
  },
  true)

export {
  SERVERLESS_WORKFLOW_NODE,
  SERVERLESS_WORKFLOW_EDGE
};
