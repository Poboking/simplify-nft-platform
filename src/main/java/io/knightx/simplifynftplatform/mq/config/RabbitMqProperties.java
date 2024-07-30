package io.knightx.simplifynftplatform.mq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午2:02
 */
@Configuration("rabbitMqProperties")
@ConfigurationProperties(prefix = "mq")
public class RabbitMqProperties {
    @Value("${mq.config.exchange.direct}")
    private String directExchange;
    @Value("${mq.config.exchange.fanout}")
    private String fanoutExchange;
    @Value("${mq.config.exchange.topic}")
    private String topicExchange;
    @Value("${mq.config.queue.mint}")
    private String mintQueue;
    @Value("${mq.config.queue.brun}")
    private String brunQueue;
    @Value("${mq.config.queue.transfer}")
    private String transferQueue;
    @Value("${mq.config.queue.soldOut}")
    private String soldOutQueue;
    @Value("${mq.config.queue.register}")
    private String registerQueue;
    @Value("${mq.config.routingKey.mint}")
    private String mintKey;
    @Value("${mq.config.routingKey.brun}")
    private String brunKey;
    @Value("${mq.config.routingKey.transfer}")
    private String transferKey;
    @Value("${mq.config.routingKey.soldOut}")
    private String soldOutKey;
    @Value("${mq.config.routingKey.register}")
    private String registerKey;

    public String getDirectExchange() {
        return directExchange;
    }

    public String getFanoutExchange() {
        return fanoutExchange;
    }

    public String getTopicExchange() {
        return topicExchange;
    }

    public String getMintQueue() {
        return mintQueue;
    }

    public String getBrunQueue() {
        return brunQueue;
    }

    public String getTransferQueue() {
        return transferQueue;
    }

    public String getSoldOutQueue() {
        return soldOutQueue;
    }

    public String getMintKey() {
        return mintKey;
    }

    public String getBrunKey() {
        return brunKey;
    }

    public String getTransferKey() {
        return transferKey;
    }

    public String getSoldOutKey() {
        return soldOutKey;
    }

    public String getRegisterQueue() {
        return registerQueue;
    }

    public String getRegisterKey() {
        return registerKey;
    }

    public void setDirectExchange(String directExchange) {
        this.directExchange = directExchange;
    }

    public void setFanoutExchange(String fanoutExchange) {
        this.fanoutExchange = fanoutExchange;
    }

    public void setTopicExchange(String topicExchange) {
        this.topicExchange = topicExchange;
    }

    public void setMintQueue(String mintQueue) {
        this.mintQueue = mintQueue;
    }

    public void setBrunQueue(String brunQueue) {
        this.brunQueue = brunQueue;
    }

    public void setTransferQueue(String transferQueue) {
        this.transferQueue = transferQueue;
    }

    public void setSoldOutQueue(String soldOutQueue) {
        this.soldOutQueue = soldOutQueue;
    }

    public void setMintKey(String mintKey) {
        this.mintKey = mintKey;
    }

    public void setBrunKey(String brunKey) {
        this.brunKey = brunKey;
    }

    public void setTransferKey(String transferKey) {
        this.transferKey = transferKey;
    }

    public void setSoldOutKey(String soldOutKey) {
        this.soldOutKey = soldOutKey;
    }

    public void setRegisterQueue(String registerQueue) {
        this.registerQueue = registerQueue;
    }

    public void setRegisterKey(String registerKey) {
        this.registerKey = registerKey;
    }
}
