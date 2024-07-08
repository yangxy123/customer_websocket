package com.customer.handler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.customer.exception.BusinessException;
import com.customer.exception.LoginException;
import com.customer.exception.ParamException;
import com.customer.resp.ApiResp;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * 
 * @author 27669
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {
	@ResponseBody
	@SuppressWarnings("unchecked")
	@org.springframework.web.bind.annotation.ExceptionHandler({ MethodArgumentNotValidException.class })
	public ApiResp<String> validationExceptionHandler(MethodArgumentNotValidException e) {
		String msg = e.getBindingResult().getFieldError().getDefaultMessage();
		return ApiResp.paramError(msg);
	}

	@ResponseBody
	@SuppressWarnings("unchecked")
	@org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
	public ApiResp<String> exceptionHandler(Exception e) {
		log.error("", e);
		return ApiResp.sysError();
	}

	@ResponseBody
	@SuppressWarnings("unchecked")
	@org.springframework.web.bind.annotation.ExceptionHandler({ LoginException.class })
	public ApiResp<String> loginHandler(LoginException e) {
		return ApiResp.authError(e.getMessage());
	}

//	
//	@ResponseBody
//	@SuppressWarnings("unchecked")
//	@org.springframework.web.bind.annotation.ExceptionHandler({ AuthorityException.class })
//	public ApiResp<String> authHandler(AuthorityException e) {
//		return ApiResp.authError(e.getMsg());
//	}
//	
//	@ResponseBody
//	@SuppressWarnings("unchecked")
//	@org.springframework.web.bind.annotation.ExceptionHandler({ JWTExcepiton.class })
//	public ApiResp<String> authHandler(JWTExcepiton e) {
//		return ApiResp.jwtError(e.getMsg());
//	}
//	
//	@ResponseBody
//	@SuppressWarnings("unchecked")
//	@org.springframework.web.bind.annotation.ExceptionHandler({ AccessDeniedException.class })
//	public ApiResp<String> accessDeniedHandler(AccessDeniedException e) {
//		return ApiResp.authError("nopermission");
//	}
//	
	@ResponseBody
	@SuppressWarnings("unchecked")
	@org.springframework.web.bind.annotation.ExceptionHandler({ BusinessException.class })
	public ApiResp<String> accessDeniedHandler(BusinessException e) {
		return ApiResp.bussError(e.getMsg());
	}

//	
//	@ResponseBody
//	@org.springframework.web.bind.annotation.ExceptionHandler({ DecryptException.class })
//	public ApiResp<String> decryptExceptionHandler(DecryptException e) {
//		return ApiResp.paramError(e.getMessage());
//	}
//	
	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler({ ParamException.class })
	public ApiResp<String> paramExceptionHandler(ParamException e) {
		return ApiResp.paramError(e.getMessage());
	}
}
