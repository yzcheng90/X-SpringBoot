package com.suke.czx.modules.app.controller;


import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.validator.Assert;
import com.suke.czx.modules.app.service.user.AppUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 注册
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/app")
public class ApiRegisterController {

    @Resource(name = "appUserService")
    private AppUserService appUserService;

    /**
     * 注册
     */
    @PostMapping("register")
    public AppBaseResult register(@RequestBody AppBaseResult appBaseResult) throws Exception {
        HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class);
        Assert.isNull(pd.get("mobile"), "手机号不能为空");
        Assert.isNull(pd.get("password"), "密码不能为空");
        appUserService.save(pd);
        return AppBaseResult.success();
    }
}
