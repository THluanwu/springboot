package com.neuedu.dao;

import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    int insert(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    Product selectByPrimaryKey(@Param("productId") Integer productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    List<Product> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(@Param("product") Product record);
    List<Category> selectId();
    List<Product> searchProduct(@Param("integerSet") Set<Integer> integerSet,
                                @Param("keyword") String keyword
                                );
}