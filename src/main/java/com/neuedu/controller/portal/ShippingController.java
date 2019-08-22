package com.neuedu.controller.portal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IShippingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {

    @Autowired
    IShippingService shippingService;

    @RequestMapping(value = "/add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return shippingService.add(shipping,userInfo.getId());
    }

    @RequestMapping(value = "/del.do")
    public ServerResponse del(HttpSession session, Integer shippingId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return shippingService.del(shippingId,userInfo.getId());
    }

    @RequestMapping(value = "/update.do")
    public ServerResponse update(HttpSession session, Shipping shipping){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return shippingService.update(shipping,userInfo.getId());
    }

    @RequestMapping(value = "/select.do")
    public ServerResponse select(HttpSession session, Integer shippingId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return shippingService.select(shippingId,userInfo.getId());
    }

    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return shippingService.list(pageNum,pageSize,userInfo.getId());
    }
}
