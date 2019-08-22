package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.config.AppConfig;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    IUserService userService;
//    @Value("${jdbc.driver}")
//    private String driver;
//    @Value("${jdbc.username}")
//    private String username;
//    @Value("${jabc.passwod}")
//    private String password;
    @Autowired
    AppConfig appConfig;

    @RequestMapping("/test")
    public String getDriver(){
        return appConfig.getPassword()+"";
    }

    @RequestMapping("/res")
    public ServerResponse res(){
        return ServerResponse.createServerResponseBySucess();
    }
}
