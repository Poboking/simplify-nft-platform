package io.knightx.simplifynftplatform.bcos.service.conifg;

import io.knightx.simplifynftplatform.bcos.service.sdk.SNPToken;
import io.knightx.simplifynftplatform.bcos.service.sdk.SNPUser;
import io.knightx.simplifynftplatform.exception.bcos.BlockChainConfigurationException;
import io.knightx.simplifynftplatform.exception.bcos.ContractNetDeployedException;
import io.knightx.simplifynftplatform.exception.bcos.GainBlockChainAccountException;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.knightx.simplifynftplatform.bcos.service.utils.StringUtil.splitString;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午2:53
 */
@Configuration
@EnableConfigurationProperties(FiscoBcosProperties.class)
public class FiscoBcosConfig {

    private final FiscoBcosProperties fiscoBcosProperties;

    @Autowired
    public FiscoBcosConfig(FiscoBcosProperties fiscoBcosProperties) {
        this.fiscoBcosProperties = fiscoBcosProperties;
    }

    @Bean
    public ConfigProperty configProperty() {
        ConfigProperty property = new ConfigProperty();
        Map<String, Object> cryptoMaterial = new HashMap<>();
        Map<String, Object> networkProperties = new HashMap<>();

        try {
            networkProperties.put("peers", List.of(splitString(fiscoBcosProperties.getPressNodes())));
            cryptoMaterial.put("certPath", fiscoBcosProperties.getCertPath());
            cryptoMaterial.put("maxBlockingQueueSize", "100");
            cryptoMaterial.put("threadPoolQueueSize", "100");
        } catch (Exception e) {
            throw new BlockChainConfigurationException("Exception: The `fisco` properties Not configured yet.");
        }
        property.setNetwork(networkProperties);
        property.setCryptoMaterial(cryptoMaterial);
        return property;
    }

    @Bean
    public ConfigOption configOption(ConfigProperty configProperty) throws ConfigException {
        return new ConfigOption(configProperty);
    }

    @Bean
    public BcosSDK bcosSDK(ConfigOption configOption) throws ConfigException {
        return new BcosSDK(configOption);
    }

    @Bean
    public Client client(BcosSDK bcosSDK) {
        return bcosSDK.getClient(BcosConstant.DEFAULT_GROUP);
    }

    @Bean(name = "deployCryptoKeyPair")
    public CryptoKeyPair cryptoKeyPair(Client client) {
        try {
            return client.getCryptoSuite()
                    .getKeyPairFactory()
                    .createKeyPair(fiscoBcosProperties.getDeployPrivateKey());
        } catch (Exception e) {
            throw new GainBlockChainAccountException("Exception: Gain The BlockChain Deploy Account`s Address Error.");
        }
    }

    @Bean("deployToken")
    public SNPToken snpToken(Client client, CryptoKeyPair cryptoKeyPair) {
        try {
            return SNPToken.load(fiscoBcosProperties.getNftContractAddress(), client, cryptoKeyPair);
        } catch (Exception e) {
            throw new ContractNetDeployedException("Exception: The SnpToken Solidity Contract is Net Yet Deployed.");
        }
    }
    
    @Bean("deployUser")
    public SNPUser snpUser(Client client, CryptoKeyPair cryptoKeyPair){
        try {
            return SNPUser.load(fiscoBcosProperties.getUserContractAddress(), client, cryptoKeyPair);
        }catch (Exception e) {
            throw new ContractNetDeployedException("Exception: The SnpUser Solidity Contract is Net Yet Deployed.");
        }
    }

}
