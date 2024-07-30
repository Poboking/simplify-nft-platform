package io.knightx.simplifynftplatform.bcos.service.biz;

import io.knightx.simplifynftplatform.exception.member.MemberAddressDeficiencyException;
import io.knightx.simplifynftplatform.po.Member;
import io.knightx.simplifynftplatform.po.repo.impl.MemberRepoImpl;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午2:46
 */
@Component
public class ChainUserService {
    private final Logger log = LoggerFactory.getLogger(ChainUserService.class);
    private final Client client;

    private final MemberRepoImpl memberRepo;

    @Autowired
    public ChainUserService(Client client, MemberRepoImpl memberRepo) {
        this.client = client;
        this.memberRepo = memberRepo;
    }

    public CryptoKeyPair getRandomKeyPair() {
        return client.getCryptoSuite().createKeyPair();
    }

    public String getAddressByPublicKey(String publicKey) {
        return client.getCryptoSuite().getKeyPairFactory().getAddress(publicKey);
    }

    public CryptoKeyPair getCryptoKeyPair(String privateKey) {
        return client.getCryptoSuite().getKeyPairFactory().createKeyPair(privateKey);
    }

    public String getAddressByUsername(String username) {
        Member member = memberRepo.lambdaQuery()
                .eq(username != null, Member::getMemberName, username)
                .one();
        if (member == null || member.getBlockAddress() == null) {
            throw new MemberAddressDeficiencyException("Exception: Deficiency Member BlockChain Address Info.");
        }
        return member.getBlockAddress();
    }


    public Boolean registerChainUser(Long memberId, String username) {
        CryptoKeyPair keyPair = getRandomKeyPair();
        String privateKey = keyPair.getHexPrivateKey();
        String publicKey = keyPair.getHexPublicKey();
        String address = keyPair.getAddress();
        boolean bool = memberRepo.lambdaUpdate()
                .eq(Member::getId, memberId)
                .eq(username != null, Member::getMemberName, username)
                .set(Member::getBlockAddress, address)
                .set(Member::getPrivateKey, privateKey)
                .set(Member::getPublicKey, publicKey)
                .update();
        if (bool) {
            log.info("Register ChainUser Success:[username]{} [address]{} [privateKey]{} [publicKey]{}", username, address, privateKey, publicKey);
        } else {
            log.error("Register ChainUser Fail:  [username]{} [address]{} [privateKey]{} [publicKey]{}", username, address, privateKey, publicKey);
        }
        return bool;
    }
}
