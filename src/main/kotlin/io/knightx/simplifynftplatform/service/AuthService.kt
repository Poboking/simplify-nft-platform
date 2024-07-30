package io.knightx.simplifynftplatform.service

import io.knightx.simplifynftplatform.dto.auth.LoginReqDto
import io.knightx.simplifynftplatform.dto.auth.TokenInfo
import io.knightx.simplifynftplatform.dto.manager.ManagerRegisterReqDto
import io.knightx.simplifynftplatform.dto.member.MemberRegisterReqDto
import io.knightx.simplifynftplatform.exception.general.BadRequestException
import io.knightx.simplifynftplatform.ext.check
import io.knightx.simplifynftplatform.profile.StpAdminUtil
import io.knightx.simplifynftplatform.profile.StpUserUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern

/**
 * 普通用户登录
 *
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午8:02
 */
@Service
class AuthService(
    @Autowired
    private val member: MemberService,
    @Autowired
    private val manager: ManagerService
) {

    fun login(dto: LoginReqDto): TokenInfo {
        checkValidInputs(dto.name, dto.password)
        if (member.checkMember(dto.name, dto.password).not()) {
            throw BadRequestException("Member name or password error")
        }
        StpUserUtil.login(member.getMemberIdByMemberName(dto.name))
        val info = StpUserUtil.getTokenInfo()
        return TokenInfo(info.tokenName, info.tokenValue)
    }

    fun register(dto: MemberRegisterReqDto): TokenInfo {
        checkValidInputs(dto.memberName, dto.password)
        member.registerMember(dto)
        return login(LoginReqDto(dto.memberName, dto.password))
    }


    fun managerLogin(dto: LoginReqDto): TokenInfo {
        checkValidInputs(dto.name, dto.password)
        if (manager.checkManager(dto.name, dto.password).not()) {
            throw BadRequestException("Manager name or password error")
        }
        StpAdminUtil.login(manager.getManagerIdByManagerName(dto.name))
        val info = StpAdminUtil.getTokenInfo()
        return TokenInfo(info.tokenName, info.tokenValue)
    }


    fun managerRegister(dto: ManagerRegisterReqDto): TokenInfo {
        checkValidInputs(dto.managerName, dto.password)
        manager.registerManager(dto)
        return managerLogin(LoginReqDto(dto.managerName, dto.password))
    }


    fun checkValidInput(str: String?): Unit {
        if (!str.check()) {
            throw BadRequestException("Invalid input, Null String")
        }
        val PATTERN: Pattern = Pattern.compile("^[a-zA-Z0-9]+$")
        if (PATTERN.matcher(str).matches().not()) {
            throw BadRequestException("Invalid input, illegal character")
        }
    }

    fun checkValidInputs(vararg strings: String?): Unit {
        for (str in strings)
            checkValidInput(str)
    }
}