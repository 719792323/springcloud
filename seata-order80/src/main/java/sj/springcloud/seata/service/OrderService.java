package sj.springcloud.seata.service;


import sj.springcloud.seata.entity.Order;

public interface OrderService {
    // 创建订单
    void create(Order order);
}