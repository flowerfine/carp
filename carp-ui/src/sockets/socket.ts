import {Manager} from "socket.io-client";
import {AdminSecurityAPI} from "@/services/admin/security/typings";

export const TIMEOUT = 1000 * 5;

const manager = new Manager("http://localhost:8999", {
  transports: ["websocket"],
  timeout: TIMEOUT,
  autoConnect: false,
  reconnection: true,
  reconnectionAttempts: 60,
  reconnectionDelay: 1000
});

export const WorkflowSocketCreator = (currentUser: AdminSecurityAPI.OnlineUserInfo) => {
  return manager.socket(`/workflow`, {
    auth: {
      userId: currentUser?.userId
    }
  })
};
