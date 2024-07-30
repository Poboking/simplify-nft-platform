package io.knightx.simplifynftplatform.annotation;

import cn.dev33.satoken.annotation.SaCheckLogin;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午9:11
 */
@SaCheckLogin(type = "admin")
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface CheckAdminLogin {
}
