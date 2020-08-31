package com.snow.mapper;

import com.snow.my.mapper.MyMapper;
import com.snow.pojo.Items;
import com.snow.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

}