import {useState} from 'react';
import {AdminSecurityAPI} from "@/services/admin/security/typings";

export default () => {
  const [authCode, setAuthCode] = useState<AdminSecurityAPI.AuthCode>()

  return {
    authCode,
    setAuthCode
  };
};
