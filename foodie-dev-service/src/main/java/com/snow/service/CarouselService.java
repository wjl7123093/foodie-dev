package com.snow.service;

import com.snow.pojo.Carousel;

import java.util.List;

/**
 * 轮播图接口
 */
public interface CarouselService {

    /**
     * 查询所有轮播图
     * @param isShow 是否可见 1 可见，0 隐藏
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);

}
