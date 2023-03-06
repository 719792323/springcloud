package stream.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class Consumer {

    @Value("${server.port}")
    private Integer port;

    //这个方法是自动触发
    @StreamListener(Sink.INPUT)
    public void consumer(Message<String> message) {
        System.out.println(String.format(
                "port:{%s},consume:{%s}", port, message.getPayload()
        ));
    }
}
