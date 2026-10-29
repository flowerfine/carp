import React, {useEffect} from "react";
import {useIntl} from "@umijs/max";
import {useGraphInstance, useGraphStore} from "@antv/xflow";

// 节点状态列表
const nodeStatusList = [
  {
    id: 'node-0',
    status: 'success',
  },
  {
    id: 'node-1',
    status: 'success',
  },
  {
    id: 'node-2',
    status: 'success',
  },
  {
    id: 'node-3',
    status: 'success',
  },
  {
    id: 'node-4',
    status: 'error',
    statusMsg: '错误信息示例',
  },
]

// 边状态列表
const edgeStatusList = [
  {
    id: 'edge-0',
    status: 'success',
  },
  {
    id: 'edge-1',
    status: 'success',
  },
  {
    id: 'edge-2',
    status: 'success',
  },
  {
    id: 'edge-3',
    status: 'success',
  },
]

const InitNode: React.FC = () => {
  const intl = useIntl()
  const graph = useGraphInstance();
  const addNodes = useGraphStore((state) => state.addNodes);
  const addEdges = useGraphStore((state) => state.addEdges);

  useEffect(() => {
    if (graph) {
      fetch('/data/data-processing-dag.json')
        .then((response) => response.json())
        .then((data) => {
          // 使用 graph.fromJSON 方法导入进去的节点，无法被键盘快捷键处理
          // graph.fromJSON(data)
          graph.addNodes(data.nodes);
          graph.addEdges(data.edges);
          graph.zoomToFit({ maxScale: 1 });
          setTimeout(() => {
            excuteAnimate()
          }, 2000)
          setTimeout(() => {
            showNodeStatus()
            stopAnimate()
          }, 3000)
        })
    }
  }, [graph]);


// 显示节点状态
  const showNodeStatus = () => {
    nodeStatusList.forEach((item) => {
      const {id, status, statusMsg} = item
      const node = graph.getCellById(id)
      const data = node.getData() as CellStatus
      node.setData({
        ...data,
        status,
        statusMsg,
      })
    })
  }

// 开启边的运行动画
  const excuteAnimate = () => {
    graph.getEdges().forEach((edge) => {
      edge.attr({
        line: {
          stroke: '#3471F9',
        },
      })
      edge.attr('line/strokeDasharray', 5)
      edge.attr('line/style/animation', 'running-line 30s infinite linear')
    })
  }

// 关闭边的动画
  const stopAnimate = () => {
    graph.getEdges().forEach((edge) => {
      edge.attr('line/strokeDasharray', 0)
      edge.attr('line/style/animation', '')
    })
    edgeStatusList.forEach((item) => {
      const {id, status} = item
      const edge = graph.getCellById(id)
      if (status === 'success') {
        edge.attr('line/stroke', '#52c41a')
      }
      if (status === 'error') {
        edge.attr('line/stroke', '#ff4d4f')
      }
    })
    // 默认选中一个节点
    graph.select('node-2')
  }

  return null;
};

export {InitNode};
