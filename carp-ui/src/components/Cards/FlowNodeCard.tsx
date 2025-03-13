import React from "react";
import {getIntl, getLocale} from "@umijs/max";
import {ProCard, ProCardProps} from "@ant-design/pro-components";

const FlowNodeCard = (props: ProCardProps) => {
  const intl = getIntl(getLocale())

  return (
    <ProCard {...props}/>
  );
}

export default FlowNodeCard;
