package io.knightx.simplifynftplatform.mq.component;

import io.knightx.simplifynftplatform.mq.config.RabbitMqProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/20 下午2:18
 */
@Component
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitSender {

    private final RabbitMqProperties properties;
    private final RabbitTemplate rabbit;

    @Autowired
    public RabbitSender(@Qualifier("rabbitMqProperties") RabbitMqProperties rabbitMqProperties, RabbitTemplate rabbit) {
        this.properties = rabbitMqProperties;
        this.rabbit = rabbit;
    }


    public void sendMint(String json) {
        this.rabbit.convertAndSend(properties.getDirectExchange(), properties.getMintKey(), json);
    }

    public void sendBrun(String json) {
        this.rabbit.convertAndSend(properties.getDirectExchange(), properties.getBrunKey(), json);
    }

    public void sendTransfer(String json) {
        this.rabbit.convertAndSend(properties.getDirectExchange(), properties.getTransferKey(), json);
    }

    public void sendSoldOut(String json) {
        this.rabbit.convertAndSend(properties.getDirectExchange(), properties.getSoldOutKey(), json);
    }

    public void sendRegister(String json) {
        this.rabbit.convertAndSend(properties.getDirectExchange(), properties.getRegisterKey(), json);
    }
}
