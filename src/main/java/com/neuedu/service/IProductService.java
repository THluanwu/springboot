package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface IProductService {
    /**
     * 增加商品
     * */
    public int addProduct(Product product) throws MyException;

    /**
     * 删除商品
     * */
    public int deleteProduct(int productId)throws MyException;
    /**
     * 修改商品
     * */
    public int updateProduct(Product product)throws MyException;
    /**
     * 查询商品
     * */
    public List<Product> findall()throws MyException;

    ServerResponse detail_portal(Integer productId);
    public List<Category> findId()throws MyException;
     ServerResponse searchAndList(Integer categoryId,
                                        String keyword,
                                        Integer pageNum,
                                        Integer pageSize,
                                        String orderBy);
}
