package io.knightx.simplifynftplatform.bcos.service.conifg;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/18 下午3:45
 */
@Configuration
@EnableConfigurationProperties(IPFSProperties.class)
public class IPFSConfiguration {

    private final IPFSProperties ipfsProperties;

    @Autowired
    public IPFSConfiguration(@Qualifier("myIpfsProperties") IPFSProperties ipfsProperties) {
        this.ipfsProperties = ipfsProperties;
    }

    @Bean
    public IPFS ipfs() {
        return new IPFS(ipfsProperties.IPFS_VPS_NODE);
    }
}
