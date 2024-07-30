package io.knightx.simplifynftplatform.exception.record;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午10:42
 */
@ExceptionMapper(code = "500", msg = "Exception: Apply Operation Failed , Apply Permission Error.")
public class ApplyRecordPermissionException extends RuntimeException {
    public ApplyRecordPermissionException(String msg) {
        super(msg);
    }
}
