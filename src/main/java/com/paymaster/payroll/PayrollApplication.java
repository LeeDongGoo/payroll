package com.paymaster.payroll;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.paymaster.payroll.mapper")
public class PayrollApplication extends SpringBootServletInitializer{
    public static void main( String[] args ) {
    	SpringApplication.run(PayrollApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	return builder.sources(PayrollApplication.class);
    }
    
}
