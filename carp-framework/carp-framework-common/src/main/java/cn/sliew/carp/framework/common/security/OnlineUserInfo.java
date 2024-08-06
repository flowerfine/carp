package cn.sliew.carp.framework.common.security;

import cn.sliew.carp.framework.common.dict.security.SecUserStatus;
import cn.sliew.carp.framework.common.dict.security.SecUserType;
import lombok.Data;

@Data
public class OnlineUserInfo {

    private Long userId;
    private SecUserType type;
    private String userName;
    private String nickName;
    private SecUserStatus status;
}
