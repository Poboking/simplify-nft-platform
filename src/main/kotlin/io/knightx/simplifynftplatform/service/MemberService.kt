package io.knightx.simplifynftplatform.service

import com.github.pagehelper.PageInfo
import io.knightx.simplifynftplatform.common.DeleteType
import io.knightx.simplifynftplatform.dto.general.PageDto
import io.knightx.simplifynftplatform.dto.member.*
import io.knightx.simplifynftplatform.exception.general.BadRequestException
import io.knightx.simplifynftplatform.exception.member.MemberAlreadyExistException
import io.knightx.simplifynftplatform.exception.member.MemberNotFountException
import io.knightx.simplifynftplatform.exception.member.MemberNotOnChainException
import io.knightx.simplifynftplatform.ext.check
import io.knightx.simplifynftplatform.ext.checkQueryTime
import io.knightx.simplifynftplatform.ext.pageQuery
import io.knightx.simplifynftplatform.ext.toErrorMsg
import io.knightx.simplifynftplatform.mq.component.RabbitSender
import io.knightx.simplifynftplatform.po.Member
import io.knightx.simplifynftplatform.po.repo.impl.MemberRepoImpl
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:43
 */
@Service
class MemberService(
    @Resource
    val repo: MemberRepoImpl,
    @Resource
    val sender: RabbitSender
) {
    fun registerMember(dto: MemberRegisterReqDto): Boolean {
        if (checkMemberNameExist(dto.memberName)) {
            throw MemberAlreadyExistException("Member already exists".toErrorMsg())
        }
        val bool: Boolean = repo.save(dto.toEntity())
        val id: Long = getMemberIdByMemberName(dto.memberName)
        sender.sendRegister(
            MemberRegisterChainUserDto(
                memberId = id,
                username = dto.memberName
            ).toJson()
        )
        return bool
    }


    fun getMember(memberId: Long): Member? {
        checkMemberExits(memberId)
        return repo.lambdaQuery().eq(Member::getId, memberId).one()
    }

    fun getMemberIdByMemberName(memberName: String): Long {
        return repo.lambdaQuery().eq(Member::getMemberName, memberName).one()?.id ?: -1
    }

    fun updateMemberInfo(dto: MemberUpdateReqDto): Boolean {
        checkMemberExits(dto.id)
        return repo.lambdaUpdate().eq(Member::getId, dto.id)
            .set(Member::getMemberName, dto.name)
            .set(dto.password.check(), Member::getPassword, dto.password)
            .set(Member::getNickName, dto.nickName)
            .update()
    }

    fun deleteMember(memberId: Long): Boolean {
        checkMemberExits(memberId)
        return repo.lambdaUpdate()
            .eq(Member::getId, memberId)
            .set(Member::getDeleteFlag, DeleteType.TRUE.value)
            .update()
    }

    fun getMemberPage(dto: PageDto<Member, MemberPageReqDto>): PageInfo<Member> {
        val pair = checkQueryTime(dto.query.startTime, dto.query.endTime)
        val start = pair.first
        val end = pair.second
        return dto.pageQuery {
            repo.lambdaQuery()
                .eq(Member::getDeleteFlag, DeleteType.FALSE.value)
                .like(dto.query.name.check(), Member::getMemberName, dto.query.name)
                .between(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank(),
                    Member::getCreateAt, start, end
                )
                .ge(
                    dto.query.startTime.isNotBlank() && dto.query.endTime.isNotBlank().not(),
                    Member::getCreateAt, start
                )
                .le(
                    dto.query.endTime.isNotBlank() && dto.query.startTime.isNotBlank().not(),
                    Member::getCreateAt, end
                )
                .list()
        }
    }

    fun checkMemberNameExist(name: String) =
        repo.lambdaQuery().eq(Member::getMemberName, name).exists()

    fun checkMemberExits(memberId: Long): Boolean {
        if (memberId <= 0) {
            throw BadRequestException("Invalid member id".toErrorMsg())
        }
        if (repo.lambdaQuery().eq(Member::getId, memberId).exists().not()) {
            throw BadRequestException("Member not exists".toErrorMsg())
        }
        return true
    }

    fun checkMember(username: String, password: String): Boolean {
        return repo.lambdaQuery().eq(Member::getMemberName, username)
            .eq(Member::getPassword, password)
            .one() != null
    }

    fun getMemberByName(username: String): Member {
        return repo.lambdaQuery()
            .eq(username.check(), Member::getMemberName, username)
            .one()
    }

    fun getMyInfoById(memberId: Long): MemberInfoRespDto {
        val member: Member = repo.getById(memberId)
        return MemberInfoRespDto(
            memberName = member.memberName ?: "Unknown",
            nickName = member.nickName ?: "Unknown",
            blockAddress = member.blockAddress ?: "Unknown",
            privateKey = member.privateKey ?: "Unknown",
            publicKey = member.publicKey ?: "Unknown",
        )
    }

    fun getPrivateKeyById(memberId: Long): String {
        val member = repo.getById(memberId)
        if (!member.check()) {
            throw MemberNotFountException("$memberId Member Not Fount".toErrorMsg())
        }
        return member.privateKey
    }


    fun checkMemberIsChainUser(memberId: Long): Unit {
        val member = repo.getById(memberId)
        if (!member.check()) {
            throw MemberNotFountException("$memberId Member Not Fount".toErrorMsg())
        }
        if (!repo.lambdaQuery().eq(Member::getId, memberId).one().privateKey.check()) {
            throw MemberNotOnChainException("$memberId Member Not On Chain".toErrorMsg())
        }
    }
}