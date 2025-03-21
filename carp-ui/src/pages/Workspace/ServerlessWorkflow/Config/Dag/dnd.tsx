import React from 'react';
import {StringExt} from "@antv/x6";
import {useDnd} from "@antv/xflow";
import {ServerlessWorkflowService} from "@/services/workspace/workflow/serverless-workflow.service";
import X6Panel from "@/components/X6/Panel";

const Dnd = () => {
  const {startDrag} = useDnd();

  const onDrag = (e: React.MouseEvent<Element, MouseEvent>, item: X6API.DndNode) => {
    const node = {
      id: StringExt.uuid(),
      shape: item.shape,
      data: {
        label: item.label,
        meta: item.dndMeta
      },
      ports: item.ports?.map((port) => {return {id: StringExt.uuid(), group: port.group}}),
    }
    startDrag(node, e);
  };

  return (
    <X6Panel
      request={() => {
        return ServerlessWorkflowService.getDnds().then((response) => {
          if (response.success && response.data) {
            return response.data;
          }
          return []
        })
      }}
      onDrag={onDrag}
    />
  );
};

export default Dnd;
