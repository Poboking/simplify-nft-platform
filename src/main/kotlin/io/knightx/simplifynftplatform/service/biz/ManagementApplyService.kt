package io.knightx.simplifynftplatform.service.biz

import io.knightx.simplifynftplatform.bcos.service.biz.ChainUserService
import io.knightx.simplifynftplatform.bcos.service.biz.TokenService
import io.knightx.simplifynftplatform.common.ApplyRecordStatus
import io.knightx.simplifynftplatform.common.ApplyType
import io.knightx.simplifynftplatform.dto.record.ApplyRecordAddReqDto
import io.knightx.simplifynftplatform.exception.apply.ApplyRecordNotFountException
import io.knightx.simplifynftplatform.exception.apply.ApplyRecordSelfException
import io.knightx.simplifynftplatform.ext.toErrorMsg
import io.knightx.simplifynftplatform.po.Member
import io.knightx.simplifynftplatform.service.CollectionService
import io.knightx.simplifynftplatform.service.HoldCollectionService
import io.knightx.simplifynftplatform.service.MemberService
import io.knightx.simplifynftplatform.service.OperationLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午10:08
 */
@Service
class ManagementApplyService(
    @Autowired val token: TokenService,
    @Autowired val chainUser: ChainUserService,
    @Autowired val member: MemberService,
    @Autowired val operationLog: OperationLogService,
    @Autowired val collection: CollectionService,
    @Autowired val holdCollection: HoldCollectionService
) {

    fun userConfirmApply(applyRecordId: Long, holdMemberId: Long): Boolean {
        checkApplyStatus(applyRecordId, ApplyRecordStatus.APPLYING.value)
        val record = operationLog.getApplyRecord(applyRecordId)
        val tokenId = collection.getTokenIdByCollectionId(record.collectionId)
        val privateKeyById: String = member.getPrivateKeyById(holdMemberId)
        val from: Member = member.getMember(record.fromMemberId)!!
        val to: Member = member.getMember(record.applyMemberId)!!
        val keyPair = chainUser.getCryptoKeyPair(privateKeyById)
        val snpToken = token.gainSDK(keyPair)
        return token.transferToken(snpToken, from.memberName, to.memberName, tokenId, ApplyType.USER.value)
    }

    fun userPutForwardApply(holdCollectionId: Long, applyMemberId: Long): Boolean {
        holdCollection.checkHoldCollectionExist(holdCollectionId)
        val hold = holdCollection.getHoldCollection(holdCollectionId)
        checkNotSelf(applyMemberId, hold.memberId)
        val add = ApplyRecordAddReqDto(
            collectionId = hold.collectionId,
            fromMemberId = hold.memberId,
            applyMemberId = applyMemberId
        )
        return operationLog.addApplyRecord(add)
    }

    fun userCancelApply(applyRecordId: Long, applyMemberId: Long): Boolean {
        operationLog.checkApplyPermission(applyRecordId, applyMemberId)
        return operationLog.updateApplyRecordStatus(
            applyRecordId,
            ApplyRecordStatus.CANCEL.value,
            null
        )
    }

    fun userRejectedApply(applyRecordId: Long, fromMemberId: Long): Boolean {
        operationLog.checkConfirmPermission(applyRecordId, fromMemberId)
        return operationLog.updateApplyRecordStatus(
            applyRecordId,
            ApplyRecordStatus.REJECTED.value,
            null
        )
    }

    fun checkNotSelf(self: Long, other: Long) {
        if (self == other) {
            throw ApplyRecordSelfException("Exception: Must be not self.".toErrorMsg())
        }
    }

    private fun checkApplyStatus(applyRecordId: Long, status: Int) {
        when(
            operationLog.getApplyRecord(applyRecordId).status
        ) {
            status -> return
            else -> throw ApplyRecordNotFountException("Exception: Apply Status is not $status.".toErrorMsg())
        }
    }
}