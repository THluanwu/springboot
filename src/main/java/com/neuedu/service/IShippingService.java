package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IShippingService {

    ServerResponse add(Shipping shipping,Integer userId);
    ServerResponse del(Integer shippingId,Integer userId);
    ServerResponse update(Shipping shipping,Integer userId);
    ServerResponse select(Integer shippingId,Integer userId);
    ServerResponse list(Integer pageNum,Integer pageSize,Integer userId);
}
