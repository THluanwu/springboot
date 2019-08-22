package com.neuedu.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;


    @Override
    public ServerResponse add(Shipping shipping, Integer userId) {
        if ((shipping.getReceiverName()==null||shipping.getReceiverName().equals(""))||(shipping.getReceiverPhone()==null||shipping.getReceiverPhone().equals(""))||(shipping.getReceiverMobile()==null||shipping.getReceiverMobile().equals(""))||(shipping.getReceiverProvince()==null||shipping.getReceiverProvince().equals(""))||(shipping.getReceiverCity()==null||shipping.getReceiverCity().equals(""))||(shipping.getReceiverDistrict()==null||shipping.getReceiverDistrict().equals(""))||(shipping.getReceiverAddress()==null||shipping.getReceiverAddress().equals(""))||(shipping.getReceiverZip()==null||shipping.getReceiverZip().equals(""))){
            return ServerResponse.createServerResponseByFail(2,"参数不能为空");
        }
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);
        Map<String,Integer> map= Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.createServerResponseBySucess(map);
    }

    @Override
    public ServerResponse del(Integer shippingId, Integer userId) {
        if (shippingId==null){
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }

        int result=shippingMapper.delByUserIdandShippingId(shippingId,userId);
        if (result>0){
            return ServerResponse.createServerResponseBySucess("删除成功");
        }
        return ServerResponse.createServerResponseByFail(10,"删除失败");
    }

    @Override
    public ServerResponse update(Shipping shipping,Integer userInfo) {
        if ((shipping.getReceiverName()==null||shipping.getReceiverName().equals(""))||(shipping.getReceiverPhone()==null||shipping.getReceiverPhone().equals(""))||(shipping.getReceiverMobile()==null||shipping.getReceiverMobile().equals(""))||(shipping.getReceiverProvince()==null||shipping.getReceiverProvince().equals(""))||(shipping.getReceiverCity()==null||shipping.getReceiverCity().equals(""))||(shipping.getReceiverDistrict()==null||shipping.getReceiverDistrict().equals(""))||(shipping.getReceiverAddress()==null||shipping.getReceiverAddress().equals(""))||(shipping.getReceiverZip()==null||shipping.getReceiverZip().equals(""))){
            return ServerResponse.createServerResponseByFail(2,"参数不能为空");
        }
        int result=shippingMapper.updateBySelectKey(shipping,userInfo);
        if (result>0){
            return ServerResponse.createServerResponseBySucess("成功");
        }
        return ServerResponse.createServerResponseByFail(1,"失败");
    }

    @Override
    public ServerResponse select(Integer shippingId, Integer userId) {
        if (shippingId==null){
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        Shipping shipping=shippingMapper.selectByPrimaryKey(shippingId);
        return ServerResponse.createServerResponseBySucess(shipping);
    }

    @Override
    public ServerResponse list(Integer pageNum,Integer pageSize,Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList=shippingMapper.selectAllShipping(userId);
        PageInfo pageInfo=new PageInfo(shippingList);
        return ServerResponse.createServerResponseBySucess(pageInfo);
    }
}
