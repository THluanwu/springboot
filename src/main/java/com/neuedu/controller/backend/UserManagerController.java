package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manager/user")
public class UserManagerController {

    @Autowired
    IUserService userService;


    @RequestMapping(value = "/manager_login.do")
    public ServerResponse managerLogin(HttpSession session, String username, String password){
        ServerResponse serverResponse=userService.manager_Login(username,password);
        if (serverResponse.isSucess()){//登录成功
            UserInfo userInfo= (UserInfo) serverResponse.getData();
            session.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return serverResponse;
    }

}
