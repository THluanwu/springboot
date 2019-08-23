package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Order;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    ServerResponse create(Integer shippingId,Integer userId);
    ServerResponse cancel(Long orderNO,Integer userId);
    ServerResponse getOrderCartProduct (Integer userId);
    ServerResponse list(Integer pageNum,Integer pageSize,Integer userId);
    ServerResponse detail(Long orderNo,Integer userId);
    ServerResponse pay(Long orderNo,Integer userId);
    ServerResponse alipay_callback(Map<String,String> map);
    ServerResponse query_order_pay_status(Long orderNo,Integer userId);
    List<Order> closeOrder(String closeOrderDate);
    ServerResponse backend_list(Integer pageNum,Integer pageSize);
    ServerResponse backend_detail(Long orderNo);
}
