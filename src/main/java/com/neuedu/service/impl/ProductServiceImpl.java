package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtils;
import com.neuedu.vo.ProductDetailVO;
import com.neuedu.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryService categoryService;

    @Override
    public int addProduct(Product product) throws MyException {
        return productMapper.insert(product);
    }
    @Override
    public int deleteProduct(int productId) throws MyException {
        return productMapper.deleteByPrimaryKey(productId);
    }
    @Override
    public int updateProduct(Product product) throws MyException {
        return productMapper.updateByPrimaryKey(product);
    }
    @Override
    public List<Product> findall() throws MyException {
        return productMapper.selectAll();
    }
    @Override
    public ServerResponse detail_portal(Integer productId) {
        if (productId==null){
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByFail(4,"该商品不存在");
        }
        //校验商品状态
        if (product.getStatus()!= Const.productStatusEnum.PRODUCT_ONLINE.getCode()){
            return ServerResponse.createServerResponseByFail(4,"商品已下架或删除");
        }
        //获取productDetailVo
        ProductDetailVO productDetailVo=assembleProductDetailVO(product);
        //返回结果
        return ServerResponse.createServerResponseBySucess(productDetailVo);
    }
    private ProductDetailVO assembleProductDetailVO(Product product){
        ProductDetailVO productDetailVO=new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        //productDetailVO.setImageHost();
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getDetail());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category!=null){
            productDetailVO.setParentId(category.getParentId());
        }else {
            productDetailVO.setParentId(0);
        }
        return productDetailVO;
    }
    private  ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO=new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());

        return productListVO;
    }
    @Override
    public List<Category> findId() throws MyException {
        return productMapper.selectId();
    }
    @Override
    public ServerResponse searchAndList(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        if (categoryId==null&&(keyword==null||keyword.equals(""))){
            return ServerResponse.createServerResponseByFail(1,"参数不能为空");
        }
        Set<Integer>integerSet= Sets.newHashSet();
        if (categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null&&(keyword==null||keyword.equals(""))){
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVO> productListVOList= Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVOList);
                return ServerResponse.createServerResponseBySucess(pageInfo);
            }
            ServerResponse serverResponse=categoryService.getDeepCategory(categoryId);

            if (serverResponse.isSucess()){
                integerSet=(Set<Integer>)serverResponse.getData();
            }
        }
        if (keyword!=null&&!keyword.equals("")){
            keyword="%"+keyword+"%";
        }
        if (orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {
            String[] orderByArr=orderBy.split("_");
            if (orderByArr.length>0){
                PageHelper.startPage(pageNum,pageSize,orderByArr[0]+" "+orderByArr[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }

        List<Product> productList=productMapper.searchProduct(integerSet,keyword);
        List<ProductListVO> productListVOList=Lists.newArrayList();

        if (productList!=null&&productList.size()>0){
            for (Product product:productList){
                ProductListVO productListVO=assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVOList);
        //pageInfo.setList(productListVOList);
        return ServerResponse.createServerResponseBySucess(pageInfo);
    }

    /**
     * 商品后台接口
     * */
    @Override
    public ServerResponse backend_list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product>productList=productMapper.selectAll();
        List<ProductListVO>productListVOList=Lists.newArrayList();
        if (productList!=null&&productList.size()>0){
            for (Product product:productList){
                ProductListVO productListVO=assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVOList);
        return ServerResponse.createServerResponseBySucess(pageInfo);
    }

    @Override
    public ServerResponse backend_search(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if (productName!=null&&!productName.equals("")){
            productName="%"+productName+"%";
        }
        List<Product>productList=productMapper.findProduct(productId,productName);
        List<ProductListVO>productListVOList=Lists.newArrayList();
        if (productList!=null&&productList.size()>0){
            for (Product product:productList){
                ProductListVO productListVO=assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVOList);
        return ServerResponse.createServerResponseBySucess(pageInfo);
    }
}
