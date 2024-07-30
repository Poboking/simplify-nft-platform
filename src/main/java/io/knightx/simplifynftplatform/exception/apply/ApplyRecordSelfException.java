package io.knightx.simplifynftplatform.exception.apply;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午10:54
 */
@ExceptionMapper(code = "400", msg = "Exception: Apply Operation Must Be Not Self.")
public class ApplyRecordSelfException extends RuntimeException {
    public ApplyRecordSelfException(String msg) {
        super(msg);
    }
}
