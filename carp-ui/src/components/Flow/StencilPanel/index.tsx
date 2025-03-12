import React, {useEffect, useRef} from 'react';
import {useGraphInstance} from '@antv/xflow';
import {Stencil} from '@antv/x6-plugin-stencil'
import './index.less'
import {CellStatus, PROCESS_NODE} from "@/components/Flow/Node/ProcessNode";

const commonAttrs = {
  body: {
    fill: '#fff',
    stroke: '#8f8f8f',
    strokeWidth: 1,
  },
}

const StencilPanel: React.FC = (props: Stencil.Options) => {
  const container = useRef<HTMLDivElement>(null)
  const stencilContainer = useRef<HTMLDivElement>(null)
  const graph = useGraphInstance();

  useEffect(() => {
    if (graph && container.current && stencilContainer.current) {
      if (graph.getPlugin('stencil')) {
        graph.disposePlugins('stencil');
      }
      const stencil = new Stencil({
        target: graph,
        title: '算子组件库',
        search(cell, keyword) {
          return cell.shape.indexOf(keyword) !== -1
        },
        placeholder: 'Search by shape name',
        notFoundText: 'Not Found',
        stencilGraphHeight: 0,
        collapsable: true,
        groups: [
          {
            name: 'source',
            title: '数据输入',
            collapsable: true,
          },
          {
            name: 'sink',
            title: '数据输出',
            collapsable: true,
          },
          {
            name: 'transform',
            title: '数据处理',
            collapsable: true,
          },
        ],
        layoutOptions: {
          columns: 1,
          columnWidth: 185,
          columnHeight: 185,
          resizeToFit: true
        }
      });


      stencilContainer.current.appendChild(stencil.container)

      const input = graph.createNode({
        shape: PROCESS_NODE,
        x: 0,
        y: 0,
        width: 85,
        height: 20,
        attrs: commonAttrs,
        data: {
          type: 'INPUT',
          name: '数据输入',
          status: CellStatus.DEFAULT,
        },
      })

      const output = graph.createNode({
        shape: PROCESS_NODE,
        x: 0,
        y: 0,
        width: 85,
        height: 20,
        attrs: commonAttrs,
        data: {
          type: 'OUTPUT',
          name: '数据输出',
          status: CellStatus.DEFAULT,
        },
      })

      const filter = graph.createNode({
        shape: PROCESS_NODE,
        x: 0,
        y: 0,
        width: 85,
        height: 20,
        attrs: commonAttrs,
        data: {
          type: 'FILTER',
          name: '数据过滤',
          status: CellStatus.DEFAULT,
        },
      })

      const join = graph.createNode({
        shape: PROCESS_NODE,
        x: 0,
        y: 0,
        width: 85,
        height: 20,
        attrs: commonAttrs,
        data: {
          type: 'JOIN',
          name: '数据连接',
          status: CellStatus.DEFAULT,
        },
      })

      const union = graph.createNode({
        shape: PROCESS_NODE,
        x: 0,
        y: 0,
        width: 85,
        height: 20,
        attrs: commonAttrs,
        data: {
          type: 'UNION',
          name: '数据合并',
          status: CellStatus.DEFAULT,
        },
      })

      const agg = graph.createNode({
        shape: PROCESS_NODE,
        x: 0,
        y: 0,
        width: 85,
        height: 20,
        attrs: commonAttrs,
        data: {
          type: 'AGG',
          name: '数据聚合',
          status: CellStatus.DEFAULT,
        },
      })

      stencil.load([input], 'source')
      stencil.load([output], 'sink')
      stencil.load([filter, join, union, agg], 'transform')
    }
  }, [graph, props]);

  return (
    <div className="stencil-app">
      <div className="app-stencil" ref={stencilContainer}/>
      <div className="app-content" ref={container}/>
    </div>
  );
};

export {StencilPanel};
