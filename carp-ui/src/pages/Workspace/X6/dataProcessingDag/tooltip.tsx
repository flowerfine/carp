import React, { useState } from 'react';
import { Tooltip } from 'antd';
import { Edge, useGraphEvent, useGraphInstance } from '@antv/xflow';

const ConnectTooltip = () => {
  const graph = useGraphInstance();
  const [tooltipVisible, setTooltipVisible] = useState(false);
  const [tooltipX, setTooltipX] = useState(0);
  const [tooltipY, setTooltipY] = useState(0);
  const [tooltipTitle, setTooltipTitle] = useState<React.ReactNode>('');

  useGraphEvent('edge:mouseenter', ({ edge, e }) => {
    showTooltip(edge, e.clientX, e.clientY);
  });

  useGraphEvent('edge:mousemove', ({ e }) => {
    if (tooltipVisible) {
      setTooltipX(e.clientX);
      setTooltipY(e.clientY);
    }
  });

  useGraphEvent('edge:mouseleave', () => {
    hideTooltip();
  });

  const getEdgeTitle = (edge: Edge): React.ReactNode => {
    if (!graph) {
      return '';
    }
    const data = edge.getData() || {};
    const { source, target } = data;
    const sourceNode = source ? graph.getCellById(source) : null;
    const targetNode = target ? graph.getCellById(target) : null;
    const sourceName = sourceNode?.getData?.()?.name || source;
    const targetName = targetNode?.getData?.()?.name || target;

    return (
      <div style={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
        <div>
          <span style={{ color: 'rgba(255,255,255,0.65)', marginRight: 8 }}>From:</span>
          <span>{sourceName}</span>
        </div>
        <div>
          <span style={{ color: 'rgba(255,255,255,0.65)', marginRight: 8 }}>To:</span>
          <span>{targetName}</span>
        </div>
      </div>
    );
  };

  const showTooltip = (edge: Edge, x: number, y: number) => {
    setTooltipTitle(getEdgeTitle(edge));
    setTooltipX(x);
    setTooltipY(y);
    setTooltipVisible(true);
  };

  const hideTooltip = () => {
    setTooltipVisible(false);
  };

  return (
    <div
      style={{
        position: 'fixed',
        left: tooltipX,
        top: tooltipY,
        width: 0,
        height: 0,
        pointerEvents: 'none',
        zIndex: 9999,
      }}
    >
      <Tooltip
        title={tooltipTitle}
        open={tooltipVisible}
        placement="top"
        rootClassName="edge-tooltip-overlay"
      >
        <span />
      </Tooltip>
    </div>
  );
};

export { ConnectTooltip };
