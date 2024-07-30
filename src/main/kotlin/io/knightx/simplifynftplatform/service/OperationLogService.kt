package io.knightx.simplifynftplatform.service

import com.github.pagehelper.PageInfo
import io.knightx.simplifynftplatform.common.*
import io.knightx.simplifynftplatform.dto.apply.ApplyRecordRespDto
import io.knightx.simplifynftplatform.dto.general.PageDto
import io.knightx.simplifynftplatform.dto.record.ApplyRecordAddReqDto
import io.knightx.simplifynftplatform.dto.record.ApplyRecordPageReqDto
import io.knightx.simplifynftplatform.dto.record.MyApplyRecordPageReqDto
import io.knightx.simplifynftplatform.dto.record.MyBeRequestRecordPageReqDto
import io.knightx.simplifynftplatform.exception.apply.ApplyRecordNotFountException
import io.knightx.simplifynftplatform.exception.collection.CollectionNotFoundException
import io.knightx.simplifynftplatform.exception.record.ApplyRecordAlreadyExistException
import io.knightx.simplifynftplatform.exception.record.ApplyRecordPermissionException
import io.knightx.simplifynftplatform.ext.check
import io.knightx.simplifynftplatform.ext.checkQueryTime
import io.knightx.simplifynftplatform.ext.pageQuery
import io.knightx.simplifynftplatform.ext.toErrorMsg
import io.knightx.simplifynftplatform.po.ApplyRecord
import io.knightx.simplifynftplatform.po.Collection
import io.knightx.simplifynftplatform.po.HoldCollection
import io.knightx.simplifynftplatform.po.repo.impl.ApplyRecordRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.CollectionRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.HoldCollectionRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.MemberRepoImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:45
 */
@Service
class OperationLogService(
    @Autowired
    private val recordRepo: ApplyRecordRepoImpl,

    @Autowired
    private val memberRepo: MemberRepoImpl,

    @Autowired
    private val holdRepo: HoldCollectionRepoImpl,

    @Autowired
    private val collectionRepo: CollectionRepoImpl
) {
    fun getApplyRecords(applyMemberId: Long): List<ApplyRecord> {
        return recordRepo.lambdaQuery().eq(ApplyRecord::getApplyMemberId, applyMemberId).list()
    }

    fun getApplyRecord(applyRecordId: Long): ApplyRecord {
        return recordRepo.lambdaQuery().eq(ApplyRecord::getId, applyRecordId).one()
    }

    fun addApplyRecord(record: ApplyRecordAddReqDto): Boolean {
        if (checkApplyRecordExist(record.applyMemberId, record.collectionId)) {
            throw ApplyRecordAlreadyExistException("Apply record already exists".toErrorMsg())
        }
        return recordRepo.save(record.toEntity().apply {
            this.status = ApplyRecordStatus.APPLYING.value
        })
    }

    fun updateApplyRecordStatus(applyRecordId: Long, status: Int, transactionHash: String?): Boolean {
        return recordRepo.lambdaUpdate()
            .eq(ApplyRecord::getId, applyRecordId)
            .set(ApplyRecord::getStatus, status)
            .set(transactionHash.check(), ApplyRecord::getTransactionHash, transactionHash)
            .update()
    }

    fun deleteApplyRecord(applyRecordId: Long): Boolean {
        return recordRepo.lambdaUpdate()
            .eq(ApplyRecord::getId, applyRecordId)
            .set(ApplyRecord::getDeleteFlag, DeleteType.TRUE.value)
            .update()
    }

    fun getApplyingRecordId(collectionId: Long, applyRecordId: Long): Long {
        return recordRepo.lambdaQuery()
            .eq(ApplyRecord::getCollectionId, collectionId)
            .eq(ApplyRecord::getApplyMemberId, applyRecordId)
            .eq(ApplyRecord::getStatus, ApplyRecordStatus.APPLYING.value)
            .one().id
    }

    fun getApplyRecordPage(dto: PageDto<ApplyRecord, ApplyRecordPageReqDto>): PageInfo<ApplyRecord> {
        return dto.pageQuery {
            recordRepo.lambdaQuery()
                .ge(
                    dto.query.startTime.check() && dto.query.endTime.check().not(),
                    ApplyRecord::getCreateAt, dto.query.startTime
                )
                .le(
                    dto.query.endTime.check() && dto.query.startTime.check().not(),
                    ApplyRecord::getCreateAt, dto.query.endTime
                )
                .between(
                    dto.query.startTime.check() && dto.query.endTime.check(),
                    ApplyRecord::getCreateAt, dto.query.startTime, dto.query.endTime
                )
                .eq(dto.query.status.check(), ApplyRecord::getStatus, dto.query.status)
                .eq(dto.query.applyMemberId.check(), ApplyRecord::getApplyMemberId, dto.query.applyMemberId)
                .eq(dto.query.holdMemberId.check(), ApplyRecord::getFromMemberId, dto.query.holdMemberId)
                .eq(dto.query.collectionId.check(), ApplyRecord::getCollectionId, dto.query.collectionId)
                .eq(ApplyRecord::getDeleteFlag, DeleteType.FALSE.value)
                .list()
        }
    }

    fun checkApplyRecordExist(applyRecordId: Long): Boolean {
        return recordRepo.lambdaQuery()
            .eq(ApplyRecord::getId, applyRecordId).count() > 0
    }

    fun checkApplyRecordExist(applyMemberId: Long, collectionId: Long): Boolean {
        return recordRepo.lambdaQuery()
            .eq(ApplyRecord::getCollectionId, collectionId)
            .eq(ApplyRecord::getApplyMemberId, applyMemberId).count() > 0
    }

    fun getMyApplyRecord(collectionId: Long?, memberId: Long, status: Int): List<ApplyRecordRespDto> {
        val record = recordRepo.lambdaQuery()
            .eq(collectionId.check(), ApplyRecord::getCollectionId, collectionId)
            .eq(ApplyRecord::getApplyMemberId, memberId)
            .eq(ApplyRecord::getStatus, ApplyRecordStatus.from(status).value)
            .list()
        if (record.check()) {
            throw ApplyRecordNotFountException("Exception: The Apply Record Not Found.")
        }
        return record.map { buildApplyRecordResp(it) }
    }

    fun getMyBeRequestedRecordPage(
        dto: PageDto<ApplyRecord, MyBeRequestRecordPageReqDto>,
        fromMemberId: Long
    ): PageInfo<ApplyRecordRespDto> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second
        val records = dto.pageQuery {
            recordRepo.lambdaQuery()
                .eq(checkStatusInt(dto.query.status), ApplyRecord::getStatus, dto.query.status)
                .eq(dto.query.collectionId > 0L, ApplyRecord::getCollectionId, dto.query.collectionId)
                .eq(dto.query.applyMemberId > 0L, ApplyRecord::getApplyMemberId, dto.query.applyMemberId)
                .eq(fromMemberId.check(), ApplyRecord::getFromMemberId, fromMemberId)
                .eq(ApplyRecord::getDeleteFlag, DeleteType.FALSE.value)
                .between(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank(),
                    ApplyRecord::getCreateAt, start, end
                )
                .ge(
                    dto.query.startTime.isNotBlank() && dto.query.endTime.isNotBlank().not(),
                    ApplyRecord::getCreateAt, start
                )
                .le(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank().not(),
                    ApplyRecord::getCreateAt, end
                )
                .list()
        }
        val resultList = records.list.map { buildApplyRecordResp(it) }
        val resultPage = PageInfo<ApplyRecordRespDto>()
        return resultPage.apply {
            this.list = resultList
            this.pages = records.pages
            this.pageNum = records.pageNum
            this.pageSize = records.pageSize
            this.total = records.total
        }
    }

    fun getMyApplyRecordPage(
        dto: PageDto<ApplyRecord, MyApplyRecordPageReqDto>,
        memberId: Long
    ): PageInfo<ApplyRecordRespDto> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second
        val records = dto.pageQuery {
            recordRepo.lambdaQuery()
                .eq(checkStatusInt(dto.query.status), ApplyRecord::getStatus, dto.query.status)
                .eq(dto.query.collectionId > 0L, ApplyRecord::getCollectionId, dto.query.collectionId)
                .eq(dto.query.holdMemberId > 0L, ApplyRecord::getFromMemberId, dto.query.holdMemberId)
                .eq(memberId.check(), ApplyRecord::getApplyMemberId, memberId)
                .eq(ApplyRecord::getDeleteFlag, DeleteType.FALSE.value)
                .between(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank(),
                    ApplyRecord::getCreateAt, start, end
                )
                .ge(
                    dto.query.startTime.isNotBlank() && dto.query.endTime.isNotBlank().not(),
                    ApplyRecord::getCreateAt, start
                )
                .le(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank().not(),
                    ApplyRecord::getCreateAt, end
                )
                .list()
        }
        val resultList = records.list.map { buildApplyRecordResp(it) }
        val resultPage = PageInfo<ApplyRecordRespDto>()
        return resultPage.apply {
            this.list = resultList
            this.pages = records.pages
            this.pageNum = records.pageNum
            this.pageSize = records.pageSize
            this.total = records.total
        }
    }

    fun buildApplyRecordResp(record: ApplyRecord): ApplyRecordRespDto {
        val collection: Collection = collectionRepo.getById(record.collectionId)
        val applyMemberName = memberRepo.getById(record.applyMemberId).memberName
        val laterOwner = getLaterOwner(collection.id)
        val holdMemberName = if (
            Objects.equals(record.type, ApplyType.USER.value) ||
            Objects.equals(collection.memberId, laterOwner.memberId)
        ) {
            memberRepo.getById(record.fromMemberId).memberName
        } else {
            PLATFORM_HOLD_COLLECTION_MEMBER_NAME
        }
        val holdCollection :HoldCollection? = holdRepo.lambdaQuery()
            .eq(HoldCollection::getCollectionId, record.collectionId)
            .eq(HoldCollection::getMemberId, record.fromMemberId)
            .eq(HoldCollection::getStatus, HoldStatus.HOLDING.value)
            .one()
        return ApplyRecordRespDto(
            id = record.id,
            collectionName = collection.name ?: "Unknown",
            holdCollectionId = holdCollection?.id ?: -1,
            fromMemberName = holdMemberName ?: "Unknown",
            applyMemberName = applyMemberName ?: "Unknown",
            status = ApplyRecordStatus.from(record.status).respName,
            createAt = record.createAt,
        )
    }

    private fun getLaterOwner(collectionId: Long): HoldCollection {
        if (checkCollectionExist(collectionId).not()) {
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        }
        return holdRepo.lambdaQuery()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getStatus, HoldStatus.HOLDING.value)
            .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
            .one()
    }

    private fun checkCollectionExist(collectionId: Long): Boolean {
        return collectionRepo.lambdaQuery()
            .eq(Collection::getId, collectionId)
            .eq(Collection::getDeleteFlag, DeleteType.FALSE.value)
            .eq(Collection::getHideFlag, HideType.FALSE.value).exists()
    }


    fun checkApplyPermission(applyRecordId: Long, applyMemberId: Long) {
        if ((recordRepo.lambdaQuery().eq(ApplyRecord::getId, applyRecordId)
                .eq(ApplyRecord::getApplyMemberId, applyMemberId)
                .exists()).not()
        ) {
            throw ApplyRecordPermissionException("Exception: Operation Failed , Apply Permission Error.".toErrorMsg())
        }
    }

    fun checkConfirmPermission(applyRecordId: Long, holdMemberId: Long) {
        if ((recordRepo.lambdaQuery().eq(ApplyRecord::getId, applyRecordId)
                .eq(ApplyRecord::getFromMemberId, holdMemberId)
                .exists()).not()
        ) {
            throw ApplyRecordPermissionException("Exception: Operation Failed , Apply Permission Error.".toErrorMsg())
        }
    }

    fun checkStatusInt(status: Int): Boolean {
        return status in ApplyRecordStatus.entries.map { it.value }
    }
}