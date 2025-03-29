import React, {useEffect, useRef, useState} from "react";
import {useModel} from "@umijs/max";
import {useGraphInstance} from "@antv/xflow";
import {ModalFormProps} from "@/typings";
import {WorkflowSocketCreator} from "@/sockets/socket";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowInstanceService} from "@/services/workspace/workflow/workflow-instance.service";

const InitNode: React.FC<ModalFormProps<WorkspaceWorkflowAPI.WorkflowInstance>> = ({data}) => {
  const {initialState, setInitialState} = useModel('@@initialState');
  const {currentUser} = initialState;
  const graph = useGraphInstance();
  const {current: socket} = useRef(WorkflowSocketCreator(currentUser));
  const [isConnected, setIsConnected] = useState<boolean>(socket.connected);

  useEffect(() => {
    socket.on("connect", () => {
      setIsConnected(true);
      console.log('socket on connect')
      socket.emitWithAck("customEvent", {
        "id": "1"
      }).then((response) => {
        console.log('socket on customEvent', response)
      })
    });

    socket.on("disconnect", (reason, details) => {
      setIsConnected(false);
      console.log('socket on disconnect', reason, details)
    });

    socket.on("info", (value) => {
      console.log('socket on info', value)
    });

    return () => {
      socket.off("connect");
      socket.off("disconnect");
      socket.off("info");
    };
  }, []);

  useEffect(() => {
    setTimeout(() => {
      if (!isConnected) {
        console.log('socket on connect')
        connectSocket()
      }
    }, 5000)
  }, []);

  useEffect(() => {
    if (graph) {
      WorkflowInstanceService.getGraph(data?.id).then(response => {
        if (response.success && response.data) {
          if (response.data.nodes) {
            graph.addNodes(response.data.nodes);
            if (response.data.edges) {
              graph.addEdges(response.data.edges);
            }
          }
          graph.zoomToFit({maxScale: 1});
        }
      })
    }
  }, [graph]);

  const connectSocket = () => {
    if (!socket.connected) {
      socket.connect();
    }
  }

  return null;
};

export {InitNode};
