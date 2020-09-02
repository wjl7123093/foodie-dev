package com.snow.config;

import com.snow.service.OrderService;
import com.snow.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务 - 订单
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * 使用定时任务关闭超期未支付订单，会存在以下弊端：
     * 1. 会有时间差，程序不严谨
     *      10:39下单，11:00 检查不足 1小时，12:00 检查超过 1小时多余21分钟
     * 2. 不支持集群
     *      单机没毛病，使用集群后，就会有多个定时任务
     * 3. 会对数据库全表搜索，极其影响数据库性能：select * from xxx
     *
     * 定时任务仅仅适用于小型轻量级项目，传统项目等
     *
     * 后续课程会涉及到消息队列：MQ -> RabbitMQ, RocketMQ, Kafka, ZeroMQ...
     *      延时任务（队列）：
     *      10:12 下单，未付款（10）状态，11:12 检查，如果还未支付，则直接关闭订单
     */

//    @Scheduled(cron = "0/3 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        orderService.closeOrder();

        System.out.println("执行定时任务, 当前时间为："
                + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }

}
