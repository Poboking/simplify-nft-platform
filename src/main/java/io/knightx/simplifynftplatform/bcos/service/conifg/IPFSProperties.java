package io.knightx.simplifynftplatform.bcos.service.conifg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/30 下午5:26
 */
@Configuration("myIpfsProperties")
@ConfigurationProperties(prefix = "ipfs")
public class IPFSProperties {

    public final String IPFS_DEFAULT_NODE = "/ip4/127.0.0.1/tcp/5001";

    @Value("${ipfs.vps.node}")
    public String IPFS_VPS_NODE;
}
