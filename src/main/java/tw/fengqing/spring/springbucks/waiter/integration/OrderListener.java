package tw.fengqing.spring.springbucks.waiter.integration;

import tw.fengqing.spring.springbucks.waiter.model.CoffeeOrder;
import tw.fengqing.spring.springbucks.waiter.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class OrderListener {
    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private CoffeeOrderService orderService;

    @Bean
    public Consumer<Long> finishedOrders() {
        return id -> {
            log.info("We've finished an order [{}].", id);
            CoffeeOrder order = orderService.get(id);
            Message<Long> message = MessageBuilder.withPayload(id)
                    .setHeader("customer", order.getCustomer())
                    .build();
            log.info("Notify the customer: {}", order.getCustomer());
            streamBridge.send("notifyOrders-out-0", message);
        };
    }
}
