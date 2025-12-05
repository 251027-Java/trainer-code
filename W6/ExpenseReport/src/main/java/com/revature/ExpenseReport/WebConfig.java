package com.revature.ExpenseReport;

import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    //Fields
    private BasicAuthInterceptor basicAuthInterceptor;

    //Constructor
    public WebConfig(BasicAuthInterceptor bai) {
        this.basicAuthInterceptor = bai;
    }

    //Method
    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(basicAuthInterceptor)
                .addPathPatters("/api/**")
                .excludePathPatterns("/api.hello");
    }
}
