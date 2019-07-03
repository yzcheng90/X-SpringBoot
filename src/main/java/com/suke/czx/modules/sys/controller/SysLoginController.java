package com.suke.czx.modules.sys.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.R;
import com.suke.czx.common.utils.ShiroUtils;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.service.SysUserService;
import com.suke.czx.modules.sys.service.SysUserTokenService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2019年4月18日 下午8:15:31
 */
@Slf4j
@RestController
@AllArgsConstructor
public class SysLoginController extends AbstractController {

	private final Producer producer;
	private final SysUserService sysUserService;
	private final SysUserTokenService sysUserTokenService;
	private final RedisTemplate redisTemplate;
	private final ShiroUtils shiroUtils;

	@RequestMapping("/")
	public R hello(){
		return R.ok("hello welcome to use x-springboot");
	}

	/**
	 * 验证码
	 */
	@SneakyThrows
	@RequestMapping("/sys/code/{time}")
	public void captcha(@PathVariable("time") String time, HttpServletResponse response){
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		log.info("==================验证码:{}====================",text);
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//redis 60秒
		redisTemplate.opsForValue().set(Constant.NUMBER_CODE_KEY + time,text,60, TimeUnit.SECONDS);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}
	/**
	 * 短信验证码
	 */
	@RequestMapping("/mobile/code/{number}")
	public Map<String, Object> mobile(@PathVariable("number") String number){

		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mobile",number);
		SysUser user = sysUserService.getOne(queryWrapper);

		//账号不存在
		if(user == null) {
			return R.error("手机号码未注册");
		}

		String mobile = (String) redisTemplate.opsForValue().get(Constant.MOBILE_CODE_KEY + number);
		if(!StrUtil.isEmpty(mobile)){
			return R.error("验证码未失效");
		}

		//生成4位验证码
		String code = RandomUtil.randomNumbers(Constant.CODE_SIZE);
		log.info("==================短信验证码:{}====================",code);
		//redis 60秒
		redisTemplate.opsForValue().set(Constant.MOBILE_CODE_KEY + number,code,60, TimeUnit.SECONDS);
		//调用短信服务去发送

		return R.ok("验证码发送成功");
	}

	/**
	 * 密码登录
	 */
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Map<String, Object> login(String username, String password, String captcha,String randomStr){

		String code_key = (String) redisTemplate.opsForValue().get(Constant.NUMBER_CODE_KEY + randomStr);
		if(StrUtil.isEmpty(code_key)){
			return R.error("验证码过期");
		}

		if(!captcha.equalsIgnoreCase(code_key)){
			return R.error("验证码不正确");
		}

		//用户信息
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",username);
		SysUser user = sysUserService.getOne(queryWrapper);

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}

	/**
	 * 手机号码登录
	 */
	@RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
	public Map<String, Object> mobileLogin(String mobile, String code){

		String code_key = (String) redisTemplate.opsForValue().get(Constant.MOBILE_CODE_KEY + mobile);
		if(StrUtil.isEmpty(code_key)){
			return R.error("验证码过期");
		}

		if(!code.equalsIgnoreCase(code_key)){
			return R.error("验证码不正确");
		}

		//用户信息
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mobile",mobile);
		SysUser user = sysUserService.getOne(queryWrapper);

		//账号不存在
		if(user == null) {
			return R.error("账号不存在");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}


	/**
	 * 退出
	 */
	@RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
	public R logout() {
		sysUserTokenService.logout(getUserId());
		shiroUtils.getSubject().logout();
		return R.ok();
	}

	/**
	 * 未认证
	 */
	@RequestMapping(value = "/sys/unauthorized", method = RequestMethod.POST)
	public R unauthorized() {
		return R.error(HttpStatus.SC_UNAUTHORIZED, "unauthorized");
	}


	
}
