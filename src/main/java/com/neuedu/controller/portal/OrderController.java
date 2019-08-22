package com.neuedu.controller.portal;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;


    @RequestMapping(value = "/create.do")
    public ServerResponse create(HttpSession session,Integer shippingId){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.create(shippingId,userInfo.getId());
    }

    @RequestMapping(value = "/cancel.do")
    public ServerResponse cancel(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.cancel(orderNo,userInfo.getId());
    }

    @RequestMapping(value = "/get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpSession session ){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.getOrderCartProduct(userInfo.getId());
    }

    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.list(pageNum,pageSize,userInfo.getId());
    }

    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.detail(orderNo,userInfo.getId());
    }

    @RequestMapping(value = "/pay.do")
    public ServerResponse pay(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.pay(orderNo,userInfo.getId());
    }

    /**
     * 支付宝回调接口
     * */
    @RequestMapping(value = "/alipay_callback.do")
    public ServerResponse callback(HttpServletRequest request){
        System.out.println("===============支付宝回调接口==================");
        Map<String,String[]>params=request.getParameterMap();
        Map<String,String>requestparams= Maps.newHashMap();
        Iterator<String> it=params.keySet().iterator();
        while (it.hasNext()){
            String key=it.next();
            String[]strArr=params.get(key);
            String value="";
            for (int i=0;i<strArr.length;i++){
                value=(i==strArr.length-1)?value+strArr[i]:value+strArr[i]+",";
            }
            requestparams.put(key,value);
        }

        //1.支付宝验签
        try {
            requestparams.remove("sign_type");
           boolean result= AlipaySignature.rsaCheckV2(requestparams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if (!result){
                return ServerResponse.createServerResponseByFail(7,"非法请求");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderService.alipay_callback(requestparams);
    }

    @RequestMapping(value = "/query_order_pay_status.do")
    public ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(10,"请登录");
        }
        return orderService.query_order_pay_status(orderNo,userInfo.getId());
    }


}
