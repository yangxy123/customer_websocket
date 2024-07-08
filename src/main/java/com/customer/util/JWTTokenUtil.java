package com.customer.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.customer.config.JWTConfig;
import com.customer.dto.LoginUserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT工具类
 * @Author Sans
 * @CreateTime 2019/10/2 7:42
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成Token
     * @Author Sans
     * @CreateTime 2019/10/2 12:16
     * @Param  selfUserEntity 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(LoginUserDto loginUser){
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(loginUser.getUserId().toString())
                // 主题
                .setSubject(JSON.toJSONString(loginUser))
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("gw")
                // 自定义属性 放入用户拥有权限
//                .claim("authorities", JSON.toJSONString(loginUser.getAuthCods()))
//                .claim("roles", JSON.toJSONString(loginUser.getRoleCodes()))
                // 失效时间
//                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
}