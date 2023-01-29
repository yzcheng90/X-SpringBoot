package com.suke.czx.modules.sys.vo;

import lombok.Data;

/**
 * @Description //TODO $
 * @Date 12:50
 * @Author yzcheng90@qq.com
 **/
@Data
public class UserInfoVO {

    public String userId;
    public String userName;
    public String name;
    public String loginIp;
    public String photo;
    public String time;
    public String[] roles;
    public String[] authBtnList;


}
