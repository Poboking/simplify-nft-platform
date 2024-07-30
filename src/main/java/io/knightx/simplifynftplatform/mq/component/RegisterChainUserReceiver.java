package io.knightx.simplifynftplatform.mq.component;

import io.knightx.simplifynftplatform.bcos.service.biz.ChainUserService;
import io.knightx.simplifynftplatform.dto.member.MemberRegisterChainUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午5:46
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.register}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange.direct}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routingKey.register}"
        )
)
@Component
public class RegisterChainUserReceiver {
    private final Logger log = LoggerFactory.getLogger(RegisterChainUserReceiver.class);

    private final ChainUserService chainUserService;

    @Autowired
    public RegisterChainUserReceiver(ChainUserService chainUserService) {
        this.chainUserService = chainUserService;
    }

    @RabbitHandler
    public void process(String json) {
        MemberRegisterChainUserDto dto = MemberRegisterChainUserDto.Companion.fromJson(json);
        Boolean bool = chainUserService.registerChainUser(dto.getMemberId(), dto.getUsername());
        if (bool) {
            log.info("RegisterChainUserReceiver: Register Chain User Succeed {}", dto.toJson());
        }else {
            log.warn("RegisterChainUserReceiver: Register Chain User  Failed {}", dto.toJson());
        }
        
    }
}
