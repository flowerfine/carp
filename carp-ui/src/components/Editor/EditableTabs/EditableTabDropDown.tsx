import React from "react";
import {Dropdown, MenuProps, Typography} from "antd";
import {useIntl} from "@umijs/max";
import {TabItem} from "./EditableTabs";

export type ContextMenuProps = {
  tabItem: TabItem
}

const EditableTabDropDown: React.FC<ContextMenuProps> = (props) => {
  const intl = useIntl();

  const menuItems = (tabKey: string): MenuProps['items'] => [
    {
      key: 'close',
      label: intl.formatMessage({id: 'pages.editor.tabs.close'}),
      onClick: () => {
        console.log('close')
      },
    },
    {
      key: 'closeOther',
      label: intl.formatMessage({ id: 'pages.editor.tabs.closeOther' }),
      onClick: () => {
        console.log('closeOther')
      },
    },
    {
      key: 'closeAll',
      label: intl.formatMessage({ id: 'pages.editor.tabs.closeAll' }),
      onClick: () => {
        console.log('closeAll')
      },
    },
  ];

  return (
    <Dropdown
      menu={{ items: menuItems(props.tabItem.key) }}
      trigger={['contextMenu']}
    >
      <Typography.Text
        ellipsis={{ tooltip: { placement: 'bottom' } }}
      >
        {props.tabItem.label}
      </Typography.Text>
    </Dropdown>
  );
}

export default EditableTabDropDown;
