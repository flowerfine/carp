import React from "react";
import {FileAddOutlined, FileDoneOutlined} from "@ant-design/icons";
import {useAccess, useIntl} from "@umijs/max";
import {EditorLayout} from '@ant-design/pro-editor';
import EditableTabs from "@/components/Editor/EditableTabs/EditableTabs";
import QueryEditor from "@/components/Editor/QueryEditor";

const MetadataGravitinoEditorWeb: React.FC = () => {
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
                children: (
                    <EditableTabs
                        items={[
                            {
                                key: '1',
                                label: 'Tab 1',
                                icon: <FileDoneOutlined />
                            },
                            {
                                key: '2',
                                label: 'Tab 2',
                                icon: <FileDoneOutlined />
                            },
                            {
                                key: '3',
                                label: 'Tab 3',
                                icon: <FileAddOutlined />
                            },
                        ]}
                    />
                )
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
                children: (
                    <QueryEditor/>
                )
            }}
            bottomPannel={{
                children: <div>输出控制台</div>
            }}
        />
    );
}

export default MetadataGravitinoEditorWeb;
