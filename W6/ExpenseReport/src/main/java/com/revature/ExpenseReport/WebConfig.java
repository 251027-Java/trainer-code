package com.revature.ExpenseReport;

<<<<<<< HEAD
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    //Fields
    private BasicAuthInterceptor basicAuthInterceptor;

    //Constructor
=======
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Fields
    private final BasicAuthInterceptor basicAuthInterceptor;

    // Constructor
>>>>>>> origin
    public WebConfig(BasicAuthInterceptor bai) {
        this.basicAuthInterceptor = bai;
    }

<<<<<<< HEAD
    //Method
    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(basicAuthInterceptor)
                .addPathPatters("/api/**")
                .excludePathPatterns("/api.hello");
=======
    // Method
    @Override
    public void addInterceptors(InterceptorRegistry reg) {
        // adding interceptors to the list of active/running interceptors
        // that are scanning requests as they come in
        reg.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/hello");
>>>>>>> origin
    }
}
