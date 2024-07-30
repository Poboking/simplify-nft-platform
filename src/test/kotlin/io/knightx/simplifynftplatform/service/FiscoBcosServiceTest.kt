package io.knightx.simplifynftplatform.service

import io.knightx.simplifynftplatform.bcos.service.conifg.FiscoBcosProperties
import io.knightx.simplifynftplatform.bcos.service.sdk.SNPToken
import io.knightx.simplifynftplatform.bcos.service.sdk.SNPUser
import io.knightx.simplifynftplatform.bcos.service.utils.StringUtil
import org.fisco.bcos.sdk.client.Client
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair
import org.fisco.bcos.sdk.transaction.model.exception.ContractException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午4:14
 */
@SpringBootTest
@EnableConfigurationProperties(FiscoBcosProperties::class)
class FiscoBcosServiceTest @Autowired constructor(
    @Qualifier("fisco-io.knightx.simplifynftplatform.bcos.service.conifg.FiscoBcosProperties")
    @Autowired private val properties: FiscoBcosProperties,
    @Autowired private val client: Client
) {
    
    @Autowired
    private lateinit var deployCryptoKeyPair: CryptoKeyPair

    @Autowired
    private lateinit var snpUser: SNPUser

    @Test
    fun loadToken() {
        val deploy = client.cryptoSuite.createKeyPair(properties.deployPrivateKey)
        val snpToken = SNPToken.load(
            properties.nftContractAddress,
            client,
            client.cryptoSuite.createKeyPair()
        )
        println("The load:$snpToken")
        val receipt = snpToken.mint("TestBy2024717${LocalDateTime.now()}")
        println("min:${receipt.status}")
        println("mint output:${receipt.output}")
    }
    
    @Test
    fun mint(){
        val snpToken = SNPToken.load(
            properties.nftContractAddress,
            client,
            deployCryptoKeyPair
        )
        val receipt = snpToken.mint("TestBy2024717${LocalDateTime.now()}")
        println("mint tokenId:${receipt.output}")
    }
    
    @Test
    fun transaction() {
        val snpToken = SNPToken.load(
            properties.nftContractAddress,
            client,
            deployCryptoKeyPair
        )
        val receipt = snpToken.safeTransferFrom(
            deployCryptoKeyPair.address, 
            client.cryptoSuite.createKeyPair().address,
            StringUtil.hexToBigInt("0xc076f5bdbd89288808cbd23b1c0966f6b62db185315dc105c847df9edd27dec4")
            )
        println(receipt.status)
        println(receipt.message)
    }
    
    
    @Test
    fun getAddressByPublicKey(): Unit {
        val key = client.cryptoSuite.cryptoKeyPair
        val publicKey = key.hexPublicKey
        key.hexPrivateKey
        println("privateKey:${key.hexPrivateKey}\n")
        println("publicKey:${key.hexPublicKey}\n")
        key.storeKeyPairWithPem("\\key")
        println(client.cryptoSuite.keyPairFactory.getAddress(publicKey))
    }
    
    @Test
    fun getRandomAddress(){
        println(client.cryptoSuite.createKeyPair().address)
    }

    @Test
    fun loadUser() {
        val deploy = client.cryptoSuite.createKeyPair(properties.deployPrivateKey)
        val snpUser = SNPUser.load(
            properties.nftContractAddress,
            client,
            client.cryptoSuite.createKeyPair()
        )
        val name = "TestBy2024711"
        println("The load:$snpUser")
        println("The register output:${snpUser.registerUser(name)}")
    }
    
    @Test
    fun getUser(){
        val snpUser = SNPUser.load(
            properties.nftContractAddress,
            client,
            client.cryptoSuite.createKeyPair()
        )
        val name = "TestBy2024711"
        println("The load:$snpUser")
//        println("The get user address output:${snpUser.getAddress(name)}")
        println("The get user address output:${snpUser.registerUser(name)}")
    }

    @Test
    fun deploy(){
        deployNft()
        deployUser()
    }
    
    @Test
    @Throws(ContractException::class)
    fun deployNft() {
        val deploy = client.cryptoSuite.createKeyPair(properties.deployPrivateKey)
        val token = SNPToken.deploy(client, deploy, "MyToken", "MFT")
        val address = token.contractAddress
        println("nft:$address")
        println(token.deployReceipt.message)
        println(token.deployReceipt.status)
        println(token.deployReceipt.statusMsg)
    }

    @Test
    @Throws(ContractException::class)
    fun deployUser() {
        val deploy = client.cryptoSuite.createKeyPair(properties.deployPrivateKey)
        val user = SNPUser.deploy(client, deploy)
        val address = user.contractAddress
        println("user:$address")
        println(user.deployReceipt.message)
        println(user.deployReceipt.status)
        println(user.deployReceipt.statusMsg)
    }
}