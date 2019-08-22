package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ICartService {

    ServerResponse add(Integer productId,Integer count,Integer userId);
    ServerResponse list(Integer userId);
    ServerResponse update(Integer productId,Integer count,Integer userId);
    ServerResponse deleteProduct(String productIds,Integer userId);
    ServerResponse select(Integer productId,Integer userId,Integer check);
    ServerResponse getCartProductCount(Integer userId);
}
