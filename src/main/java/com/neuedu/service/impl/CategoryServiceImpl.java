package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public ServerResponse getCategory(Integer categoryId) {
        if (categoryId==null){
            return ServerResponse.createServerResponseByFail(2,"参数不能为空");
        }

        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){
            return ServerResponse.createServerResponseByFail(1,"该类别不存在");
        }

        List<Category> categoryList=categoryMapper.selectChildCategory(categoryId);
        Collections.sort(categoryList, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                if (o1.getSortOrder()>o2.getSortOrder()){
                    return 1;
                }
                if (o1.getSortOrder()==o2.getSortOrder()){
                    return 0;
                }
                return -1;
            }
        });
        return ServerResponse.createServerResponseBySucess(categoryList);
    }

    @Override
    public ServerResponse getDeepCategory(Integer categoryId) {
        if (categoryId==null){
            return ServerResponse.createServerResponseByFail(100,"参数不能为空");
        }
        Set<Category> categorySet= Sets.newHashSet();
        categorySet=findAllChildCategory(categorySet,categoryId);
        Set<Integer> integerSet=Sets.newHashSet();
        Iterator<Category> categoryIterator=categorySet.iterator();
        while (categoryIterator.hasNext()){
            Category category=categoryIterator.next();
            integerSet.add(category.getId());
        }
        return ServerResponse.createServerResponseBySucess(integerSet);
    }

    private Set<Category> findAllChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if (category!=null){
            categorySet.add(category);
        }
        List<Category> categoryList=categoryMapper.selectChildCategory(categoryId);
        if (categoryList!=null&&categoryList.size()>0){
            for (Category categorynew:categoryList){
                findAllChildCategory(categorySet,categorynew.getId());
            }
        }
        return categorySet;
    }
}
