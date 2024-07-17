package com.customer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.customer.**.mapper")
public class CustomerApplication 
{
	public static void main( String[] args )
    {
    	SpringApplication.run(CustomerApplication.class, args);
    	log.info("\n----------------------------------------------------------\n\t" +
                "客户系统启动成功123! "+
                "----------------------------------------------------------");
    }
}
