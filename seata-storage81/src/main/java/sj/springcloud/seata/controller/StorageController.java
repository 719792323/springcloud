package sj.springcloud.seata.controller;

import entities.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sj.springcloud.seata.service.StorageService;

import javax.annotation.Resource;

@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    //RequestMapping默认GET POST请求都支持，根据前端自动适应
    @RequestMapping(value = "/storage/decrease")
    public CommonResult decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult(200, "扣减库存成功");
    }
}