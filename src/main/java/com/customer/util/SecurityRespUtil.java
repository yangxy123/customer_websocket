package com.customer.util;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSON;
import com.customer.resp.ApiResp;

import lombok.extern.slf4j.Slf4j;

/**
 * 返回结果工具类
 * @Author Sans
 * @CreateTime 2019/9/28 10:50
 */
@Slf4j
public class SecurityRespUtil {

    /**
     * 私有化构造器
     */
    private SecurityRespUtil(){}

    /**
     * 使用response输出JSON
    * @author yangxy
    * @version 创建时间：2023年7月26日 上午11:47:58 
    * @param response
    * @param apiDto 
     */
    public static void responseJson(ServletResponse response, ApiResp<String> apiDto){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(apiDto));
        } catch (Exception e) {
            log.error("【JSON输出异常】"+e);
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }

}