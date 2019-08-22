package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ICategoryService {

    ServerResponse getCategory(Integer categoryId);
    ServerResponse getDeepCategory(Integer categoryId);
}
