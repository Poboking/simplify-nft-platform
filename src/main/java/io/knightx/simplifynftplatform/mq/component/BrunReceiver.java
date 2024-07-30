package io.knightx.simplifynftplatform.mq.component;

import io.knightx.simplifynftplatform.dto.collection.CollectionBrunDto;
import io.knightx.simplifynftplatform.service.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午3:14
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.brun}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange.direct}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routingKey.brun}"
        )
)
@Component
public class BrunReceiver {
    private final Logger log = LoggerFactory.getLogger(BrunReceiver.class);
    private final CollectionService service;

    @Autowired
    public BrunReceiver(CollectionService service) {
        this.service = service;
    }

    @RabbitHandler
    public void process(String json) {
        CollectionBrunDto dto = CollectionBrunDto.Companion.fromJson(json);
        String tokenId = dto.getTokenId();
        long collectionId = service.getCollectionIdByTokenId(tokenId);
        boolean bool = service.deleteCollection(collectionId);
        if (bool) {
            log.info("BrunReceiver: Burn Succeed {}", dto.toJson());
        } else {
            log.warn("BrunReceiver: Burn Failed {}", dto.toJson());
        }
    }
}
