package com.customer.system.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.customer.config.JWTConfig;
import com.customer.dto.LoginUserDto;
import com.customer.entity.system.UserInfoEntity;
import com.customer.enums.RedisKeyEnums;
import com.customer.enums.StatusEnums;
import com.customer.exception.BusinessException;
import com.customer.resp.ApiResp;
import com.customer.system.req.LoginReq;
import com.customer.system.resp.LoginResp;
import com.customer.system.service.LoginService;
import com.customer.system.service.UserInfoService;
import com.customer.util.JWTTokenUtil;
import com.customer.util.RedisUtils;

/** 
* @author yangxy
* @version 创建时间：2024年6月1日 上午11:23:10 
*/
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public ApiResp<LoginResp> login(LoginReq loginReq) {
		// TODO Auto-generated method stub
		UserInfoEntity userInfoEntity = userInfoService.getOne(new QueryWrapper<UserInfoEntity>().lambda().eq(UserInfoEntity::getUserName, loginReq.getUserName()));
		if(ObjectUtils.isEmpty(userInfoEntity)) {
			throw new BusinessException("用户名或密码错误");
		}
		
		if(userInfoEntity.getStatus().intValue() == StatusEnums.UNENABLE.status) {
			throw new BusinessException("账号已停用");
		}
		
//		if(!bCryptPasswordEncoder.matches(loginReq.getPwd(), userInfoEntity.getPassword())) {
//			throw new BusinessException("用户名或密码错误");
//		}
		
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setUserId(userInfoEntity.getId());
		loginUserDto.setUserName(userInfoEntity.getUserName());
		
		String createAccessToken = JWTTokenUtil.createAccessToken(loginUserDto);
		LoginResp loginResp = new LoginResp();
		loginResp.setAccessToken(createAccessToken);
		redisUtils.set(RedisKeyEnums.LOGIN_KEY.key+createAccessToken, loginUserDto ,24*60*60);
		return ApiResp.sucess(loginResp);
	}

	@Override
	public ApiResp<String> logout(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
		String token = tokenHeader.replace(JWTConfig.tokenPrefix, "");
		redisUtils.del(RedisKeyEnums.LOGIN_KEY.key+token);
		return ApiResp.sucess();
	}

}
