package io.knightx.simplifynftplatform.exception.general;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午8:39
 */
@ExceptionMapper(code = "501", msg = "Exception: Unauthorized Exception.")
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
