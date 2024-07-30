package io.knightx.simplifynftplatform.profile;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.strategy.SaStrategy;
import io.knightx.simplifynftplatform.exception.general.UnauthorizedException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static cn.dev33.satoken.SaManager.log;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午8:56
 */
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(SaTokenConfiguration.class);

    // 开启Sa-Token的继承注解功能，这样就可以使用@CheckUserLogin和@CheckAdminLogin注解了 ヾ(≧▽≦*)o
    @PostConstruct
    public void rewriteSaStrategy() {
        SaStrategy.instance.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 校验拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 指定一条 match 规则
//                    SaRouter
//                            //在Spring MVC中，之前是通过AntPathMatcher来分析路径的，
//                            // 但是从Spring 5.3.0开始改为使用WebFlux中引入的PathPatternParser, 也就是说不再支持"**"这种通配符
//                            .match("/**")    // 拦截的 path 列表，可以写多个 */
//                            .notMatch("/login",
//                                    "/back/login",
//                                    // swagger文档配置
//                                    "/*.html",
//                                    "/*/api-docs",
//                                    "/doc.html",
//                                    "/doc.html/*",
//                                    "/doc.*",
//                                    "/swagger-ui.*",
//                                    "/swagger-resources",
//                                    "/webjars/*",
//                                    "/v2/api-docs/*",
//                                    "/v3/api-docs/*,",
//                                    "/doc.html#/user接口文档/*",
//                                    "/doc.html#/admin接口文档/*")        // 排除掉的 path 列表，可以写多个
//                            .check(r -> {
//                                if (!StpUserUtil.isLogin() && !StpUtil.isLogin()){
//                                    throw new UnauthorizedException("[UnauthorizedException]: 用户未登录");
//                                }
//                            });        // 要执行的校验动作，可以写完整的 lambda 表达式


                    /**
                     * Member路由
                     */
                    SaRouter.match(
                                    "/**")
                            .notMatch(
                                    "/auth/*",
                                    "/auth/back/*",
                                    "/doc.html",
                                    "/doc.html#/**",
                                    "/favicon.ico"
                            )
                            .check(r -> {
                                if (Boolean.FALSE.equals(StpUserUtil.isLogin())) {
                                    throw new UnauthorizedException("[UnauthorizedException]: 用户未登录");
                                }
                            });
                    /**
                     * 共有路由
                     */
                    SaRouter.match("/dictconfig/**").check(r -> {
                        if (Boolean.FALSE.equals(StpUserUtil.isLogin() || StpAdminUtil.isLogin())) {
                            throw new UnauthorizedException("[UnauthorizedException]: 用户未登录");
                        }
                    });
                    /**
                     * Admin路由
                     */
                    SaRouter.match("/back/**").check(r -> {
                        if (Boolean.FALSE.equals(StpAdminUtil.isLogin())) {
                            throw new UnauthorizedException("[UnauthorizedException]: 管理员未登录");
                        }
                    });
                    // 根据路由划分模块，不同模块不同鉴权
                    SaRouter.match("/back/**", r -> StpAdminUtil.checkPermission("admin"));
                    SaRouter.match("/collection/**", r -> StpAdminUtil.checkRoleOr("user", "admin"));

                    // 甚至你可以随意的写一个打印语句
                    SaRouter.match("/**", r ->
                    {
                        if (StpUserUtil.isLogin()) {
                            log.info("[" + StpUserUtil.getLoginIdDefaultNull() + "]:用户访问");
                        }
                        if (StpAdminUtil.isLogin()) {
                            log.info("[" + StpAdminUtil.getLoginIdDefaultNull() + "]:管理员访问");
                        }
                    });
                })).addPathPatterns("/**")
                // "/error"用于给404错误放行
                .excludePathPatterns("/test/",
                        "/error",
                        // 排除icon图标
                        "/favicon.ico",
                        "/favicon.ico/**",
                        // swagger文档配置
                        "/*.html",
                        "/**/api-docs",
                        "/doc.html",
                        "/doc.html#/**",
                        "/doc.html/**",
                        "/doc.*",
                        "/swagger-ui.*",
                        "/swagger-resources",
                        "/webjars/**",
                        "/v2/api-docs/**",
                        "/v3/api-docs/**");
    }
}
