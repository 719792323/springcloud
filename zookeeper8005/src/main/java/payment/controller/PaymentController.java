package payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private Integer port;
    private Random random = new Random();

    @GetMapping(value = "/zk/payment")
    public String payment() {
        return "from port:" + port + " you should pay :" + random.nextInt();
    }
}
