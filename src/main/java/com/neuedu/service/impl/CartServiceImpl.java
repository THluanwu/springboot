package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.utils.BigDecimalUtils;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public ServerResponse add(Integer productId, Integer count, Integer userId) {
        if (productId==null||count==null){
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        Cart cart=cartMapper.selectCartByUseridAndProductid(userId,productId);
        if (cart==null){
            Cart cart1=new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(Const.CartCheckEnum.CART_CHECKED.getCode());
            cartMapper.insert(cart1);
        }else {
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(cart.getQuantity()+count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);

        }
        CartVO cartVO=getCartVOLimit(userId);
        return ServerResponse.createServerResponseBySucess(cartVO);
    }

    @Override
    public ServerResponse list(Integer userId) {
        List<Cart>cartList= cartMapper.selectCarByUserid(userId);
        if (cartList==null||cartList.size()==0){
            return ServerResponse.createServerResponseByFail(9,"还没有选择商品");
        }
        CartVO cartVO=getCartVOLimit(userId);
        return ServerResponse.createServerResponseBySucess(cartVO);
    }

    @Override
    public ServerResponse update(Integer productId, Integer count, Integer userId) {

        Cart cart=cartMapper.selectCartByUseridAndProductid(userId,productId);
        if (cart!=null){
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }

        return ServerResponse.createServerResponseBySucess(getCartVOLimit(userId));
    }

    @Override
    public ServerResponse deleteProduct(String productIds, Integer userId) {
        if (productIds==null||productIds.equals("")){
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        List<Integer> productIdList=Lists.newArrayList();
        String[] productIdArr=productIds.split(",");
        if (productIdArr!=null&&productIdArr.length>0){
            for (String productIdstr:productIdArr){
                Integer productId=Integer.parseInt(productIdstr);
                productIdList.add(productId);
            }
        }
        cartMapper.deleteByUserIdAndProductIds(userId,productIdList);


        return ServerResponse.createServerResponseBySucess(getCartVOLimit(userId));
    }

    @Override
    public ServerResponse select(Integer productId, Integer userId,Integer check) {
//        if (productId==null){
//            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
//        }
        cartMapper.selectOrUnselectProduct(productId,userId,check);
        return ServerResponse.createServerResponseBySucess(getCartVOLimit(userId));
    }

    @Override
    public ServerResponse getCartProductCount(Integer userId) {

        int count=cartMapper.getCartProductCount(userId);
        return ServerResponse.createServerResponseBySucess(count);
    }

    private CartVO getCartVOLimit(Integer userId){
        CartVO cartVO=new CartVO();
        List<Cart> cartList=cartMapper.selectCarByUserid(userId);
        List<CartProductVO> cartProductVOList= Lists.newArrayList();
        BigDecimal carttotalprice=new BigDecimal(0);
        if (cartList!=null&&cartList.size()>0){
            for (Cart cart:cartList){
                CartProductVO cartProductVO=new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setQuantity(cart.getQuantity());
                cartProductVO.setUserId(cart.getUserId());
                Product product=productMapper.selectByPrimaryKey(cart.getProductId());
                if (product!=null){
                    cartProductVO.setProductId(cart.getProductId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());

                    int stock=product.getStock();
                    int limitProductCount=0;
                    if (stock>cart.getQuantity()){
                        limitProductCount=cart.getQuantity();
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                        cartProductVO.setProductChecked(cart.getChecked());
                    }else {//库存不足
                        limitProductCount=stock;
                        Cart cart1=new Cart();
                        cart1.setQuantity(stock);
                        cart1.setProductId(cart.getProductId());
                        //cart1.setChecked(cart.getChecked());
                        cart1.setUserId(userId);
                        cartMapper.updateByPrimaryKey(cart1);
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    }
                    cartProductVO.setQuantity(limitProductCount);
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),Double.valueOf(cartProductVO.getQuantity())));

                }
                if (cart.getChecked()==1) {
                    carttotalprice = BigDecimalUtils.add(carttotalprice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }
                cartProductVOList.add(cartProductVO);
            }
        }
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setCarttotalprice(carttotalprice);

        int count = cartMapper.isCheckAll(userId);
        if (count>0){
            cartVO.setIsallchecked(false);
        }else {
            cartVO.setIsallchecked(true);
        }

        return cartVO;
    }
}
