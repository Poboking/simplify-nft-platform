package io.knightx.simplifynftplatform.exception.apply;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午8:42
 */
@ExceptionMapper(code = "400", msg = "Exception: The Apply Record Not Found.")
public class ApplyRecordNotFountException extends RuntimeException {
    public ApplyRecordNotFountException(String msg) {
        super(msg);
    }
}
