import React from "react";
import {useAccess, useIntl} from "@umijs/max";
import {EditorLayout} from '@ant-design/pro-editor';
import {Tabs} from "antd";

export type TabItem = {
  key: string;
  label: React.ReactNode;
  children: React.ReactNode;
};

export type EditableTabsProps = {
  tabList?: TabItem[];
  onTabAdd?: (tab: TabItem) => void,
  onTabDelete?: (tab: TabItem) => void,
  onTabChange?: (activeKey: string, tab: TabItem) => void,
};

const EditableTabs: React.FC<EditableTabsProps> = (props) => {
    const intl = useIntl();
    const access = useAccess();

    return (
        <EditorLayout
            style={{
                maxWidth: '100%',
                height: '600px',
            }}
            type={"Left&Right"}
            header={{
                iconConfig: false,
                children: <div>tab控制台</div>
            }}
            footer={false}
            leftPannel={{
                children: <div>schema信息</div>
            }}
            rightPannel={{
                minWidth: 300,
                children: <div>查询历史等功能</div>
            }}
            centerPannel={{
                tabs: {
                    type: "editable-card",
                    items: [
                        {label: 'Tab 1', children: 'Content of Tab 1', key: '1'},
                        {label: 'Tab 2', children: 'Content of Tab 2', key: '2'},
                    ]
                },
                children: (<Tabs
                    type={"editable-card"}
                    items={[
                        {label: 'Tab 1', children: 'Content of Tab 1', key: '1'},
                        {label: 'Tab 2', children: 'Content of Tab 2', key: '2'},
                    ]}
                />)
            }}
            bottomPannel={{
                children: <div>输出控制台</div>
            }}
        />
    );
}

export default EditableTabs;
