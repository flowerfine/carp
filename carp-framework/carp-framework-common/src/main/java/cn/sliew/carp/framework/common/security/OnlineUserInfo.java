package cn.sliew.carp.framework.common.security;

import cn.sliew.carp.framework.common.dict.security.UserStatus;
import cn.sliew.carp.framework.common.dict.security.UserType;
import lombok.Data;

@Data
public class OnlineUserInfo {

    private Long userId;
    private UserType type;
    private String userName;
    private String nickName;
    private UserStatus status;
}
