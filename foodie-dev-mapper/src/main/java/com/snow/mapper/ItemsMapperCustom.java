package com.snow.mapper;

import com.snow.my.mapper.MyMapper;
import com.snow.pojo.Items;
import com.snow.pojo.vo.ItemCommentVO;
import com.snow.pojo.vo.SearchItemVO;
import com.snow.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemVO > searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemVO > searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    public int decreaseItemSpecStock(@Param("specId") String specId, @Param("pendingCounts") int pendingCounts);

}