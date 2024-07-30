package io.knightx.simplifynftplatform

import io.knightx.simplifynftplatform.dto.member.MemberRegisterReqDto
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SimplifyNftPlatformApplicationTests {

    @Test
    fun contextLoads() {
    }

    
    @Test
    fun toEntityTest(){
        println("this is a test")
        println(
            MemberRegisterReqDto(
                memberName = "LiSan",
                password = "test"
            ).toEntity().toString()
        )
    }
}
