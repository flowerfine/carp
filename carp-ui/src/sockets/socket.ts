import {Manager,io,  SocketOptions} from "socket.io-client";
import {AdminSecurityAPI} from "@/services/admin/security/typings";

export const TIMEOUT = 1000 * 5;

const options: SocketOptions = {
  transports: ["websocket"],
  timeout: TIMEOUT,
  autoConnect: false,
  reconnection: true,
  reconnectionAttempts: 60,
  reconnectionDelay: 1000
}

const manager = new Manager("http://localhost:8999", {
  ...options
});

export const WorkflowSocketCreator = (currentUser: AdminSecurityAPI.OnlineUserInfo) => {
  return manager.socket(`/workflow`, {
    auth: {
      userId: currentUser?.userId,
      u_token: currentUser?.token,
      userName: currentUser?.userName
    }
  })
};
