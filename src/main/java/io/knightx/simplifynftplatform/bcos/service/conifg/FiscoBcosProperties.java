package io.knightx.simplifynftplatform.bcos.service.conifg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午2:55
 */
@Configuration
@ConfigurationProperties(prefix = "fisco")
public class FiscoBcosProperties {
    @Value("${fisco.cert-path}")
    private String certPath;
    @Value("${fisco.deploy-private-key}")
    private String deployPrivateKey;
    @Value("${fisco.deploy-address}")
    private String deployAddress;
    @Value("${fisco.user-contract-address}")
    private String userContractAddress;
    @Value("${fisco.nft-contract-address}")
    private String nftContractAddress;
    @Value("${fisco.press-nodes}")
    private String pressNodes;

    public String getCertPath() {
        return certPath;
    }

    public String getDeployPrivateKey() {
        return deployPrivateKey;
    }

    public String getDeployAddress() {
        return deployAddress;
    }

    public String getUserContractAddress() {
        return userContractAddress;
    }

    public String getNftContractAddress() {
        return nftContractAddress;
    }

    public String getPressNodes() {
        return pressNodes;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public void setDeployPrivateKey(String deployPrivateKey) {
        this.deployPrivateKey = deployPrivateKey;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public void setUserContractAddress(String userContractAddress) {
        this.userContractAddress = userContractAddress;
    }

    public void setNftContractAddress(String nftContractAddress) {
        this.nftContractAddress = nftContractAddress;
    }

    public void setPressNodes(String pressNodes) {
        this.pressNodes = pressNodes;
    }

}
