package com.snow.service.impl;

import com.snow.enums.YesOrNo;
import com.snow.mapper.CarouselMapper;
import com.snow.pojo.Carousel;
import com.snow.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    public CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {

        Example carouselExample = new Example(Carousel.class);
        carouselExample.orderBy("sort").desc();  // 降序排列（默认升序）
        Example.Criteria carouselCriteria = carouselExample.createCriteria();
        carouselCriteria.andEqualTo("isShow", isShow);

        List<Carousel> result = carouselMapper.selectByExample(carouselExample);

        return result;
    }
}
