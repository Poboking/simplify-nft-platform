package io.knightx.simplifynftplatform.exception.general;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午3:44
 */
@ExceptionMapper(code = "500", msg = "Exception: Server Error.")
public class ServerErrorException extends RuntimeException {
    public ServerErrorException(String msg) {
        super(msg);
    }
}
