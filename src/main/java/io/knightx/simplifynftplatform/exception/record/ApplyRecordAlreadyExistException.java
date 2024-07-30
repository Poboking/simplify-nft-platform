package io.knightx.simplifynftplatform.exception.record;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午4:27
 */
@ExceptionMapper(code = "400", msg = "Exception: The Apply Record Already Exist.")
public class ApplyRecordAlreadyExistException extends RuntimeException{
    public ApplyRecordAlreadyExistException(String message) {
        super(message);
    }
}
