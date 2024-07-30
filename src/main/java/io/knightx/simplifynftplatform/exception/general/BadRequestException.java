package io.knightx.simplifynftplatform.exception.general;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午3:43
 */
@ExceptionMapper(code = "400", msg = "Exception: Bad Request.")
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
