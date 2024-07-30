package io.knightx.simplifynftplatform.service.biz

import io.knightx.simplifynftplatform.bcos.service.biz.ChainUserService
import io.knightx.simplifynftplatform.bcos.service.biz.TokenService
import io.knightx.simplifynftplatform.exception.hold.HoldCollectionNotFountException
import io.knightx.simplifynftplatform.ext.toErrorMsg
import io.knightx.simplifynftplatform.service.CollectionService
import io.knightx.simplifynftplatform.service.HoldCollectionService
import io.knightx.simplifynftplatform.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午11:07
 */
@Service
class ManagementTokenService(
    @Autowired val token: TokenService,
    @Autowired val chainUser: ChainUserService,
    @Autowired val member: MemberService,
    @Autowired val collection: CollectionService,
    @Autowired val holdCollection: HoldCollectionService,
) {

    fun userCreateToken(collectionName: String, content: String, creatorId: Long): Boolean {
        member.checkMemberIsChainUser(creatorId)
        collection.checkCollectionUptoStandard(collectionName, content)
        val one = member.getMember(creatorId)!!
        val privateKeyById: String = member.getPrivateKeyById(creatorId)
        val keyPair = chainUser.getCryptoKeyPair(privateKeyById)
        val snpToken = token.gainSDK(keyPair)
        return token.mintToken(snpToken, one.memberName, creatorId, collectionName, content)
    }

    fun userSoldOutToken(creatorId: Long, collectionId: Long): Boolean {
        val holdCollectionId = holdCollection.getMyHoldCollectionId(collectionId, creatorId)
        holdCollection.checkHoldCollectionSoldOut(
            holdCollectionId ?: throw HoldCollectionNotFountException("Hold collection not found".toErrorMsg())
        )
        val username: String = member.getMember(creatorId)!!.memberName
        val tokenId = collection.getCollection(collectionId).tokenId
        val privateKeyById: String = member.getPrivateKeyById(creatorId)
        val keyPair = chainUser.getCryptoKeyPair(privateKeyById)
        val snpToken = token.gainSDK(keyPair)
        return token.soldOutToken(snpToken, username, tokenId, holdCollectionId)
    }
}