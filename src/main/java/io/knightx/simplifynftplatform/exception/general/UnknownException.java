package io.knightx.simplifynftplatform.exception.general;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午3:45
 */
@ExceptionMapper(code = "600", msg = "Exception: Unknown Error.")
public class UnknownException extends RuntimeException {
    public UnknownException(String msg) {
        super(msg);
    }
}
