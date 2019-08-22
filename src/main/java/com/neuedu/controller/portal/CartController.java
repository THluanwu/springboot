package com.neuedu.controller.portal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;

    @RequestMapping(value = "/add.do")
    public ServerResponse add (HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.add(productId,count,userInfo.getId());
    }

    @RequestMapping(value = "/list.do")
    public ServerResponse list (HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.list(userInfo.getId());
    }

    @RequestMapping(value = "/update.do")
    public ServerResponse update (HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.update(productId,count,userInfo.getId());
    }

    @RequestMapping(value = "/delete_product.do")
    public ServerResponse deleteProduct(HttpSession session,String productIds){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.deleteProduct(productIds,userInfo.getId());
    }

    @RequestMapping(value = "/select.do")
    public ServerResponse select (HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.select(productId,userInfo.getId(),Const.CartCheckEnum.CART_CHECKED.getCode());
    }

    @RequestMapping(value = "/un_select.do")
    public ServerResponse unSelect (HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.select(productId,userInfo.getId(),Const.CartCheckEnum.CART_UNCHECKED.getCode());
    }

    @RequestMapping(value = "/select_all.do")
    public ServerResponse selectAll (HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.select(null,userInfo.getId(),Const.CartCheckEnum.CART_CHECKED.getCode());
    }

    @RequestMapping(value = "/un_select_all.do")
    public ServerResponse unSelect (HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.select(null,userInfo.getId(),Const.CartCheckEnum.CART_UNCHECKED.getCode());
    }

    @RequestMapping(value = "/get_cart_product_count.do")
    public ServerResponse getCartProductCount (HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return cartService.getCartProductCount(userInfo.getId());
    }
}
