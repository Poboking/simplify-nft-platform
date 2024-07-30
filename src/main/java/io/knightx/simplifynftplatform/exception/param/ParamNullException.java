package io.knightx.simplifynftplatform.exception.param;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/24 下午11:14
 */
@ExceptionMapper(code = "400", msg = "Exception: Param Null.")
public class ParamNullException extends RuntimeException {
    public ParamNullException(String message) {
        super(message);
    }
}
