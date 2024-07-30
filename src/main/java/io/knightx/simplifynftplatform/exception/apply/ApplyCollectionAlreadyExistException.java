package io.knightx.simplifynftplatform.exception.apply;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午10:56
 */
@ExceptionMapper(code = "400", msg = "Exception:ApplyException Already exist.")
public class ApplyCollectionAlreadyExistException extends RuntimeException {
    public ApplyCollectionAlreadyExistException(String message) {
        super(message);
    }
}
