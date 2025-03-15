import React from 'react';
import {Panel, PanelGroup, PanelResizeHandle} from "react-resizable-panels";
import X6ControlMinimap from "@/components/X6/Control";
import X6GridSnapline from "@/components/X6/Grid";
import X6HistoryClipboard from "@/components/X6/History";

type X6LayoutProps = {
  menubar?: React.ReactNode;
  toolbar?: React.ReactNode;
  dnd?: React.ReactNode;
  body: React.ReactNode;
};

const X6Layout: React.FC = ({menubar, toolbar, dnd, body}: X6LayoutProps) => {

  return (
    <div style={{height: 700}}>
      <PanelGroup direction="vertical">
        {menubar && (
          <>
            <Panel minSize={5} maxSize={5} style={{background: '#b2a17b'}}>
              {menubar}
            </Panel>
            <PanelResizeHandle/>
          </>
        )}
        <Panel style={{background: '#c6d252'}}>
          <PanelGroup direction="horizontal">
            {dnd && (
              <>
                <Panel minSize={5} maxSize={15} style={{background: '#77d529',}}>
                  {dnd}
                </Panel>
                <PanelResizeHandle/>
              </>
            )}
            <Panel style={{background: '#4dd79d',}}>
              <PanelGroup direction="vertical">
                {toolbar && (
                  <>
                    <Panel minSize={5} maxSize={5} style={{background: '#208cb4',}}>
                      {toolbar}
                    </Panel>
                    <PanelResizeHandle/>
                  </>
                )}
                <Panel style={{background: '#7213c0',}}>
                  {body}
                  <X6ControlMinimap/>
                  <X6GridSnapline/>
                  <X6HistoryClipboard/>
                </Panel>
                <PanelResizeHandle/>
              </PanelGroup>
            </Panel>
          </PanelGroup>
        </Panel>
      </PanelGroup>
    </div>
  );
};

export default X6Layout;
