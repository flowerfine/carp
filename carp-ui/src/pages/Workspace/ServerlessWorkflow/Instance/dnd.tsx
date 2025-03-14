import React from 'react';
import {useDnd} from "@antv/xflow";
import {ServerlessWorkflowService} from "@/services/workspace/workflow/serverless-workflow.service";
import X6Panel from "@/components/X6/Panel";
import {CellStatus, PROCESS_NODE} from "@/components/Flow/Node/ProcessNode";

const Dnd = () => {
  const {startDrag} = useDnd();

  let id = 0;

  const onDrag = (e: React.MouseEvent<Element, MouseEvent>, item: X6API.DndNode) => {
    id += 1;
    const node = {
      id: id.toString(),
      shape: item.shape,
      data: {
        type: item.key,
        name: item.label + "_" + id,
        status: CellStatus.DEFAULT,
      },
      ports: item.ports,
      tools: [
        {
          name: "button-remove",
          args: {
            x: "100%",
            y: 0,
            offset: {x: -35, y: 0}
          }
        }
      ],
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
