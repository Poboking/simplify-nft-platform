package io.knightx.simplifynftplatform.service

import com.github.pagehelper.PageInfo
import io.knightx.simplifynftplatform.common.DeleteType
import io.knightx.simplifynftplatform.common.HideType
import io.knightx.simplifynftplatform.dto.collection.*
import io.knightx.simplifynftplatform.dto.general.PageDto
import io.knightx.simplifynftplatform.exception.collection.CollectionContentAlreadyExistException
import io.knightx.simplifynftplatform.exception.collection.CollectionNameAlreadyExistException
import io.knightx.simplifynftplatform.exception.collection.CollectionNotFoundException
import io.knightx.simplifynftplatform.ext.check
import io.knightx.simplifynftplatform.ext.checkQueryTime
import io.knightx.simplifynftplatform.ext.pageQuery
import io.knightx.simplifynftplatform.ext.toErrorMsg
import io.knightx.simplifynftplatform.po.Collection
import io.knightx.simplifynftplatform.po.repo.impl.CollectionRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.HoldCollectionRepoImpl
import io.knightx.simplifynftplatform.po.repo.impl.MemberRepoImpl
import jakarta.annotation.Resource
import org.springframework.stereotype.Service


/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:43
 */
@Service
class CollectionService(
    @Resource
    val repo: CollectionRepoImpl,
    val memberRepo: MemberRepoImpl,
    val holdRepo: HoldCollectionRepoImpl
) {

    fun saveCollection(dto: CollectionCreateReqDto): Boolean {
        if (checkCollectionName(dto.name)) {
            throw CollectionNameAlreadyExistException("Collection name already exists".toErrorMsg())
        }
        return repo.save(dto.toEntity())
    }

    fun mintCollection(dto: CollectionMintDto): Boolean {
        checkCollectionUptoStandard(dto.name, dto.content)
        val member = memberRepo.getById(dto.memberId)
        return repo.save(dto.toEntity().apply {
            this.memberId = member.id
            this.memberName = member.memberName
            this.tokenId = dto.tokenId
            this.name = dto.name
        })
    }

    fun getCollection(collectionId: Long): Collection {
        if (!checkCollectionExist(collectionId)) {
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        }
        return repo.getById(collectionId)
    }

    fun updateCollection(dto: CollectionUpdateReqDto): Boolean {
        if (!checkCollectionExist(dto.id)) {
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        }
        return repo.lambdaUpdate()
            .eq(Collection::getId, dto.id)
            .set(dto.name.check(), Collection::getName, dto.name)
            .set(dto.content.check(), Collection::getContent, dto.content)
            .update()
    }

    fun deleteCollection(collectionId: Long): Boolean {
        return repo.lambdaUpdate()
            .eq(Collection::getId, collectionId)
            .set(Collection::getDeleteFlag, DeleteType.TRUE.value)
            .update()
    }


    fun getCollectionPage(dto: PageDto<Collection, CollectionPageReqDto>): PageInfo<Collection> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second
        return dto.pageQuery {
            repo.lambdaQuery()
                .eq(Collection::getHideFlag, HideType.FALSE.value)
                .eq(Collection::getDeleteFlag, DeleteType.FALSE.value)
                .like(dto.query.memberName.check(), Collection::getMemberName, dto.query.memberName)
                .like(dto.query.name.check(), Collection::getName, dto.query.name)
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
    }

    fun checkCollectionExist(collectionId: Long): Boolean {
        return repo.lambdaQuery()
            .eq(Collection::getId, collectionId)
            .count() > 0
    }

    fun checkCollectionName(name: String): Boolean {
        return repo.lambdaQuery()
            .eq(Collection::getName, name)
            .count() > 0
    }

    fun checkMyCollectionExist(collectionId: Long, memberId: Long): Boolean {
        return repo.lambdaQuery()
            .eq(Collection::getId, collectionId)
            .eq(Collection::getMemberId, memberId)
            .count() > 0
    }

    fun checkCollectionNameExist(name: String) =
        repo.lambdaQuery().eq(Collection::getName, name).exists()

    fun checkCollectionContentExist(content: String) =
        repo.lambdaQuery().eq(Collection::getContent, content).exists()

    /**
     * 用于检查藏品名称和藏品内容是否符合规范(是否重复存在)
     *
     * @throws CollectionNameAlreadyExistException
     */
    fun checkCollectionUptoStandard(name: String, content: String): Unit {
        if (checkCollectionNameExist(name)) {
            throw CollectionNameAlreadyExistException("Collection name already exists".toErrorMsg())
        }
        if (checkCollectionContentExist(content)) {
            throw CollectionContentAlreadyExistException("Collection content already exists".toErrorMsg())
        }
    }

    fun getCollectionIdByTokenId(tokenId: String): Long {
        val one = repo.lambdaQuery().eq(Collection::getTokenId, tokenId)
            .one()
        if (one.check().not()) {
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        }
        return one.id
    }

    fun getTokenIdByCollectionId(collectionId: Long): String {
        return repo.getById(collectionId).tokenId
    }

    fun getMyCollection(collectionId: Long, memberId: Long): Collection {
        val one = repo.lambdaQuery()
            .eq(Collection::getId, collectionId)
            .eq(Collection::getMemberId, memberId)
            .one()
        if (one.check().not())
            throw CollectionNotFoundException("Collection not found".toErrorMsg())
        return one
    }

    fun getMyCollectionPage(dto: PageDto<Collection, MyCollectionPageReqDto>, memberId: Long): PageInfo<Collection> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second
        return dto.pageQuery {
            repo.lambdaQuery()
                .eq(Collection::getDeleteFlag, DeleteType.FALSE.value)
                .eq(memberId.check(), Collection::getMemberId, memberId)
                .like(dto.query.name.check(), Collection::getName, dto.query.name)
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
    }

    fun hideCollection(collectionId: Long): Boolean {
        return repo.lambdaUpdate()
            .eq(Collection::getId, collectionId)
            .set(Collection::getHideFlag, HideType.TRUE.value)
            .update()
    }
}