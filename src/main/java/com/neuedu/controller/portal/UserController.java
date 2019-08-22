package com.neuedu.controller.portal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpSession session,String username,String password){
        ServerResponse serverResponse=userService.login(username,password);
        if (serverResponse.isSucess()){//登录成功
            UserInfo userInfo= (UserInfo) serverResponse.getData();
            session.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return serverResponse;
    }

    @RequestMapping(value = "/register.do")
    public ServerResponse register(UserInfo userInfo){
        ServerResponse serverResponse=userService.register(userInfo);
        return serverResponse;
    }

    @RequestMapping(value = "/check_valid.do")
    public ServerResponse checkValid(String str,String type){
        ServerResponse serverResponse=userService.checkValid(str,type);
        return serverResponse;
    }


    @RequestMapping(value = "/get_user_info.do")
    public ServerResponse getUserInfo(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(1,"用户未登陆");
        }
        UserInfo newuser=new UserInfo();
        newuser.setId(userInfo.getId());
        newuser.setUsername(userInfo.getUsername());
        newuser.setEmail(userInfo.getEmail());
        newuser.setCreateTime(userInfo.getCreateTime());
        newuser.setUpdateTime(userInfo.getUpdateTime());
        return ServerResponse.createServerResponseBySucess(newuser);
    }


    @RequestMapping(value = "/forget_get_question.do")
    public ServerResponse forgetGetQuestion(String username){
        ServerResponse serverResponse=userService.forgetGetQuestion(username);
        return serverResponse;
    }


    @RequestMapping(value = "/forget_check_answer.do")
    public ServerResponse forgetCheckAnswer(String username, String question, String answer){
        ServerResponse serverResponse=userService.forgetCheckAnswer(username,question,answer);
        return serverResponse;
    }


    @RequestMapping(value = "/forget_reset_password.do")
    public ServerResponse forgetResetPassword(String username,String passwordNew, String forgetToken){
        ServerResponse serverResponse=userService.forgetResetPassword(username,passwordNew,forgetToken);
        return serverResponse;
    }


    @RequestMapping(value = "/reset_password.do")
    public ServerResponse ResetPassword(String passwordOld, String passwordNew,HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);

        ServerResponse serverResponse=userService.ResetPassword(passwordOld,passwordNew,userInfo.getUsername());
        return serverResponse;
    }


    @RequestMapping(value = "/update_information.do")
    public ServerResponse updateInformation(HttpSession session,UserInfo user){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(1,"用户未登陆");
        }
        user.setId(userInfo.getId());
        ServerResponse serverResponse=userService.updateInformation(user);
        if (serverResponse.isSucess()){
            UserInfo newuser=userService.findUserById(userInfo.getId());
            session.setAttribute(Const.CURRENTUSER,newuser);
        }
        return serverResponse;
    }


    @RequestMapping(value = "/get_information.do")
    public ServerResponse getInformation(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(1,"用户未登陆");
        }
        userInfo.setPassword(null);
        return ServerResponse.createServerResponseBySucess(userInfo);
    }


    @RequestMapping(value = "/logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createServerResponseBySucess("退出成功",null);

    }


}
