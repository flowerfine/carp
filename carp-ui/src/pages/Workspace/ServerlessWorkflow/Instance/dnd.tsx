import React from 'react';
import {useDnd} from "@antv/xflow";
import {ServerlessWorkflowService} from "@/services/workspace/workflow/serverless-workflow.service";
import X6Panel from "@/components/X6/Panel";
import {guid} from "@antv/l7";

const Dnd = () => {
  const {startDrag} = useDnd();

  const onDrag = (e: React.MouseEvent<Element, MouseEvent>, item: X6API.DndNode) => {
    const node = {
      shape: item.shape,
      data: {
        label: item.label,
        dndMeta: item.dndMeta
      },
      ports: item.ports?.map((port) => {return {id: guid(), group: port.group}}),
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
