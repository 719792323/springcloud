package sj.springcloud.seata.service;

import entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//通过OpenFeign远程调用库存的微服务
@FeignClient(value = "seata-storage-service")
public interface StorageService {

    //扣减库存，比如买了5个1号商品：对1号商品库存减5
    @PostMapping(value = "/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}