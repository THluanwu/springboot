package com.neuedu.controller.backend;


import com.neuedu.common.ServerResponse;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/manager/order")
public class OrderManagerController {

    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "/list.do")
    public ServerResponse list (@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        return orderService.backend_list(pageNum,pageSize);
    }
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(Long orderNo){
        return orderService.backend_detail(orderNo);
    }
}
