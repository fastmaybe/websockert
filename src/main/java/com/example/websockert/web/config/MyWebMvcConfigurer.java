package com.example.websockert.web.config;

import com.example.websockert.web.interceptor.UserAuthInteceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Author: liulang
 * @Date: 2020/8/26 15:52
 */
@Configuration
@EnableWebMvc
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 视图控制器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/chatroom").setViewName("chatroom");

    }



    @Bean
    public InternalResourceViewResolver resourceViewResolver()
    {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("/resources/");
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }


    /**
     * 视图解析
     * @param registry
     */

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(resourceViewResolver());

    }

    /**\
     * 资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources","/static","classpath:/static/","classpath:/resources/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/resources","/static","classpath:/static/","classpath:/resources/");
    }


    @Bean
    UserAuthInteceptor userAuthInteceptor() {
        return new UserAuthInteceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInteceptor())
                .addPathPatterns("/chatroom/**");
    }
}
