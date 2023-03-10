package sj.springcloud.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class MyBlockHandler {
    public static Object handler1(BlockException blockException){
        return "handler1 from MyBlockHandler";
    }

    public static Object handler2(BlockException blockException){
        return "handler2 from MyBlockHandler";
    }
}
