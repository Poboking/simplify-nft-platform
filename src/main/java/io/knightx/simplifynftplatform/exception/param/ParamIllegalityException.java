package io.knightx.simplifynftplatform.exception.param;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/26 下午10:20
 */
@ExceptionMapper(code = "400", msg = "Exception: Param Illegality.")
public class ParamIllegalityException extends RuntimeException {
    public ParamIllegalityException(String message) {
        super(message);
    }
}
