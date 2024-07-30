package io.knightx.simplifynftplatform.service

import io.knightx.simplifynftplatform.dto.member.MemberRegisterReqDto
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午6:54
 */
@SpringBootTest
class MemberServiceTest @Autowired constructor(
    private val service: MemberService
) {
    val log: Logger = org.slf4j.LoggerFactory.getLogger(MemberServiceTest::class.java)


    @Test
    fun registerMemberTest() {
        log.info("register test")
        service.registerMember(
            MemberRegisterReqDto(
                memberName = "LiSan",
                password = "test"
            )
        )
    }
}