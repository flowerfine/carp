import React from "react";
import { CopyOutlined, FormatPainterOutlined, PauseCircleOutlined, PlayCircleOutlined } from "@ant-design/icons";
import { useIntl } from "@umijs/max";
import { ActionGroup, ActionIconGroupItemType } from "@ant-design/pro-editor";
import { Col, Row } from "antd";

const ToolBar: React.FC = (props) => {
  const intl = useIntl();

  const items: ActionIconGroupItemType[] = [
    {
      icon: <PlayCircleOutlined />,
      title: intl.formatMessage({id: 'pages.editor.toolbar.play'}),
    },
    {
      icon: <PauseCircleOutlined />,
      title: intl.formatMessage({id: 'pages.editor.toolbar.pause'}),
    },
    {
      icon: <CopyOutlined />,
      title: intl.formatMessage({id: 'pages.editor.toolbar.save'}),
    },
    {
      type: 'divider',
    },
    {
      icon: <FormatPainterOutlined />,
      title: intl.formatMessage({id: 'pages.editor.toolbar.format'}),
    },
  ]

  return (
    <Row>
      <Col span={16}></Col>
      <Col span={8}><ActionGroup items={items} /></Col>
    </Row>
  );
}

export default ToolBar;
