package io.knightx.simplifynftplatform.mq.component;

import io.knightx.simplifynftplatform.dto.collection.CollectionSoldOutDto;
import io.knightx.simplifynftplatform.service.CollectionService;
import io.knightx.simplifynftplatform.service.HoldCollectionService;
import io.knightx.simplifynftplatform.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午3:23
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.soldOut}",autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange.direct}",type = ExchangeTypes.DIRECT),
                key = "${mq.config.routingKey.soldOut}"
        )
)
@Component
public class SoldOutReceiver {
    
    private final Logger log = LoggerFactory.getLogger(SoldOutReceiver.class);
    private final CollectionService collection;
    private final MemberService member;
    
    private final HoldCollectionService holdCollection;

    @Autowired
    public SoldOutReceiver(CollectionService collectionService, MemberService memberService, HoldCollectionService holdCollection) {
        this.collection = collectionService;
        this.member = memberService;
        this.holdCollection = holdCollection;
    }
    
    @RabbitHandler
    public void process(String json){
        CollectionSoldOutDto dto = CollectionSoldOutDto.Companion.fromJson(json);
        long collectionId = collection.getCollectionIdByTokenId(dto.getTokenId());
        long memberId = member.getMemberIdByMemberName(dto.getUserName());
        boolean holdBool = holdCollection.soldOutCollection(dto.getHoldCollectionId(), collectionId, memberId);
        boolean collectionBool = collection.hideCollection(collectionId);
        if (holdBool && collectionBool) {
            log.info("SoldOutReceiver: SoldOut Succeed {}", dto.toJson());
        }else {
            log.warn("SoldOutReceiver: SoldOut Failed {}", dto.toJson());
        }
    }
}
