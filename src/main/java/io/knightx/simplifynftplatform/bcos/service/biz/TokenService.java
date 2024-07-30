package io.knightx.simplifynftplatform.bcos.service.biz;

import io.knightx.simplifynftplatform.bcos.service.conifg.FiscoBcosProperties;
import io.knightx.simplifynftplatform.bcos.service.ipfs.IPFSService;
import io.knightx.simplifynftplatform.bcos.service.sdk.SNPToken;
import io.knightx.simplifynftplatform.bcos.service.utils.StatusUtil;
import io.knightx.simplifynftplatform.bcos.service.utils.StringUtil;
import io.knightx.simplifynftplatform.dto.collection.CollectionBrunDto;
import io.knightx.simplifynftplatform.dto.collection.CollectionMintDto;
import io.knightx.simplifynftplatform.dto.collection.CollectionSoldOutDto;
import io.knightx.simplifynftplatform.dto.collection.CollectionTransferDto;
import io.knightx.simplifynftplatform.mq.component.RabbitSender;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午2:46
 */
@Component
@EnableConfigurationProperties(FiscoBcosProperties.class)
public class TokenService {

    private final Logger log = LoggerFactory.getLogger(TokenService.class);
    private final FiscoBcosProperties fiscoBcosProperties;
    private final RabbitSender sender;
    private final IPFSService ipfsService;
    private final ChainUserService chainUserService;
    private final Client client;

    @Autowired
    public TokenService(FiscoBcosProperties fiscoBcosProperties, RabbitSender sender, IPFSService ipfsService, ChainUserService chainUserService, Client client) {
        this.fiscoBcosProperties = fiscoBcosProperties;
        this.sender = sender;
        this.ipfsService = ipfsService;
        this.chainUserService = chainUserService;
        this.client = client;
    }

    public SNPToken gainSDK(CryptoKeyPair user) {
        return SNPToken.load(
                fiscoBcosProperties.getNftContractAddress(),
                client,
                user
        );
    }

    public boolean mintToken(SNPToken token, String username, Long memberId, String collectionName, String content)
            throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String tokenUrl = ipfsService
                .save(collectionName,
                        username,
                        LocalDateTime.now().toString(),
                        content);
        TransactionReceipt receipt = token.mint(tokenUrl);
        String tokenId = receipt.getOutput();
        //todo 铸造Collection
        CollectionMintDto dto = new CollectionMintDto(memberId, username, collectionName, content, tokenId, now);
        sender.sendMint(dto.toJson());
        log.info("RabbitMQ: Mint" + dto.toJson());
        return StatusUtil.checkTransactionStatus(receipt.getStatus());
    }


    public boolean transferToken(SNPToken token, String fromName, String toName, String tokenId, Integer type) {
        LocalDateTime now = LocalDateTime.now();
        String fromAddress = chainUserService.getAddressByUsername(fromName);
        String toAddress = chainUserService.getAddressByUsername(toName);
        TransactionReceipt receipt = token.safeTransferFrom(fromAddress, toAddress, StringUtil.hexToBigInt(tokenId));
        String transactionHash = receipt.getTransactionHash();
        //todo 交易collection
        CollectionTransferDto dto = new CollectionTransferDto(fromName, toName, tokenId, type, transactionHash, now);
        sender.sendTransfer(dto.toJson());
        log.info("RabbitMQ: Transfer" + dto.toJson());
        return StatusUtil.checkTransactionStatus(receipt.getStatus());
    }


    public boolean burnToken(SNPToken token, String tokenId) {
        LocalDateTime now = LocalDateTime.now();
        TransactionReceipt receipt = token.burn(StringUtil.hexToBigInt(tokenId));
        String blockHash = receipt.getBlockHash();
        //todo 销毁collection
        CollectionBrunDto dto = new CollectionBrunDto(tokenId, blockHash, now);
        sender.sendBrun(dto.toJson());
        log.info("RabbitMQ: Burn" + dto.toJson());
        return StatusUtil.checkTransactionStatus(receipt.getStatus());
    }

    public boolean soldOutToken(SNPToken token, String username, String tokenId, Long holdCollectionId) throws ContractException {
        LocalDateTime now = LocalDateTime.now();
        String address = chainUserService.getAddressByUsername(username);
        String owner = token.ownerOf(StringUtil.hexToBigInt(tokenId));
        if (owner.equals(address)) {
            //todo 下架collection
            CollectionSoldOutDto dto = new CollectionSoldOutDto(tokenId, username, holdCollectionId, now);
            sender.sendSoldOut(dto.toJson());
            log.info("RabbitMQ: SoldOut" + dto.toJson());
            return true;
        }
        return false;
    }
    
}
