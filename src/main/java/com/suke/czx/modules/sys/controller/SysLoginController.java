package com.suke.czx.modules.sys.controller;

import cn.hutool.core.io.IoUtil;
import com.google.code.kaptcha.Producer;
import com.suke.czx.common.annotation.AuthIgnore;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.Constant;
import com.suke.zhjg.common.autofull.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关
 *
 * @author czx
 * @email object_czx@163.com
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "SysLoginController", tags = "登录相关")
public class SysLoginController extends AbstractController {

    private final Producer producer;
    private final RedisTemplate redisTemplate;

    @AuthIgnore
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public R hello() {
        return R.ok("hello welcome to use x-springboot");
    }

    /**
     * 验证码
     */
    @AuthIgnore
    @SneakyThrows
    @RequestMapping(value = "/sys/code/{time}", method = RequestMethod.GET)
    public void captcha(@PathVariable("time") String time, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        log.info("验证码:{}", text);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //redis 60秒
        redisTemplate.opsForValue().set(Constant.NUMBER_CODE_KEY + time, text, 60, TimeUnit.SECONDS);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IoUtil.close(out);
    }

    /**
     * 退出
     */
    @AuthIgnore
    @GetMapping(value = "/sys/logout")
    public void logout() {

    }
}
