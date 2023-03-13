package sj.springcloud.seata.controller;

import entities.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sj.springcloud.seata.entity.Order;
import sj.springcloud.seata.service.OrderService;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    //创建订单
    @GetMapping(value = "/order/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult(200, "订单创建成功!");
    }
}