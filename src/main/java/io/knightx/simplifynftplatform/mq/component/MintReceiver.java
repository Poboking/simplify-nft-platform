package io.knightx.simplifynftplatform.mq.component;

import io.knightx.simplifynftplatform.dto.collection.CollectionMintDto;
import io.knightx.simplifynftplatform.dto.hold.HoldCollectionCreateReqDto;
import io.knightx.simplifynftplatform.service.CollectionService;
import io.knightx.simplifynftplatform.service.HoldCollectionService;
import io.knightx.simplifynftplatform.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.knightx.simplifynftplatform.common.NFTConstKt.USER_CREATE_COLLECTION_APPLY_RECORD_ID;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午2:57
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.mint}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange.direct}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routingKey.mint}"
        )
)
@Component
public class MintReceiver {
    private final Logger log = LoggerFactory.getLogger(MintReceiver.class);

    private final CollectionService collection;
    private final HoldCollectionService holdCollection;

    private final OperationLogService operationLog;

    @Autowired
    public MintReceiver(CollectionService collectionService, HoldCollectionService holdCollection, OperationLogService operationLog) {
        this.collection = collectionService;
        this.holdCollection = holdCollection;
        this.operationLog = operationLog;
    }

    @RabbitHandler
    public void process(String json) {
        CollectionMintDto dto = CollectionMintDto.Companion.fromJson(json);
        boolean collectionBool = collection.mintCollection(dto);
        long collectionId = collection.getCollectionIdByTokenId(dto.getTokenId());
        HoldCollectionCreateReqDto createReqDto = new HoldCollectionCreateReqDto(
                dto.getMemberId(),
                collectionId,
                USER_CREATE_COLLECTION_APPLY_RECORD_ID);
        boolean holdCollectionBool = holdCollection.saveHoldCollection(createReqDto);
        if (collectionBool && holdCollectionBool) {
            log.info("MintReceiver: Mint Succeed {}", dto.toJson());
        } else {
            log.warn("MintReceiver: Mint Failed {}", dto.toJson());
        }
    }
}
