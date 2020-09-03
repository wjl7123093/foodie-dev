package com.snow.service.impl;

import com.github.pagehelper.PageInfo;
import com.snow.utils.PagedGridResult;

import java.util.List;

/**
 * xxxServiceImpl 基类。放置一些公共方法
 */
public abstract class BaseServiceImpl {



    /**
     * 封装分页数据返回给前端
     * @param list
     * @param page
     * @return
     */
    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

}
