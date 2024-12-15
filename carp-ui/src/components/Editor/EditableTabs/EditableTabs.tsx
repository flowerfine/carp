import React, { useRef, useState } from "react";
import { Tabs } from "antd";
import { FileAddOutlined } from "@ant-design/icons";
import { useAccess, useIntl } from "@umijs/max";
import EditableTabContextMenu from "./EditableTabContextMenu";
import EditableTabDropDown from "./EditableTabDropDown";

export type TabItem = {
  key: string;
  label: React.ReactNode;
  icon: React.ReactNode;
};

export type EditableTabsProps = {
  items: TabItem[];
  onTabAdd?: (tab: TabItem) => void,
  onTabDelete?: (tab: TabItem) => void,
  onTabChange?: (activeKey: string, tab: TabItem) => void,
};

const EditableTabs: React.FC<EditableTabsProps> = (props) => {
  const intl = useIntl();
  const access = useAccess();
  const [activeKey, setActiveKey] = useState<string | undefined>(props.items[0]?.key);
  const [items, setItems] = useState<TabItem[]>([]);
  const newTabIndex = useRef(0);

  const onChange = (newActiveKey: string) => {
    setActiveKey(newActiveKey);
    if (props.onTabChange) {
      const index = items?.findIndex((item) => item.key === newActiveKey);
      props.onTabChange(newActiveKey, items[index])
    }
  };

  const onEdit = (
    targetKey: React.MouseEvent | React.KeyboardEvent | string,
    action: 'add' | 'remove',
  ) => {
    if (action === 'add') {
      add();
    } else {
      remove(targetKey);
    }
  };

  const add = () => {
    const newActiveKey = `newTab${newTabIndex.current++}`;
    const newPanes = [...items];
    const newTabItem: TabItem = { key: newActiveKey, label: 'New Tab', icon: <FileAddOutlined /> }
    newPanes.push(newTabItem);
    setItems(newPanes);
    setActiveKey(newActiveKey);
    if (props.onTabAdd) {
      props.onTabAdd(newTabItem)
    }
  };

  const remove = (targetKey: TargetKey) => {
    let newActiveKey = activeKey;
    let lastIndex = -1;
    items.forEach((item, i) => {
      if (item.key === targetKey) {
        lastIndex = i - 1;
      }
    });
    const index = items?.findIndex((item) => item.key === newActiveKey);
    const removedTabItem = items[index]
    const newPanes = items.filter((item) => item.key !== targetKey);
    if (newPanes.length && newActiveKey === targetKey) {
      if (lastIndex >= 0) {
        newActiveKey = newPanes[lastIndex].key;
      } else {
        newActiveKey = newPanes[0].key;
      }
    }
    setItems(newPanes);
    setActiveKey(newActiveKey);
    if (props.onTabDelete) {
      props.onTabDelete(removedTabItem)
    }
  };

  const buildContextMenu = (item: TabItem): TabItem & { label: React.ReactNode } => {
    return {
      ...item,
      label: <EditableTabDropDown tabItem={item} />,
    };
  };

  return (
    <>
      <Tabs
        type={"editable-card"}
        activeKey={activeKey}
        items={items?.map((item) => buildContextMenu(item))}
        onChange={onChange}
        onEdit={onEdit}
      />
    </>
  );
}

export default EditableTabs;
