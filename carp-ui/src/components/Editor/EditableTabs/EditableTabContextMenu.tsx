import React, {useEffect, useRef} from "react";
import {useIntl} from "@umijs/max";
import {TabItem} from "./EditableTabs";
import {ContextMenu} from "@ant-design/pro-editor";
import {CopyOutlined} from "@ant-design/icons";

export type ContextMenuProps = {
  tabItem: TabItem
}

const EditableTabContextMenu: React.FC<ContextMenuProps> = (props) => {
  const intl = useIntl();
  const divContainer = useRef(null);
  const contextMenu = useRef(null);

  useEffect(() => {
    // 在组件挂载后访问 DOM 元素
    if (contextMenu.current) {
      console.log(contextMenu.current)
    }
  }, [contextMenu]);

  return (
    <div ref={divContainer}>
      <ContextMenu
        ref={contextMenu}
        container={divContainer.current ? divContainer.current : this}
        items={[
          {
            key: 'copy',
            label: '复制',
            icon: <CopyOutlined />,
            shortcut: ['meta', 'C'],
          },
        ]}
      />
    </div>
  );
}

export default EditableTabContextMenu;
