package io.knightx.simplifynftplatform.mq.component;

import io.knightx.simplifynftplatform.common.ApplyRecordStatus;
import io.knightx.simplifynftplatform.common.ApplyType;
import io.knightx.simplifynftplatform.dto.collection.CollectionTransferDto;
import io.knightx.simplifynftplatform.dto.hold.HoldCollectionTransferDto;
import io.knightx.simplifynftplatform.po.Member;
import io.knightx.simplifynftplatform.service.CollectionService;
import io.knightx.simplifynftplatform.service.HoldCollectionService;
import io.knightx.simplifynftplatform.service.MemberService;
import io.knightx.simplifynftplatform.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午3:38
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.transfer}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange.direct}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routingKey.transfer}"
        )
)
@Component
public class TransferReceiver {
    private final Logger log = LoggerFactory.getLogger(TransferReceiver.class);
    private final CollectionService collection;
    private final MemberService member;
    private final HoldCollectionService holdCollection;
    private final OperationLogService operationLog;

    @Autowired
    public TransferReceiver(CollectionService collectionService, MemberService member, HoldCollectionService holdCollection, OperationLogService operationLog) {
        this.collection = collectionService;
        this.member = member;
        this.holdCollection = holdCollection;
        this.operationLog = operationLog;
    }

    @RabbitHandler
    public void process(String json) {
        CollectionTransferDto dto = CollectionTransferDto.Companion.fromJson(json);
        boolean toBool = false, fromBool = false, recordBool = false;
        /**
         * ApplyType为Plate-from, 表示平台发行, 无初始持有用户. 
         */
        if (Objects.equals(dto.getType(), ApplyType.USER.getValue())) {
            long collectionId = collection.getCollectionIdByTokenId(dto.getTokenId());
            Member from = member.getMemberByName(dto.getFromName());
            Member to = member.getMemberByName(dto.getToName());
            long applyRecordId = operationLog.getApplyingRecordId(collectionId, to.getId());
            recordBool = operationLog.updateApplyRecordStatus(
                    applyRecordId,
                    ApplyRecordStatus.APPROVED.getValue(),
                    dto.getTransactionHash());
            fromBool = holdCollection.releasedHoldCollection(from.getId(), collectionId);
            toBool = holdCollection.transferHoldCollection(new HoldCollectionTransferDto(
                    to.getId(),
                    collectionId,
                    applyRecordId,
                    dto.getCreateAt()
            ));
        } else if (Objects.equals(dto.getType(), ApplyType.PLATFORM.getValue())) {
            long collectionId = collection.getCollectionIdByTokenId(dto.getTokenId());
            Member to = member.getMemberByName(dto.getToName());
            long applyRecordId = operationLog.getApplyingRecordId(collectionId, to.getId());
            recordBool = operationLog.updateApplyRecordStatus(
                    applyRecordId,
                    ApplyRecordStatus.APPROVED.getValue(),
                    dto.getTransactionHash());
            fromBool = true;
            toBool = holdCollection.transferHoldCollection(new HoldCollectionTransferDto(
                    to.getId(),
                    collectionId,
                    applyRecordId,
                    dto.getCreateAt()
            ));
        }

        if (fromBool && toBool && recordBool) {
            log.info("TransferReceiver: Transfer Succeed {}", dto.toJson());
        } else {
            log.warn("TransferReceiver: Transfer Failed {}", dto.toJson());
        }
    }


}
