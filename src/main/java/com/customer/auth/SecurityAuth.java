package com.customer.auth;

/** 
 * 自定义权限校验
* @author yangxy
* @version 创建时间：2023年7月26日 下午3:01:20 
*/
public class SecurityAuth {
	
	public boolean hasPermission(String permission) {
		return hasAnyPermissions(permission);
	}
	
	public boolean hasAnyPermissions(String... permissions) {
//		LoginDto loginUser = SecurityFrameworkUtils.getLoginUser();
//		if(ObjectUtils.isEmpty(loginUser)) {
//			throw new LoginException("Notloggedin");
//		}
//		if(loginUser.getIsAdmin() == 1) {//超级管理员不校验权限
//			return true;
//		}
//		List<String> authCods = SecurityFrameworkUtils.getLoginUser().getAuthCods();
//		if(ObjectUtils.isEmpty(authCods)) {
//			return false;
//		}
//		for(String permission:permissions) {
//			if(authCods.contains(permission)) {
//				return true;
//			}
//		}
		return false;
    }
	
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    public boolean hasAnyRoles(String... roles) {
//    	LoginDto loginUser = SecurityFrameworkUtils.getLoginUser();
//    	if(ObjectUtils.isEmpty(loginUser)) {
//			throw new LoginException("Notloggedin");
//		}
//		if(loginUser.getIsAdmin() == 0) {//超级管理员不校验权限
//			return true;
//		}
//    	List<String> roleCodes = SecurityFrameworkUtils.getLoginUser().getRoleCodes();
//    	if(ObjectUtils.isEmpty(roleCodes)) {
//			return false;
//		}
//		for(String role:roles) {
//			if(roleCodes.contains(role)) {
//				return true;
//			}
//		}
		return false;
    }
}
