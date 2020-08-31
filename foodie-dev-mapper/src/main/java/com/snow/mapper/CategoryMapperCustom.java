package com.snow.mapper;

import com.snow.my.mapper.MyMapper;
import com.snow.pojo.Category;
import com.snow.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);

    public List getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);

}