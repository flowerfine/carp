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
        stencilGraphWidth: 300,
        stencilGraphHeight: 180,
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
          columns: 2,
          columnWidth: 80,
          rowHeight: 55,
        }
      });


      stencilContainer.current.appendChild(stencil.container)

      const input = graph.createNode({
        shape: PROCESS_NODE,
        x: 40,
        y: 40,
        width: 80,
        height: 40,
        attrs: commonAttrs,
        data: {
          type: 'INPUT',
          name: '数据输入',
          status: CellStatus.DEFAULT,
        },
      })

      const output = graph.createNode({
        shape: PROCESS_NODE,
        x: 40,
        y: 40,
        width: 80,
        height: 40,
        attrs: commonAttrs,
        data: {
          type: 'OUTPUT',
          name: '数据输出',
          status: CellStatus.DEFAULT,
        },
      })

      const n2 = graph.createNode({
        label: 'circle',
        shape: 'circle',
        x: 180,
        y: 40,
        width: 40,
        height: 40,
        attrs: commonAttrs,
      })

      const n3 = graph.createNode({
        shape: 'ellipse',
        x: 280,
        y: 40,
        width: 80,
        height: 40,
        label: 'ellipse',
        attrs: commonAttrs,
      })

      const n4 = graph.createNode({
        shape: 'path',
        x: 420,
        y: 40,
        width: 40,
        height: 40,
        path: 'M24.85,10.126c2.018-4.783,6.628-8.125,11.99-8.125c7.223,0,12.425,6.179,13.079,13.543c0,0,0.353,1.828-0.424,5.119c-1.058,4.482-3.545,8.464-6.898,11.503L24.85,48L7.402,32.165c-3.353-3.038-5.84-7.021-6.898-11.503c-0.777-3.291-0.424-5.119-0.424-5.119C0.734,8.179,5.936,2,13.159,2C18.522,2,22.832,5.343,24.85,10.126z',
        attrs: commonAttrs,
        label: 'path',
      })

      stencil.load([input], 'source')
      stencil.load([output], 'sink')
      stencil.load([n2, n3, n4], 'transform')
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
