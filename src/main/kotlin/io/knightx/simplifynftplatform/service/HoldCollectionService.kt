package io.knightx.simplifynftplatform.service

import com.github.pagehelper.PageInfo
import io.knightx.simplifynftplatform.common.DeleteType
import io.knightx.simplifynftplatform.common.HideType
import io.knightx.simplifynftplatform.common.HoldStatus
import io.knightx.simplifynftplatform.dto.general.PageDto
import io.knightx.simplifynftplatform.dto.hold.*
import io.knightx.simplifynftplatform.exception.collection.CollectionNotFoundException
import io.knightx.simplifynftplatform.exception.hold.HoldCollectionAlreadyExistException
import io.knightx.simplifynftplatform.exception.hold.HoldCollectionAlreadySoldOutException
import io.knightx.simplifynftplatform.exception.hold.HoldCollectionConvertDetailRespException
import io.knightx.simplifynftplatform.exception.hold.HoldCollectionNotFountException
import io.knightx.simplifynftplatform.ext.*
import io.knightx.simplifynftplatform.po.Collection
import io.knightx.simplifynftplatform.po.HoldCollection
import io.knightx.simplifynftplatform.po.Member
import io.knightx.simplifynftplatform.po.mapper.MPJHoldCollectionMapper
import io.knightx.simplifynftplatform.po.repo.impl.CollectionRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.HoldCollectionRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.MemberRepoImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:43
 */
@Suppress("UNCHECKED_CAST")
@Service
class HoldCollectionService(
    @Autowired
    val holdRepo: HoldCollectionRepoImpl,
    @Autowired
    val memberRepo: MemberRepoImpl,
    @Autowired
    val collectionRepo: CollectionRepoImpl,
    @Autowired
    val mpjHoldMapper: MPJHoldCollectionMapper
) {

    fun saveHoldCollection(dto: HoldCollectionCreateReqDto): Boolean {
        if (checkHoldCollectionExist(dto.collectionId, dto.memberId)) {
            throw HoldCollectionAlreadyExistException("Hold collection already exists".toErrorMsg())
        }
        return holdRepo.save(dto.toEntity().apply {
            this.status = HoldStatus.HOLDING.value
            this.hideFlag = HideType.FALSE.value
        })
    }

    fun transferHoldCollection(dto: HoldCollectionTransferDto): Boolean {
        if (checkHoldCollectionExist(dto.collectionId, dto.applyRecordId)) {
            throw HoldCollectionAlreadyExistException("Hold collection already exists".toErrorMsg())
        }
        val bool: Boolean = holdRepo.save(dto.toEntity().apply {
            this.status = HoldStatus.HOLDING.value
        })
        return bool
    }

    fun deleteHoldCollection(collectionId: Long, memberId: Long): Boolean {
        return holdRepo.lambdaUpdate()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getMemberId, memberId)
            .set(HoldCollection::getDeleteFlag, DeleteType.TRUE.value)
            .update()
    }

    fun getHoldCollection(holdCollectionId: Long): HoldCollection {
        checkHoldCollectionExist(holdCollectionId)
        return holdRepo.getById(holdCollectionId)
    }

    fun getHoldCollectionPage(dto: PageDto<HoldCollection, HoldCollectionPageReqDto>): PageInfo<HoldCollection> {
        return dto.pageQuery {
            holdRepo.lambdaQuery()
                .eq(dto.query.collectionId.check(), HoldCollection::getCollectionId, dto.query.collectionId)
                .eq(dto.query.memberId.check(), HoldCollection::getMemberId, dto.query.memberId)
                .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
                .between(
                    dto.query.endTime.check() && dto.query.startTime.check(),
                    HoldCollection::getCreateAt, dto.query.startTime, dto.query.endTime
                )
                .ge(
                    dto.query.startTime.check() && dto.query.endTime.check().not(),
                    HoldCollection::getCreateAt, dto.query.startTime
                )
                .le(
                    dto.query.endTime.check() && dto.query.startTime.check().not(),
                    HoldCollection::getCreateAt, dto.query.endTime
                )
                .list()
        }
    }


    fun checkMyHoldCollectionExist(holdCollectionId: Long, memberId: Long): Boolean {
        return holdRepo.lambdaQuery()
            .eq(HoldCollection::getId, holdCollectionId)
            .eq(HoldCollection::getMemberId, memberId)
            .eq(HoldCollection::getStatus, HoldStatus.HOLDING.value)
            .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
            .count() > 0
    }

    fun checkHoldCollectionExist(collectionId: Long, memberId: Long): Boolean {
        return holdRepo.lambdaQuery()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getMemberId, memberId)
            .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
            .count() > 0
    }

    private fun checkCollectionSoldOut(collectionId: Long): Boolean {
        return holdRepo.lambdaQuery()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getHideFlag, HideType.TRUE.value)
            .exists()
    }

    fun soldOutCollection(holdCollectionId: Long, collectionId: Long, memberId: Long): Boolean {
        if (!checkHoldCollectionExist(collectionId, memberId)) {
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        }
        if (checkCollectionSoldOut(collectionId)) {
            return false
        }
        return holdRepo.lambdaUpdate()
            .eq(HoldCollection::getId, holdCollectionId)
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getMemberId, memberId)
            .set(HoldCollection::getHideFlag, HideType.TRUE.value)
            .update()
    }

    fun releasedHoldCollection(memberId: Long, collectionId: Long): Boolean {
        return holdRepo.lambdaUpdate()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getMemberId, memberId)
            .eq(HoldCollection::getStatus, HoldStatus.HOLDING.value)
            .set(HoldCollection::getStatus, HoldStatus.RELEASED.value)
            .update()
    }

    fun getMyHoldCollectionId(collectionId: Long, memberId: Long): Long? {
        return holdRepo.lambdaQuery()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getMemberId, memberId)
            .eq(HoldCollection::getStatus, HoldStatus.HOLDING.value)
            .one()?.id
    }

    fun getMyHoldCollection(holdCollectionId: Long, memberId: Long): MyHoldCollectionDetailRespDto {
        if (checkMyHoldCollectionExist(holdCollectionId, memberId).not()) {
            throw HoldCollectionNotFountException("Exception:Request HoldCollection Permission denied.".toErrorMsg())
        }
        return convertToDetailRespDto(holdRepo.getById(holdCollectionId))
    }

    fun getMyHoldCollectionPage(
        dto: PageDto<HoldCollection, MyHoldCollectionPageReqDto>,
        memberId: Long
    ): PageInfo<MyHoldCollectionDetailRespDto> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second

//        val resultPage: PageInfo<MyHoldCollectionDetailRespDto> =
//            HoldCollection().pageQuery(dto.pageNum, dto.pageSize) {
//                val join = MPJLambdaWrapper<HoldCollection>()
//                    .selectAll(HoldCollection::class.java)
//                    .selectAs(Collection::getName, MyHoldCollectionDetailRespDto::collectionName)
//                    .selectAs(Collection::getTokenId, MyHoldCollectionDetailRespDto::tokenId)
//                    .selectAs(Member::getMemberName, MyHoldCollectionDetailRespDto::memberName)
//                    .leftJoin(Collection::class.java, Collection::getId, HoldCollection::getCollectionId)
//                    .leftJoin(Member::class.java, Member::getId, HoldCollection::getMemberId)
//                    .eq(memberId.check(), HoldCollection::getMemberId, memberId)
//                    .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
//                    .like(dto.query.collectionName.isNotBlank(), Collection::getName, dto.query.collectionName)
//                    .between(
//                        dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank(),
//                        HoldCollection::getCreateAt, start, end
//                    )
//                    .ge(
//                        dto.query.startTime.isNotBlank() && dto.query.endTime.isNotBlank().not(),
//                        HoldCollection::getCreateAt, start
//                    )
//                    .le(
//                        dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank().not(),
//                        HoldCollection::getCreateAt, end
//                    )
//                mpjHoldMapper.selectJoinList(MyHoldCollectionDetailRespDto::class.java, join)
//            } as PageInfo<MyHoldCollectionDetailRespDto>
//        
//        return resultPage.apply {
//            this.list.toMutableList().stream().forEach {
//                it.statusName = HoldStatus.getName(it.status)
//            }
//        }

        val resultDto = dto.pageQuery {
            holdRepo.lambdaQuery()
                .eq(memberId.check(), HoldCollection::getMemberId, memberId)
                .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
                .between(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank(),
                    HoldCollection::getCreateAt, start, end
                )
                .ge(
                    dto.query.startTime.isNotBlank() && dto.query.endTime.isNotBlank().not(),
                    HoldCollection::getCreateAt, start
                )
                .le(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank().not(),
                    HoldCollection::getCreateAt, end
                )
                .list()
        }
        return convertToResultPageInfo(resultDto) {
            resultDto.list.stream().map { convertToDetailRespDto(it) }.toList()
        }

    }

    fun checkHoldCollectionSoldOut(holdCollectionId: Long) {
        val exists = holdRepo.lambdaQuery()
            .eq(HoldCollection::getId, holdCollectionId)
            .eq(HoldCollection::getHideFlag, HideType.TRUE.value)
            .exists()
        if (exists) {
            throw HoldCollectionAlreadySoldOutException("Collection Already sold out".toErrorMsg())
        }
    }

    fun getLaterOwner(collectionId: Long): HoldCollection? {
        if (checkCollectionExist(collectionId).not()) {
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        }
        return holdRepo.lambdaQuery()
            .eq(HoldCollection::getCollectionId, collectionId)
            .eq(HoldCollection::getStatus, HoldStatus.HOLDING.value)
            .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
            .list()[0]
    }

    private fun checkCollectionExist(collectionId: Long): Boolean {
        return collectionRepo.lambdaQuery()
            .eq(Collection::getId, collectionId)
            .eq(Collection::getDeleteFlag, DeleteType.FALSE.value)
            .eq(Collection::getHideFlag, HideType.FALSE.value).exists()
    }

    fun convertToDetailRespDto(entity: HoldCollection): MyHoldCollectionDetailRespDto {
        val member: Member
        val collection: Collection
        try {
            member = memberRepo.getById(entity.memberId)
            collection = collectionRepo.getById(entity.collectionId)
        } catch (e: Exception) {
            throw HoldCollectionConvertDetailRespException("Exception:Convert DetailRespDto failed $e".toErrorMsg())
        }
        return MyHoldCollectionDetailRespDto(
            id = entity.id,
            memberId = entity.memberId,
            memberName = member.memberName,
            collectionId = entity.collectionId,
            collectionName = collection.name,
            applyRecordId = entity.applyRecordId,
            status = entity.status,
            statusName = HoldStatus.getName(entity.status),
            tokenId = collection.tokenId,
            createAt = entity.createAt,
            hideFlag = entity.hideFlag,
        )
    }

    fun convertToDetailRespDto(entity: Collection): MemberHoldCollectionDetailRespDto {
        val member: Member
        val holdCollection: HoldCollection
        try {
            holdCollection =
                getLaterOwner(entity.id) ?: throw HoldCollectionNotFountException("Collection not found".toErrorMsg())
            member = memberRepo.getById(holdCollection.memberId)
        } catch (e: Exception) {
            throw HoldCollectionConvertDetailRespException("Exception:Convert DetailRespDto failed $e".toErrorMsg())
        }
        return MemberHoldCollectionDetailRespDto(
            id = holdCollection.id,
            memberId = entity.memberId,
            memberName = member.memberName,
            collectionId = entity.id,
            collectionName = entity.name,
            applyRecordId = holdCollection.applyRecordId,
            status = holdCollection.status,
            statusName = HoldStatus.getName(holdCollection.status),
            tokenId = entity.tokenId,
            createAt = entity.createAt,
            hideFlag = entity.hideFlag,
        )
    }

    fun getMemberHoldCollectionPage(dto: PageDto<Collection, MemberHoldCollectionPageReqDto>): PageInfo<MemberHoldCollectionDetailRespDto> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second
        val resultDto = dto.pageQuery {
            collectionRepo.lambdaQuery()
                .eq(Collection::getHideFlag, HideType.FALSE.value)
                .eq(Collection::getDeleteFlag, DeleteType.FALSE.value)
                .like(dto.query.collectionName.isNotBlank(), Collection::getName, dto.query.collectionName)
                .between(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank(),
                    Collection::getCreateAt, start, end
                )
                .ge(
                    dto.query.startTime.isNotBlank() && dto.query.endTime.isNotBlank().not(),
                    Collection::getCreateAt, start
                )
                .le(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank().not(),
                    Collection::getCreateAt, end
                )
                .list()
        }
        return convertToResultPageInfo(resultDto) {
            resultDto.list.stream().map { convertToDetailRespDto(it) }.toList()
        }
    }

    fun checkHoldCollectionExist(holdCollectionId: Long) {
        val bool = holdRepo.lambdaQuery()
            .eq(HoldCollection::getId, holdCollectionId)
            .eq(HoldCollection::getDeleteFlag, DeleteType.FALSE.value)
            .exists()
        if (!bool) {
            throw HoldCollectionNotFountException("Hold Collection not found".toErrorMsg())
        }
    }
}