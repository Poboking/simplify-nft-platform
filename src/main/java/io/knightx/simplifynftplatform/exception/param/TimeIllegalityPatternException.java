package io.knightx.simplifynftplatform.exception.param;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/24 下午10:52
 */
@ExceptionMapper(code = "400", msg = "Exception: Time Illegality Pattern.")
public class TimeIllegalityPatternException extends RuntimeException {
    public TimeIllegalityPatternException(String message) {
        super(message);
    }
}
