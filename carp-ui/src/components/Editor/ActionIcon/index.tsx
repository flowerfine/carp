import React from "react";
import {PauseCircleOutlined, PlayCircleOutlined} from "@ant-design/icons";
import {ActionIcon, IconsProps} from "@ant-design/pro-editor";

export const RunAction: React.FC<IconsProps> = (props) => (
  <ActionIcon icon={<PlayCircleOutlined />} {...props} />
)

export const PauseAction: React.FC<IconsProps> = (props) => (
  <ActionIcon icon={<PauseCircleOutlined />} {...props} />
)
