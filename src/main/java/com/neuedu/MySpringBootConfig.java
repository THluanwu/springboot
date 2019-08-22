package com.neuedu;

import com.google.common.collect.Lists;
import com.neuedu.interceptors.AdminAuthroityInterceptor;
import com.neuedu.interceptors.PortalAuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootConfiguration
public class MySpringBootConfig implements WebMvcConfigurer {



    @Autowired
    AdminAuthroityInterceptor adminAuthroityInterceptor;

    @Autowired
    PortalAuthorityInterceptor portalAuthorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(adminAuthroityInterceptor)
//                .addPathPatterns("/manage/**");


        List<String> addPatterns= Lists.newArrayList();
        addPatterns.add("/user/**");
        addPatterns.add("/cart/**");
        addPatterns.add("/order/");
        addPatterns.add("/shipping/");

        List<String> excludePathPatterns=Lists.newArrayList();
        excludePathPatterns.add("/user/login.do");
        excludePathPatterns.add("/user/register.do");
        excludePathPatterns.add("/user/check_valid.do");
        excludePathPatterns.add("/user/forget_get_question.do");
        excludePathPatterns.add("/user/forget_check_answer.do");
        excludePathPatterns.add("/user/forget_reset_password.do");
        excludePathPatterns.add("/user/logout.do");

        excludePathPatterns.add("/order/alipay_callback.do");

        registry.addInterceptor(portalAuthorityInterceptor)
                .addPathPatterns(addPatterns)
                .excludePathPatterns(excludePathPatterns);
    }
}
