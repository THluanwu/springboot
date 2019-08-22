package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manager/category")
public class CategoryManagerController {

    @Autowired
    ICategoryService categoryService;

    @RequestMapping(value = "/get_category.do")
    public ServerResponse getCategory(HttpSession session,Integer categoryId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"用户未登陆，请登录");
        }
        return categoryService.getCategory(categoryId);
    }

    @RequestMapping(value = "/get_deep_category.do")
    public ServerResponse getDeepCategory(HttpSession session,Integer categoryId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"用户未登陆，请登录");
        }
        return categoryService.getDeepCategory(categoryId);
    }
}
