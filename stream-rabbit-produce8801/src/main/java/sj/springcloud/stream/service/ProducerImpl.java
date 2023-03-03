package sj.springcloud.stream.service;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;//注意导入的source包路径
import org.springframework.messaging.MessageChannel;//注意导入的MessageBuilder包路径
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;


//消息生产管道
@EnableBinding(Source.class)
public class ProducerImpl implements Producer {

    //消息输出管道
    @Resource
    private MessageChannel output;

    @Override
    public boolean send(Object msg) {
        return output.send(MessageBuilder.withPayload(msg).build());
    }
}
