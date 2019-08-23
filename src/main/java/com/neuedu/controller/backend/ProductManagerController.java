package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manager/product")
public class ProductManagerController {

    @Autowired
    IProductService productService;


    @RequestMapping(value = "/list.do")
    public ServerResponse list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize){
                return productService.backend_list(pageNum,pageSize);
    }

    @RequestMapping(value = "/search.do")
    public ServerResponse search(@RequestParam(required = false) Integer productId,
                                 @RequestParam(required = false) String productName,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        return productService.backend_search(productId,productName,pageNum,pageSize);
    }


}
