package sj.springcloud.stream.service;

public interface Producer {
    boolean send(Object msg);
}
