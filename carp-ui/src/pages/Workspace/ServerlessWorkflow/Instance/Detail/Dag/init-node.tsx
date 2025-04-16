import React, {useEffect, useRef, useState} from "react";
import {useModel} from "@umijs/max";
import {Cell, useGraphInstance} from "@antv/xflow";
import {ModalFormProps} from "@/typings";
import {WorkflowSocketCreator} from "@/sockets/socket";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {ServerlessWorkflowInstanceService} from "@/services/workspace/workflow/serverless-workflow-instance.service";

const InitNode: React.FC<ModalFormProps<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>> = ({data}) => {
  const {initialState, setInitialState} = useModel('@@initialState');
  const {currentUser} = initialState;
  const graph = useGraphInstance();
  const {current: socket} = useRef(WorkflowSocketCreator(currentUser));
  const [isConnected, setIsConnected] = useState<boolean>(socket.connected);

  useEffect(() => {
    socket.on("connect", () => {
      setIsConnected(true);
      socket.emitWithAck("readLogs", {
        "workflowInstanceId": data?.id
      })
      socket.emitWithAck("readEvents", {
        "workflowInstanceId": data?.id
      })
    });

    socket.on("disconnect", (reason, details) => {
      setIsConnected(false);
    });

    socket.on("pushLogs", (value) => {
      console.log('socket on pushLogs', value)
    });

    socket.on("pushEvents", (value) => {
      console.log('socket on pushEvents', value)
      resetGraph();
    });

    return () => {
      socket.off("connect");
      socket.off("disconnect");
      socket.off("pushLogs");
      socket.off("pushEvents");
    };
  }, []);

  useEffect(() => {
    setTimeout(() => {
      if (!isConnected) {
        connectSocket()
      }
    }, 5000)
  }, []);

  useEffect(() => {
    resetGraph()
  }, [graph]);

  const connectSocket = () => {
    if (!socket.connected) {
      socket.connect();
    }
  }

  const resetGraph = () => {
    if (graph) {
      ServerlessWorkflowInstanceService.toX6Graph(data?.id).then(response => {
        if (response.success && response.data) {
          graph.resetCells([])
          if (response.data.nodes) {
            // cells.push(response.data.nodes);
            graph.addNodes(response.data.nodes);
            if (response.data.edges) {
              // cells.push(response.data.edges)
              graph.addEdges(response.data.edges);
            }
          }
          graph.zoomToFit({maxScale: 1});
        }
      })
    }
  }

  return null;
};

export {InitNode};
