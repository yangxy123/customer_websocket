package com.customer.system.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.customer.entity.system.UserInfoEntity;
import com.customer.exception.BusinessException;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;
import com.customer.system.mapper.UserInfoMapper;
import com.customer.system.req.EditPwdReq;
import com.customer.system.req.ResetPwdReq;
import com.customer.system.req.UserInfoAddReq;
import com.customer.system.req.UserInfoEditReq;
import com.customer.system.req.UserInfoPageReq;
import com.customer.system.service.UserInfoService;
import com.customer.util.SecurityFrameworkUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author yangxy
 * @version 创建时间：2024年5月30日 下午8:25:07
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public ApiResp<PageResp<UserInfoEntity>> page(UserInfoPageReq userInfoPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<UserInfoEntity> lambda = new QueryWrapper<UserInfoEntity>().lambda();

		if (!StringUtils.isEmpty(userInfoPageReq.getUserName())) {
			lambda.like(UserInfoEntity::getUserName, userInfoPageReq.getUserName());
		}

		if (!ObjectUtils.isEmpty(userInfoPageReq.getStatus())) {
			lambda.eq(UserInfoEntity::getStatus, userInfoPageReq.getStatus());
		}

		PageHelper.startPage(userInfoPageReq.getPageNum(), userInfoPageReq.getPageSize());
		Page<UserInfoEntity> page = (Page<UserInfoEntity>) list(lambda);
		return ApiResp.page(page);
	}

	@Override
	public ApiResp<String> add(UserInfoAddReq userInfoAddReq) {
		// TODO Auto-generated method stub
		UserInfoEntity one = getOne(new QueryWrapper<UserInfoEntity>().lambda().eq(UserInfoEntity::getUserName,
				userInfoAddReq.getUserName()));
		if (!ObjectUtils.isEmpty(one)) {
			throw new BusinessException("用户名已存在");
		}

		String password = bCryptPasswordEncoder.encode(userInfoAddReq.getPassword());
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		BeanUtils.copyProperties(userInfoAddReq, userInfoEntity);
		userInfoEntity.setPassword(password);
		save(userInfoEntity);
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> edit(UserInfoEditReq userInfoEditReq) {
		// TODO Auto-generated method stub
		UserInfoEntity one = getOne(new QueryWrapper<UserInfoEntity>().lambda()
				.eq(UserInfoEntity::getUserName, userInfoEditReq.getUserName())
				.ne(UserInfoEntity::getId, userInfoEditReq.getId()));
		if (!ObjectUtils.isEmpty(one)) {
			throw new BusinessException("用户名已存在");
		}
		UserInfoEntity userInfoEntity = getById(userInfoEditReq.getId());
		if (ObjectUtils.isEmpty(userInfoEntity)) {
			throw new BusinessException("数据不存在");
		}
		BeanUtils.copyProperties(userInfoEditReq, userInfoEntity);
		updateById(userInfoEntity);
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> del(Long userId) {
		// TODO Auto-generated method stub
		removeById(userId);
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> restPwd(ResetPwdReq resetPwdReq) {
		// TODO Auto-generated method stub
		Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
		UserInfoEntity userInfoEntity = getById(loginUserId);
		if (ObjectUtils.isEmpty(userInfoEntity)) {
			throw new BusinessException("数据不存在");
		}
		String password = bCryptPasswordEncoder.encode(resetPwdReq.getNewPwd());
		userInfoEntity.setPassword(password);
        updateById(userInfoEntity);
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> editPwd(EditPwdReq editPwdReq) {
		// TODO Auto-generated method stub
		Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
		UserInfoEntity userInfoEntity = getById(loginUserId);
		if (ObjectUtils.isEmpty(userInfoEntity)) {
			throw new BusinessException("数据不存在");
		}
		
		boolean matches = bCryptPasswordEncoder.matches(editPwdReq.getOldPwd(), userInfoEntity.getPassword());
        if(!matches){
            throw new BusinessException("旧密码错误");
        }
        
        String password = bCryptPasswordEncoder.encode(editPwdReq.getNewPwd());
        userInfoEntity.setPassword(password);
        updateById(userInfoEntity);
		return ApiResp.sucess();
	}

}
