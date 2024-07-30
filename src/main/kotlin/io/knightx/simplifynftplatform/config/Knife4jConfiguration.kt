package io.knightx.simplifynftplatform.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午8:49
 */
@Configuration
class Knife4jConfiguration {

    /***
     * 配置Swagger文档
     */
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI() // 增加swagger授权请求头配置
            .components(
                Components().addSecuritySchemes(
                    "bearer-jwt",
                    SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                )
            )
            .info(
                Info()
                    .title("#SimplifyNFT Project Api#")
                    .description(
                        """
                                            This project is a Springboot-based nft platform website.
                                            The Project is Based on Kotlin And SpringBoot,
                                            And The Api Documentation is Based on Swagger3 and OpenAPI3.
                                        
                                        """.trimIndent()
                    )
                    .version("v1.0.0")
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                    )
                    .contact(
                        Contact()
                            .name("poboking")
                            .email("poboking@163.com")
                            .url("https://github.com/poboking")
                    )
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("YunPing project's home page.")
                    .url("https://github.com/Poboking/yunping")
            )
    }
}