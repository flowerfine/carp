import React from 'react';
import {FooterToolbar} from "@ant-design/pro-components";
import {Background, Control, Minimap} from '@antv/xflow';

const X6ControlMinimap: React.FC = () => {

  return (
    <>
      <Background color="#F2F7FA"/>
      <FooterToolbar
        style={{
          background: 'transparent',
          width: 0,
          bottom: 80,
          left: '50%',
        }}
        portalDom={false}
      >
        <Control items={['zoomOut', 'zoomTo', 'zoomIn', 'zoomToFit', 'zoomToOrigin']}/>
      </FooterToolbar>
      <FooterToolbar
        style={{
          background: 'transparent',
          width: 0,
          bottom: 50,
          right: 200,
        }}
        portalDom={false}
      >
        <Minimap/>
      </FooterToolbar>
    </>
  );
};

export default X6ControlMinimap;
